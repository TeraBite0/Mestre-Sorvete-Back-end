package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.DestaqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DestaqueService {
    private final ProdutoService produtoService;
    private final DestaqueRepository destaqueRepository;


    public Destaque alterarDestaque(Destaque destaqueNovo) {
        Produto produtoNovo = produtoService.buscarPorId(destaqueNovo.getProduto().getId()); // valida se o produto é inexistente por id
        LocalDate hoje = LocalDate.now();
        Destaque destaque = destaqueRepository.findByDtRecomendacao(hoje);

        if (destaque == null) { // valida se o destaque atual não foi gerado ainda
            destaque = new Destaque();
            destaque.setDtRecomendacao(hoje);
        }

        destaque.setProduto(produtoNovo);
        destaque.setTexto(destaqueNovo.getTexto());
        destaqueRepository.save(destaque);
        return destaque;
    }

    public Destaque destaque() {
        LocalDate hoje = LocalDate.now();
        Destaque destaque = destaqueRepository.findByDtRecomendacao(hoje);

        if (destaque == null) {
            List<Destaque> destaques = destaqueRepository.findAll();
            Produto produtoDoDia = gerarDestaque(destaques);
            String txt = """
                    Este sorvete é uma combinação irresistível de cremosidade e sabor, 
                    trazendo uma experiência única. 
                    Feito com uma base suave e refrescante, 
                    ele harmoniza perfeitamente com o sabor característico de seu ingrediente principal, 
                    proporcionando uma explosão de sabor e textura a cada porção.
                    """;
            destaque = new Destaque(null, produtoDoDia, hoje, txt); // colocar texto depois
            destaqueRepository.save(destaque);
            excluirDadosAntigos();
        }

        return destaque;
    }

    private Produto gerarDestaque(List<Destaque> destaques) {
        List<Produto> produtos = produtoService.listarProduto().stream().filter(Produto::getIsAtivo).collect(Collectors.toList());

        if (destaques.isEmpty() || produtos.size() <= destaques.size()) return produtoAleatorio(produtos);
        else {
            boolean produtoNovo;
            Produto produtoGerado;

            do {
                produtoNovo = true;
                produtoGerado = produtoAleatorio(produtos);

                for (Destaque d : destaques) {
                    if (d.getProduto().getId().equals(produtoGerado.getId())) {
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
        List<Destaque> destaques = destaqueRepository.findByDtRecomendacaoBefore(dtLimite);

        destaqueRepository.deleteAll(destaques);
    }
}
