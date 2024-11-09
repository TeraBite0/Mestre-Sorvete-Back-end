package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@DisplayName("Notificações")
class NotificacaoServiceTest {

    @Autowired
    private NotificacaoService notificacaoService;

    @Autowired
    private NotificacaoRepository notificacaoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @BeforeEach
    protected void setup() {
        List<Produto> produtos = List.of(
                new Produto(1, "Gelo gelado", null, null, null, true, true),
                new Produto(2, "Gelo geladinho", null, null, null, true, true),
                new Produto(3, "Gelo quentinho", null, null, 10.0, false, true),
                new Produto(4, "Gelo quente", null, null, 10.0, true, false));

        produtoRepository.saveAll(produtos);

        List<Notificacao> notificacoes = List.of(
                new Notificacao(1, "test1@test.test", produtos.get(0)),
                new Notificacao(2, "test2@test.test", produtos.get(0)),
                new Notificacao(3, "test3@test.test", produtos.get(0)),
                new Notificacao(4, "test4@test.test", produtos.get(1)),
                new Notificacao(5, "test5@test.test", produtos.get(1)));
        notificacaoRepository.saveAll(notificacoes);
    }

    @Test
    @DisplayName("Cria corretamente")
    public void criarNotificacao() {
        Notificacao notificacaoOk = notificacaoRepository.findById(1).orElseThrow();
        Notificacao notificacaoResposta = null;

        try {
            notificacaoResposta = notificacaoService.criarNotificacao(notificacaoOk);
        } catch (Exception e) {
            fail("Erro ao criar Notificação: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(notificacaoResposta, "A notificação resposta é nula");
        assertEquals(notificacaoOk.getEmail(), notificacaoResposta.getEmail(), "O e-mail da notificação deve ser igual ao esperado");
        assertEquals(notificacaoOk.getProduto().getId(), notificacaoResposta.getProduto().getId(), "O produto da notificação deve ser o igual ao esperado");
    }

    @Test
    @DisplayName("Lista todas notificações")
    public void listar() {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        List<Notificacao> notificacaoResposta = new ArrayList<>();

        try {
            notificacaoResposta = notificacaoService.listarNotificacoes();
        } catch (Exception e) {
            fail("Erro ao listar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertEquals(notificacoes.size(), notificacaoResposta.size(), "O numero de notificações retornado é diferente do esperado");

        notificacaoRepository.deleteAll();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> notificacaoService.listarNotificacoes());
        assertEquals(exception.getStatusCode(), HttpStatusCode.valueOf(204), "O status da resposta não é o correto");
    }

    @Test
    @DisplayName("Deletar notificações")
    public void deletarNotificacao(){
        List<Notificacao> notificacoes = notificacaoRepository.findAll();

        try {
            notificacaoService.deletarNotificacaoPorProdutoId(1);
        } catch (Exception e) {
            fail("Erro ao deletar Notificações: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        List<Notificacao> notificacoesAtualizado = notificacaoRepository.findAll();
        Integer delecoesEsperadas = 0;
        Integer delecoes = notificacoes.size() - notificacoesAtualizado.size();

        for(Notificacao n : notificacoes){
            if (n.getProduto().getId() == 1){
                delecoesEsperadas++;
            }
        }

        assertEquals(delecoes, delecoesEsperadas, "O numero de deleções é diferente do esperado");
    }

    //testar envio de alertas
}
