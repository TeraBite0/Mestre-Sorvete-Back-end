package grupo.terabite.terabite.entity.dashboard;

// ESta classe não representa uma entidade do banco, mas sim um conjunto de outros dados processados de outras tabelas

import grupo.terabite.terabite.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Builder
@AllArgsConstructor
public class Dashboard {
    List<ResumoDoMes> resumoDeVendas;

    List<PrevisaoVendasPorTemperatura> previsaoVendasPorTemperatura;

    List<ProdutoVendido> produtosMaisVendidos;

    List<ProdutoVendido> produtosMenosVendidos;

    List<ProdutoEstoque> produtosEstoque;
}
