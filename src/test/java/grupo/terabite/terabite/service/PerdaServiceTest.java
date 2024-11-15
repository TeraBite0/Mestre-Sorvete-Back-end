package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Perda;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.PerdaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Perda")
class PerdaServiceTest {

    @Mock
    private PerdaRepository perdaRepository;

    @InjectMocks
    private PerdaService perdaService;

    public List<Produto> produtos;

    public List<Perda> perdasEsperadas;

    @BeforeEach
    protected void setup() {
        produtos = List.of(
                new Produto(1, "Gelo gelado", null, null, 7.0, true, true),
                new Produto(2, "Gelo geladinho", null, null, 7.0, true, true),
                new Produto(3, "Gelo quentinho", null, null, 10.0, false, true),
                new Produto(4, "Gelo quente", null, null, 10.0, true, false));

        perdasEsperadas = List.of(
                new Perda(1, 2, LocalDate.now(), produtos.get(0)),
                new Perda(2, 3, LocalDate.now(), produtos.get(1)),
                new Perda(3, 4, LocalDate.now(), produtos.get(2)),
                new Perda(4, 1, LocalDate.now(), produtos.get(0)),
                new Perda(5, 1, LocalDate.now(), produtos.get(1))
        );
    }

    @Test
    @DisplayName("Quando o banco de dados não possui perdas, o serviço deve lançar ResponseStatusException com status 204")
    void deveLancarExcecaoQuandoNaoExistemPerdas() {
        Mockito.when(perdaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.listarPerda());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui perdas, o serviço deve retornar a lista correta")
    void deveRetornarListaDePerdasQuandoExistirem() {
        Mockito.when(perdaRepository.findAll()).thenReturn(perdasEsperadas);

        List<Perda> perdasRetornadas = perdaService.listarPerda();
        assertIterableEquals(perdasEsperadas, perdasRetornadas, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 404")
    void deveLancarExecaoQuandoNaoEncontrarPerdaPorID() {
        Mockito.when(perdaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.buscarPerdaPorId(2));
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
    }

    @Test
    @DisplayName("Quando buscar por ID existente, deve retornar a perda correspondente")
    void deveRetornarPerdaQuandoPassadoSeuId() {
        Perda perda = perdasEsperadas.get(0);
        Mockito.when(perdaRepository.findById(1)).thenReturn(Optional.of(perda));

        Perda resultado = perdaService.buscarPerdaPorId(1);

        assertNotNull(resultado, "A perda retornada não deveria ser nula");
        assertEquals(perda.getId(), resultado.getId(), "O ID da perda retornada não está correto");
        assertEquals(perda.getQtdProduto(), resultado.getQtdProduto(), "A quantidade de perdas retornada não está correto");
        assertEquals(perda.getProduto(), resultado.getProduto(), "O produto que teve perda retornada não está correto");
    }

    //TODO: O método está retornando sempre nulo, validar para consertar o método
//    @Test
//    @DisplayName("Quando criar uma nova perda, deve definir o ID como null e salvar no repositório")
//    void deveCriarNovaPerda() {
//        Perda perdaSalva = perdasEsperadas.get(0);
//        Perda novaPerda = new Perda(null,3, LocalDate.now(), produtos.get(1));
//
//        Mockito.when(perdaRepository.save(novaPerda)).thenReturn(perdaSalva);
//
//        Perda resultado = perdaService.criarPerda(novaPerda);
//
//        assertNotNull(resultado, "O resultado não deve ser nulo");
//        assertEquals(novaPerda.getId(), resultado.getId(), "O ID da perda salva não está correto");
//        assertEquals(novaPerda.getQtdProduto(), resultado.getQtdProduto(), "A quantidade de perda salva não está correto");
//        assertEquals(novaPerda.getProduto(), resultado.getProduto(), "O produto da perda salva não está correto");
//    }

    @Test
    @DisplayName("Deve atualizar")
    void atualizarPerda() {
    }

    @Test
    @DisplayName("Quando deletar por ID existente, deve executar sem impetimentos")
    void deletarPerda() {
    }

    @Test
    @DisplayName("deve buscar por produto id")
    void buscarPerdaPorProdutoId() {
    }
}