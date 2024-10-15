package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Notificacao;
import grupo.terabite.terabite.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {

    private final NotificacaoRepository notificacaoRepository;
    private final ProdutoService produtoService;

    public List<Notificacao> listarNotificacoes() {
        List<Notificacao> notificacoes = notificacaoRepository.findAll();
        if (notificacoes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return notificacoes;
    }

    public Notificacao criarNotificacao(Notificacao novaNotificacao, String nomeProduto) {
        novaNotificacao.setProduto(produtoService.buscarPorNomeProduto(nomeProduto));
        return notificacaoRepository.save(novaNotificacao);
    }

    public void deletarNotificacao(Integer id) { // fazer cenário para caso o produto seja desativado
        if (!notificacaoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        notificacaoRepository.deleteById(id);
    }
}
