package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.repository.DestaqueRepository;
import grupo.terabite.terabite.repository.RecomendacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacaoService {

    private final ProdutoService produtoService;
    private final DestaqueRepository destaqueRepository;
    private final RecomendacaoRepository recomendacaoRepository;

    public List<Recomendacao> listarRecomendacoes(){
        List<Recomendacao> recomendacoes = recomendacaoRepository.findAll();

        if(recomendacoes.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        return recomendacoes;
    }

    public Recomendacao criarRecomendacao(Recomendacao recomendacao){
        if(!recomendacaoRepository.findByProdutoId(recomendacao.getProduto().getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        return recomendacaoRepository.save(recomendacao);
    }

    public Recomendacao atualizarRecomendacao(Integer id, Recomendacao recomendacao){
        if(!recomendacaoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        } else if(!recomendacaoRepository.findByProdutoId(recomendacao.getProduto().getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        recomendacao.setId(id);
        return recomendacaoRepository.save(recomendacao);
    }

    public void excluirRecomendacao(Integer id){
        if(!recomendacaoRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        recomendacaoRepository.deleteById(id);
    }

    public Destaque alterarRecomendacaoDoDia(Destaque destaque) {
        Produto produtoNovo = produtoService.buscarPorId(destaque.getProduto().getId()); // valida se o produto é inexistente por id
        LocalDate hoje = LocalDate.now();
        Destaque recomendacaoDoDia = destaqueRepository.findByDtRecomendacao(hoje);

        if (recomendacaoDoDia == null) { // valida se a recomendacao atual não foi gerada ainda
            recomendacaoDoDia = new Destaque();
            recomendacaoDoDia.setDtRecomendacao(hoje);
        }

        recomendacaoDoDia.setProduto(produtoNovo);
        recomendacaoDoDia.setTexto(destaque.getTexto());
        destaqueRepository.save(recomendacaoDoDia);
        return recomendacaoDoDia;
    }

    public Destaque recomendacaoDoDia() {
        LocalDate hoje = LocalDate.now();
        Destaque recomendacaoDoDia = destaqueRepository.findByDtRecomendacao(hoje);
        List<Destaque> recomendacoes = destaqueRepository.findAll();
        Produto produtoDoDia;

        if (recomendacaoDoDia == null) {
            produtoDoDia = gerarRecomendacaoDoDia(recomendacoes);
            recomendacaoDoDia = new Destaque(produtoDoDia, hoje);
            destaqueRepository.save(recomendacaoDoDia);
            excluirDadosAntigos();
        }
        return recomendacaoDoDia;
    }

    private Produto gerarRecomendacaoDoDia(List<Destaque> recomendacoes) {
        List<Produto> produtos = produtoService.listarProduto().stream().filter(Produto::getIsAtivo).collect(Collectors.toList());

        if (recomendacoes.isEmpty() || produtos.size() >= recomendacoes.size()) {
            return produtoAleatorio(produtos);
        } else {
            boolean produtoNovo;
            Produto produtoGerado;

            do {
                produtoNovo = true;
                produtoGerado = produtoAleatorio(produtos);

                for (Destaque r : recomendacoes) {
                    if (r.getProduto().getId().equals(produtoGerado.getId())) {
                        produtoNovo = false;
                        break;
                    }
                }
            }
            while (!produtoNovo);

            return produtoGerado;
        }
    }

    private Produto produtoAleatorio(List<Produto> produtos) {
        Double n = (Math.random() * produtos.size());
        return produtos.get(n.intValue());
    }

    private void excluirDadosAntigos() {
        LocalDate dtLimite = LocalDate.now().minusDays(7); // <- qtd de dias que definem uma recomendação antiga
        List<Destaque> recomendacoes = destaqueRepository.findByDtRecomendacaoBefore(dtLimite);
        for (Destaque r : recomendacoes) {
            destaqueRepository.deleteById(r.getId());
        }
    }
}
