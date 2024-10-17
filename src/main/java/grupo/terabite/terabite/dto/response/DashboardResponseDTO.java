package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {
    List<ResumoDoMes> resumoDeVendas;

    List<PrevisaoVendasPorTemperatura> previsaoTemperatura;

    List<ProdutoVendido> produtosMaisVendidos;

    List<ProdutoVendido> produtosMenosVendidos;

    List<ProdutoEstoque> produtosBaixoEstoque;

    public class ResumoDoMes{
        private String data; // EXEMPLO 10/24 (MM/AA)

        private Double faturamento; //EXEMPLO 3.000,00

        private Double temperaturaMedia; // EXEMPLO 28.0
    }

    public class PrevisaoVendasPorTemperatura{
        private String data; // EXEMPLO 16/10 (DD/MM)

        private Double porcentagemVenda; // EXEMPLO -25.0 (%)
    }

    public class ProdutoVendido{
        private String nome;

        private Integer qtdVendido;
    }

    public class ProdutoEstoque{
        private String nome;

        private String marca;

        private Double valorUnitario;

        private Integer qtdEmEstoque;
    }
}
