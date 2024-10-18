package grupo.terabite.terabite.entity.dashboard;

// ESta classe não representa uma entidade do banco, mas sim um conjunto de outros dados processados de outras tabelas
// Esta classe não provém de outras entidades por motivos de dados relacionais

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Dashboard {
    private List<ResumoDoMes> resumoDeVendas;

    private List<PrevisaoVendasPorTemperatura> previsaoVendasPorTemperatura;

    private List<ProdutoVendido> produtosMaisVendidos;

    private List<ProdutoVendido> produtosMenosVendidos;

    private List<ProdutoEstoque> produtosEstoque;
}
