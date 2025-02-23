package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.RecomendacaoDia;
import grupo.terabite.terabite.repository.RecomendacaoDiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecomendacaoDiaService {

    private final ProdutoService produtoService;
    private final RecomendacaoDiaRepository recomendacaoDiaRepository;

    public RecomendacaoDia alterarRecomendacaoDoDia(RecomendacaoDia recomendacaoDia) {
        Produto produtoNovo = produtoService.buscarPorId(recomendacaoDia.getProduto().getId()); // valida se o produto é inexistente por id
        LocalDate hoje = LocalDate.now();
        RecomendacaoDia recomendacaoDoDia = recomendacaoDiaRepository.findByDtRecomendacao(hoje);

        if (recomendacaoDoDia == null) { // valida se a recomendacao atual não foi gerada ainda
            recomendacaoDoDia = new RecomendacaoDia();
            recomendacaoDoDia.setDtRecomendacao(hoje);
        }

        recomendacaoDoDia.setProduto(produtoNovo);
        recomendacaoDoDia.setTexto(recomendacaoDia.getTexto());
        recomendacaoDiaRepository.save(recomendacaoDoDia);
        return recomendacaoDoDia;
    }

    public RecomendacaoDia recomendacaoDoDia() {
        LocalDate hoje = LocalDate.now();
        RecomendacaoDia recomendacaoDoDia = recomendacaoDiaRepository.findByDtRecomendacao(hoje);
        List<RecomendacaoDia> recomendacoes = recomendacaoDiaRepository.findAll();
        Produto produtoDoDia;

        if (recomendacaoDoDia == null) {
            produtoDoDia = gerarRecomendacaoDoDia(recomendacoes);
            recomendacaoDoDia = new RecomendacaoDia(produtoDoDia, hoje);
            recomendacaoDiaRepository.save(recomendacaoDoDia);
            excluirDadosAntigos();
        }
        return recomendacaoDoDia;
    }

    private Produto gerarRecomendacaoDoDia(List<RecomendacaoDia> recomendacoes) {
        List<Produto> produtos = produtoService.listarProduto().stream().filter(Produto::getIsAtivo).collect(Collectors.toList());

        if (recomendacoes.isEmpty() || produtos.size() >= recomendacoes.size()) {
            return produtoAleatorio(produtos);
        } else {
            boolean produtoNovo;
            Produto produtoGerado;

            do {
                produtoNovo = true;
                produtoGerado = produtoAleatorio(produtos);

                for (RecomendacaoDia r : recomendacoes) {
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
        LocalDate dtLimite = LocalDate.now().minusDays(7); // <- qtd de dias que definem uma recomendação antida
        List<RecomendacaoDia> recomendacoes = recomendacaoDiaRepository.findByDtRecomendacaoBefore(dtLimite);
        for (RecomendacaoDia r : recomendacoes) {
            recomendacaoDiaRepository.deleteById(r.getId());
        }
    }
}
