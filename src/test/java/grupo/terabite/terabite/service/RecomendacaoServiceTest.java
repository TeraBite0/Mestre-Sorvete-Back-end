package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.DestaqueRepository;
import grupo.terabite.terabite.repository.RecomendacaoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Recomendação")
class RecomendacaoServiceTest extends DataFactory {

    @Mock
    private ProdutoService produtoService;


    @Mock
    private RecomendacaoRepository recomendacaoRepository;

    @InjectMocks
    private RecomendacaoService recomendacaoService;

    @Test
    @DisplayName("Lista todas recomendações")
    void listarRecomendacoes() {
        Mockito.when(recomendacaoRepository.findAll()).thenReturn(recomendacoes);

        List<Recomendacao> recomendacaoResponse = recomendacaoService.listarRecomendacoes();

        assertNotNull(recomendacaoResponse, "A lista de recomendações não pode ser nula");
        assertNotNull(recomendacaoResponse.get(0).getProduto(), "Não pode ser retornado um produto nulo");
        assertEquals(recomendacoes, recomendacaoResponse, "A lista de recomendações foi alterada");

        Mockito.when(recomendacaoRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> recomendacaoService.listarRecomendacoes(), "Nenhuma Recomendação devia ser listada");
        assertEquals(HttpStatusCode.valueOf(204), exception.getStatusCode(), "O status da requisição deveria ser 204 quando não há recomendações");
    }

    @Test
    @DisplayName("Cria uma recomendação")
    void criarRecomendacao() {
        Recomendacao expected = new Recomendacao(1, produtos.get(0));

        Mockito.when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(expected.getProduto());
        Mockito.when(recomendacaoRepository.save(Mockito.any())).thenReturn(expected);

        Recomendacao recomendacaoResponse = recomendacaoService.criarRecomendacao(expected.getProduto().getId());

        assertNotNull(recomendacaoResponse,"A recomendação não pode ser nula");
        assertEquals(expected.getProduto(), recomendacaoResponse.getProduto(), "O produto é diferente do esperado");
    }

    @Test
    @DisplayName("Atualiza uma recomendação")
    void atualizarRecomendacao() {
        Recomendacao expected = recomendacoes.get(0);

        Mockito.when((recomendacaoRepository.existsById(Mockito.anyInt()))).thenReturn(true);
        Mockito.when(recomendacaoRepository.save(Mockito.any())).thenReturn(expected);
        Mockito.when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(produtos.get(0));
        Mockito.when(recomendacaoRepository.findByProdutoId(Mockito.anyInt())).thenReturn(new ArrayList<>());

        Recomendacao response = recomendacaoService.atualizarRecomendacao(expected.getId(), expected.getProduto().getId());

        assertNotNull(response, "A recomendação não pode ser nula");
        assertNotNull(response.getProduto(), "O produto não pode ser nulo");

        Mockito.when(recomendacaoRepository.findByProdutoId(Mockito.anyInt())).thenReturn(List.of(recomendacoes.get(0)));

        ResponseStatusException exception1 = assertThrows(ResponseStatusException.class, () -> recomendacaoService.atualizarRecomendacao(expected.getId(), expected.getProduto().getId()),"Não deveria ser possivel recomendar um produto já recomendado");
        assertEquals(HttpStatusCode.valueOf(409), exception1.getStatusCode(), "O status deveria ser 409");

        Mockito.when((recomendacaoRepository.existsById(Mockito.anyInt()))).thenReturn(false);
        ResponseStatusException exception2 = assertThrows(ResponseStatusException.class, () -> recomendacaoService.atualizarRecomendacao(expected.getId(), expected.getProduto().getId()), "Não deveria ser possivel atualizar uma recomendação com id inexistente");
        assertEquals(HttpStatusCode.valueOf(404), exception2.getStatusCode(), "O status deveria ser 404");

    }

    @Test
    @DisplayName("Exclui uma recomendação")
    void excluirRecomendacao() {
        int recomendacaoId = recomendacoes.get(0).getId();

        Mockito.when(recomendacaoRepository.existsById(recomendacaoId)).thenReturn(true);

        assertDoesNotThrow(() -> recomendacaoService.excluirRecomendacao(recomendacaoId), "A recomendação deve ser excluída sem erro");

        Mockito.verify(recomendacaoRepository, Mockito.times(1)).deleteById(recomendacaoId);


        Mockito.when(recomendacaoRepository.existsById(Mockito.anyInt())).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> recomendacaoService.excluirRecomendacao(recomendacaoId), "Deveria lançar exceção ao excluir recomendação inexistente");
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode(), "O status deveria ser 404");
    }

}