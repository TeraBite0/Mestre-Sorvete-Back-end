package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.service.api.EmailApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Notificações")
class NotificacaoServiceTest {

    @Mock
    private NotificacaoRepository notificacaoRepository;

    @Mock
    private EmailApiService emailApiService;

    @InjectMocks
    private NotificacaoService notificacaoService;

    List<Produto> produtos;
    List<Notificacao> notificacoes;

    @BeforeEach
    protected void setup() {
        this.produtos = List.of(
                new Produto(1, "Gelo gelado", null, null, 10.00, true, true),
                new Produto(2, "Gelo geladinho", null, null, 10.0, true, true),
                new Produto(3, "Gelo quentinho", null, null, 10.0, false, true),
                new Produto(4, "Gelo quente", null, null, 10.0, true, false));


        this.notificacoes = List.of(
                new Notificacao(1, "test1@test.test", produtos.get(0)),
                new Notificacao(2, "test2@test.test", produtos.get(0)),
                new Notificacao(3, "test3@test.test", produtos.get(0)),
                new Notificacao(4, "test4@test.test", produtos.get(1)),
                new Notificacao(5, "test5@test.test", produtos.get(1)));
    }

    @Test
    @DisplayName("Cria corretamente")
    public void criarNotificacao() {
        Notificacao notificacaoOk = notificacoes.get(0);
        Notificacao notificacaoResposta = null;
        Mockito.when(notificacaoRepository.save(Mockito.any())).thenReturn(notificacaoOk);

        try {
            notificacaoResposta = notificacaoService.criarNotificacao(notificacaoOk);
        } catch (Exception e) {
            fail("Erro ao criar Notificação: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(notificacaoResposta, "A notificação resposta é nula");
        assertEquals(notificacaoOk.getEmail(), notificacaoResposta.getEmail(), "O e-mail da notificação deve ser igual ao esperado");
        assertEquals(notificacaoOk.getProduto().getId(), notificacaoResposta.getProduto().getId(), "O produto da notificação deve ser o igual ao esperado");
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
    public void deletarNotificacao(){
        try {
            notificacaoService.deletarNotificacaoPorProdutoId(1);
        } catch (Exception e) {
            fail("Erro ao deletar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }
    }

    @Test
    @DisplayName("Envia notificações corretamente")
    public void notificar(){
        try {
            Mockito.when(notificacaoRepository.findByProdutoId(Mockito.anyInt())).thenReturn(notificacoes.stream().filter( n -> n.getProduto().getId() == 1).toList());
            notificacaoService.notificarProdutoEmEstoque(produtos.get(0));

            Mockito.when(notificacaoRepository.findByProdutoId(Mockito.anyInt())).thenReturn(new ArrayList<>());
            notificacaoService.notificarProdutoEmEstoque(produtos.get(0));
        } catch (Exception e) {
            fail("Erro ao deletar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        RuntimeException exception = assertThrows(RuntimeException.class, () -> notificacaoService.notificarProdutoEmEstoque(produtos.get(2)), "Não deve ser possivel notificar um produto inativo");
        assertThrows(RuntimeException.class, () -> notificacaoService.notificarProdutoEmEstoque(produtos.get(3)), "Não deve ser possivel notificar um produto inativo");
        assertEquals("Erro ao notificar, produto inapto para notificar", exception.getMessage(), "Mensagem de erro, diferente do esperado");
    }
}
