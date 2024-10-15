package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.repository.RecomendacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecomendacaoService {

    @Autowired
    ProdutoService produtoService;

    @Autowired
    RecomendacaoRepository recomendacaoRepository;

    public Produto alterarRecomendacaoDoDia(Integer produtoNovoId){
        Produto produtoNovo = produtoService.buscarPorId(produtoNovoId); // valida se o produto é inexistente por id
        LocalDate hoje = LocalDate.now();
        Recomendacao recomendacaoDoDia = recomendacaoRepository.findByDtRecomendacao(hoje);

        if(recomendacaoDoDia == null){ // valida se a recomendacao atual não foi gerada ainda
            recomendacaoDoDia = new Recomendacao();
            recomendacaoDoDia.setDtRecomendacao(hoje);
        }

        recomendacaoDoDia.setProduto(produtoNovo);
        recomendacaoRepository.save(recomendacaoDoDia);
        return recomendacaoDoDia.getProduto();
    }

    public Produto recomendacaoDoDia() {
        LocalDate hoje = LocalDate.now();
        Recomendacao recomendacaoDoDia = recomendacaoRepository.findByDtRecomendacao(hoje);
        List<Recomendacao> recomendacoes = recomendacaoRepository.findAll();
        Produto produtoDoDia;

        if (recomendacaoDoDia == null) {
            produtoDoDia = gerarRecomendacaoDoDia(recomendacoes);
            recomendacaoDoDia = new Recomendacao(produtoDoDia, hoje);
            recomendacaoRepository.save(recomendacaoDoDia);
            excluirDadosAntigos();
        }
        return recomendacaoDoDia.getProduto();
    }

    private Produto gerarRecomendacaoDoDia(List<Recomendacao> recomendacoes) {
        List<Produto> produtos = produtoService.listarProduto().stream().filter(Produto::getIsAtivo).collect(Collectors.toList());

        if (recomendacoes.isEmpty() || produtos.size() >= recomendacoes.size()) {
            return produtoAleatorio(produtos);
        } else {
            boolean produtoNovo;
            Produto produtoGerado;

            do {
                produtoNovo = true;
                produtoGerado = produtoAleatorio(produtos);

                for (Recomendacao r : recomendacoes) {
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

    private void excluirDadosAntigos(){
        LocalDate dtLimite = LocalDate.now().minusDays(7); // <- qtd de dias que definem uma recomendação antida
        List<Recomendacao> recomendacoes = recomendacaoRepository.findByDtRecomendacaoBefore(dtLimite);
        for(Recomendacao r: recomendacoes){
            recomendacaoRepository.deleteById(r.getId());
        }
    }
}
