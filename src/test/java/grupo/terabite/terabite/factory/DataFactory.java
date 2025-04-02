package grupo.terabite.terabite.factory;

import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;

public class DataFactory {

    protected List<Tipo> tipos;

    protected List<Subtipo> subtipos;

    protected List<Marca> marcas;

    protected List<Produto> produtos;

    protected List<Notificacao> notificacoes;

    protected List<Recomendacao> recomendacoes;

    protected List<Destaque> destaques;

    protected List<Fornecedor> fornecedores;

    protected Lote lote;

    @BeforeEach
    protected void setup() {
        tipos = List.of(
                new Tipo(1, "Congelados"),
                new Tipo(2, "Quente")
        );

        subtipos = List.of(
                new Subtipo(1, tipos.get(0), "Super-congelado"),
                new Subtipo(2, tipos.get(0), "Semi-congelado"),
                new Subtipo(3, tipos.get(1), "Quente")
        );

        marcas = List.of(
                new Marca(1, "Penguim"),
                new Marca(2, "Urso polar")
        );

        produtos = List.of(
                new Produto(1, "Gelo gelado", subtipos.get(0), marcas.get(1), 9.0, true, 10, 10, true, false, false),
                new Produto(2, "Gelo geladinho", subtipos.get(0), marcas.get(0), 6.0, true, 10, 10, true, false, false),
                new Produto(3, "Gelo quente", subtipos.get(2), marcas.get(1), 8.0, true, 10, 10, false, false, false),
                new Produto(4, "Gelo quentinho", subtipos.get(2), marcas.get(0), 10.0, true, 10, 10, false, false, false),
                new Produto(5, "Neve gelada", subtipos.get(1), marcas.get(1), 12.0, true, 10, 10, true, false, false),
                new Produto(6, "Neve geladinha", subtipos.get(1), marcas.get(0), 10.0, true, 10, 10, true, false, false),
                new Produto(7, "Neve quente", subtipos.get(2), marcas.get(1), 10.5, true, 10, 10, true, false, false),
                new Produto(8, "Neve quentinha", subtipos.get(2), marcas.get(0), 6.5, true, 10, 10, true, false, false)
        );

        this.notificacoes = List.of(
                new Notificacao(1, "test1@test.test", produtos.get(0)),
                new Notificacao(2, "test2@test.test", produtos.get(0)),
                new Notificacao(3, "test3@test.test", produtos.get(0)),
                new Notificacao(4, "test4@test.test", produtos.get(1)),
                new Notificacao(5, "test5@test.test", produtos.get(1))
        );

        this.recomendacoes = List.of(
                new Recomendacao(1, produtos.get(0)),
                new Recomendacao(2, produtos.get(1)),
                new Recomendacao(3, produtos.get(2)),
                new Recomendacao(4, produtos.get(3)),
                new Recomendacao(5, produtos.get(4))
        );

        this.destaques = List.of(
                new Destaque(1, produtos.get(0), LocalDate.now(), "Esse produto é MUITO BOM"),
                new Destaque(2, produtos.get(1), LocalDate.now().minusDays(1), "Esse produto é BOM"),
                new Destaque(3, produtos.get(2), LocalDate.now().minusDays(2), "Esse produto é ok..."),
                new Destaque(4, produtos.get(3), LocalDate.now().minusDays(3), "Esse produto nem é tão bom assim...")
        );

        this.fornecedores = List.of(
                new Fornecedor(1, "Fornecedor"),
                new Fornecedor(2, "Fornecedor2"),
                new Fornecedor(3, "Fornecedor3"),
                new Fornecedor(4, "Fornecedor4")
        );

        List<LoteProduto> loteProdutos = List.of(
                new LoteProduto(1, null, produtos.get(0), 10)
                // new LoteProduto(2, null, produtos.get(1), 20)
        );

        this.lote = new Lote(1, fornecedores.get(0), LocalDate.now(), LocalDate.now(), LocalDate.now(), 1000.0, LoteStatusEnum.AGUARDANDO_ENTREGA, null, loteProdutos);

        for (LoteProduto lp : loteProdutos) {
            lp.setLote(lote);
        }
    }
}
