package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import grupo.terabite.terabite.service.api.EmailApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final EmailApiService emailApiService;
    private final ProdutoService produtoService;

    public List<Notificacao> listarNotificacoes() {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        if (notificacoes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return notificacoes;
    }

    public void notificarProdutoEmEstoque(Produto produto) {
        if (produto.getDisponivel() && produto.getIsAtivo()) {

            List<Notificacao> notificacoes = notificacaoRepository.findByProdutoId(produto.getId());
            if (!notificacoes.isEmpty()) {
                emailApiService.enviarAlertaDeProdutos(notificacoes);
            }
        } else {
            throw new RuntimeException("Erro ao notificar, produto inapto para notificar");
        }
    }

    public List<Notificacao> criarNotificacao(String email, List<Integer> produtosId) {

        // lista com produtos registrados notificação para este email (validação de duplicatas)
        List<Integer> produtosIdUsuario = notificacaoRepository.findByEmail(email).stream().map(Notificacao::getProduto).map(Produto::getId).toList();
        List<Notificacao> notificacoesSalvar = new ArrayList<>();

        for(Integer produtoId: produtosId){
            if(!produtosIdUsuario.contains(produtoId)){
                notificacoesSalvar.add(new Notificacao(null, email, produtoService.buscarPorId(produtoId)));
            }
        }

        return notificacaoRepository.saveAll(notificacoesSalvar);
    }

    public void deletarNotificacaoPorProdutoId(Integer produtoId) {
        notificacaoRepository.deleteByProdutoId(produtoId);
    }
}
