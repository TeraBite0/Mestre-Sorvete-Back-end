package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import grupo.terabite.terabite.service.api.EmailApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final EmailApiService emailApiService;

    public List<Notificacao> listarNotificacoes() {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        if (notificacoes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return notificacoes;
    }

    public void notificarProdutoEmEstoque(Produto produto) {
        if (/* produto.getEmEstoque() && */ produto.getIsAtivo()) {

            List<Notificacao> notificacoes = notificacaoRepository.findByProdutoId(produto.getId());
            if (!notificacoes.isEmpty()) {
                emailApiService.enviarAlertaDeProdutos(notificacoes);
            }
        } else {
            throw new RuntimeException("Erro ao notificar, produto inapto para notificar");
        }
    }

    public Notificacao criarNotificacao(Notificacao novaNotificacao) {

        // Validação de duplicatas
        if(!notificacaoRepository.findByEmailAndProdutoId(novaNotificacao.getEmail(), novaNotificacao.getProduto().getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        return notificacaoRepository.save(novaNotificacao);
    }

    public void deletarNotificacaoPorProdutoId(Integer produtoId) {
        notificacaoRepository.deleteByProdutoId(produtoId);
    }
}
