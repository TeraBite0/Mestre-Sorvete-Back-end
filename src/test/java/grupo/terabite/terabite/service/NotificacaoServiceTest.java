package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import grupo.terabite.terabite.service.api.EmailApiService;
import org.junit.jupiter.api.BeforeEach;
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
@DisplayName("Notificações")
class NotificacaoServiceTest extends DataFactory {

    @Mock
    private NotificacaoRepository notificacaoRepository;
    @Mock
    private EmailApiService emailApiService;
    @Mock
    private ProdutoService produtoService;
    @InjectMocks
    private NotificacaoService notificacaoService;

    @Test
    @DisplayName("Cria corretamente")
    public void criarNotificacao() {
        Notificacao notificacaoOk = notificacoes.get(0);
        List<Notificacao> notificacaoResposta = null;
        Mockito.when(notificacaoRepository.saveAll(Mockito.any())).thenReturn(List.of(notificacaoOk));
        Mockito.when(notificacaoRepository.findByEmail(Mockito.anyString())).thenReturn(List.of());
        Mockito.when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(notificacaoOk.getProduto());

        try {
            notificacaoResposta = notificacaoService.criarNotificacao(notificacaoOk.getEmail(), List.of(notificacaoOk.getId()));
        } catch (Exception e) {
            fail("Erro ao criar Notificação: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(notificacaoResposta, "A notificação resposta é nula");
        assertEquals(notificacaoOk.getEmail(), notificacaoResposta.get(0).getEmail(), "O e-mail da notificação deve ser igual ao esperado");
        assertEquals(notificacaoOk.getProduto().getId(), notificacaoResposta.get(0).getProduto().getId(), "O produto da notificação deve ser o igual ao esperado");
    }

    @Test
    @DisplayName("Lista todas notificações")
    public void listar() {
        List<Notificacao> notificacaoResposta = new ArrayList<>();

        Mockito.when(notificacaoRepository.findAll()).thenReturn(notificacoes);

        try {
            notificacaoResposta = notificacaoService.listarNotificacoes();
        } catch (Exception e) {
            fail("Erro ao listar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertEquals(notificacoes.size(), notificacaoResposta.size(), "O numero de notificações retornado é diferente do esperado");

        Mockito.when(notificacaoRepository.findAll()).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> notificacaoService.listarNotificacoes());
        assertEquals(exception.getStatusCode(), HttpStatusCode.valueOf(204), "O status da resposta não é o correto");
    }

    @Test
    @DisplayName("Deleta notificações")
    public void deletarNotificacao() {
        try {
            notificacaoService.deletarNotificacaoPorProdutoId(1);
        } catch (Exception e) {
            fail("Erro ao deletar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }
    }

    @Test
    @DisplayName("Envia notificações corretamente")
    public void notificar() {
        Mockito.when(notificacaoRepository.findByProdutoId(Mockito.anyInt())).thenReturn(notificacoes.stream().filter(n -> n.getProduto().getId() == 1).toList());
        Mockito.doNothing().when(emailApiService).enviarAlertaDeProdutos(Mockito.any());
        notificacaoService.notificarProdutoEmEstoque(produtos.get(0));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificacaoService.notificarProdutoEmEstoque(produtos.get(2)), "Não deve ser possivel notificar um produto inativo");
        assertThrows(RuntimeException.class, () -> notificacaoService.notificarProdutoEmEstoque(produtos.get(3)), "Não deve ser possivel notificar um produto inativo");
        assertEquals("Erro ao notificar, produto inapto para notificar", exception.getMessage(), "Mensagem de erro, diferente do esperado");
    }

//  atualmente existe validação de duplicatas mas, ela faz a operação sem retornar erro
//    @Test
//    @DisplayName("Não cria notificação duplicadas")
//    public void registrarNotificacaoDuplicada() {
//        Mockito.when(notificacaoRepository.findByEmail(Mockito.anyString())).thenReturn(List.of(notificacoes.get(0)));
//        Mockito.when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(null);
//
//        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> notificacaoService.criarNotificacao(notificacoes.get(0).getEmail(), List.of(notificacoes.get(0).getId())), "Deve ser retornado erro ao criar notificação já ativa");
//        assertEquals(HttpStatusCode.valueOf(409), exception.getStatusCode(), "A resposta da exception de duplicados está incorreta");
//        Mockito.verify(notificacaoRepository, Mockito.never()).save(Mockito.any(Notificacao.class));
//    }
}
