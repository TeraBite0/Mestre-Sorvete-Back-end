package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Builder
public class DashboardResponseDTO {
    List<ResumoDoMes> resumoDeVendas;

    List<PrevisaoVendasPorTemperatura> previsaoTemperatura;

    List<ProdutoVendido> produtosMaisVendidos;

    List<ProdutoVendido> produtosMenosVendidos;

    List<ProdutoEstoque> produtosBaixoEstoque;

    @Getter
    @Setter
    public static class ResumoDoMes{
        private String data; // EXEMPLO 10/24 (MM/AA)

        private Double faturamento; //EXEMPLO 3.000,00

        private Double temperaturaMedia; // EXEMPLO 28.0

        public ResumoDoMes(String data, Double faturamento, Double temperaturaMedia) {
            this.data = data;
            this.faturamento = faturamento;
            this.temperaturaMedia = temperaturaMedia;
        }
    }

    @Getter
    @Setter
    public static class PrevisaoVendasPorTemperatura{
        private String data; // EXEMPLO 16/10 (DD/MM)

        private Double porcentagemVenda; // EXEMPLO -25.0 (%)

        public PrevisaoVendasPorTemperatura(String data, Double porcentagemVenda) {
            this.data = data;
            this.porcentagemVenda = porcentagemVenda;
        }
    }

    @Getter
    @Setter
    public static class ProdutoVendido{
        private String nome;

        private Integer qtdVendido;

        public ProdutoVendido(String nome, Integer qtdVendido) {
            this.nome = nome;
            this.qtdVendido = qtdVendido;
        }
    }

    @Getter
    @Setter
    public static class ProdutoEstoque{
        private String nome;

        private String marca;

        private Double valorUnitario;

        private Integer qtdEmEstoque;

        public ProdutoEstoque(String nome, String marca, Double valorUnitario, Integer qtdEmEstoque) {
            this.nome = nome;
            this.marca = marca;
            this.valorUnitario = valorUnitario;
            this.qtdEmEstoque = qtdEmEstoque;
        }
    }
}
