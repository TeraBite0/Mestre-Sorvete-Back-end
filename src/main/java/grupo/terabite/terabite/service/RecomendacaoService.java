package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.repository.RecomendacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomendacaoService {

    private final ProdutoService produtoService;
    private final RecomendacaoRepository recomendacaoRepository;

    public List<Recomendacao> listarRecomendacoes(){
        List<Recomendacao> recomendacoes = recomendacaoRepository.findAll();
        if(recomendacoes.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));

        return recomendacoes;
    }

    public Recomendacao criarRecomendacao(Recomendacao recomendacao){
        recomendacao.setProduto(produtoService.buscarPorId(recomendacao.getProduto().getId()));

        // fazer validação de produto inativo

        return recomendacaoRepository.save(recomendacao);
    }

    public Recomendacao atualizarRecomendacao(Integer id, Recomendacao recomendacao){
        if(!recomendacaoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        } else if(!recomendacaoRepository.findByProdutoId(recomendacao.getProduto().getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        recomendacao.setId(id);
        recomendacao.setProduto(produtoService.buscarPorId(recomendacao.getProduto().getId()));
        return recomendacaoRepository.save(recomendacao);
    }

    public void excluirRecomendacao(Integer id){
        if(!recomendacaoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        recomendacaoRepository.deleteById(id);
    }

}
