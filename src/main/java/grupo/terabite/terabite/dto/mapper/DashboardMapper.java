package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.DashboardResponseDTO;
import grupo.terabite.terabite.entity.dashboard.*;

import java.util.ArrayList;
import java.util.List;

public class DashboardMapper {
    public static DashboardResponseDTO toResponseDTO(Dashboard dashboard) {
        if (dashboard == null) return null;

        List<DashboardResponseDTO.ResumoDoMes> resumoDoMesesDto = new ArrayList<>();
        List<DashboardResponseDTO.PrevisaoVendasPorTemperatura> previsaoVendasPorTemperaturaDto = new ArrayList<>();
        List<DashboardResponseDTO.ProdutoVendido> produtosMaisVendidosDto = new ArrayList<>();
        List<DashboardResponseDTO.ProdutoVendido> produtosMenosVendidosDto = new ArrayList<>();
        List<DashboardResponseDTO.ProdutoEstoque> produtosBaixoEstoqueDto = new ArrayList<>();

        for (ResumoDoMes rdm : dashboard.getResumoDeVendas()) {
            resumoDoMesesDto.add(new DashboardResponseDTO.ResumoDoMes(
                    rdm.getData(),
                    rdm.getFaturamento(),
                    rdm.getTemperaturaMedia()
            ));
        }

        for (PrevisaoVendasPorTemperatura pvpt : dashboard.getPrevisaoVendasPorTemperatura()) {
            previsaoVendasPorTemperaturaDto.add(new DashboardResponseDTO.PrevisaoVendasPorTemperatura(
                    pvpt.getData(),
                    pvpt.getPorcentagemVenda()
            ));
        }

        for (ProdutoVendido pv : dashboard.getProdutosMaisVendidos()) {
            produtosMaisVendidosDto.add(new DashboardResponseDTO.ProdutoVendido(
                    pv.getNome(),
                    pv.getQtdVendido()
            ));
        }

        for (ProdutoVendido pv : dashboard.getProdutosMenosVendidos()) {
            produtosMenosVendidosDto.add(new DashboardResponseDTO.ProdutoVendido(
                    pv.getNome(),
                    pv.getQtdVendido()
            ));
        }

        for (ProdutoEstoque pe : dashboard.getProdutosEstoque()) {
            produtosBaixoEstoqueDto.add(new DashboardResponseDTO.ProdutoEstoque(
                    pe.getNome(),
                    pe.getMarca(),
                    pe.getValorUnitario(),
                    pe.getQtdEmEstoque()
            ));
        }


        return DashboardResponseDTO.builder()
                .previsaoTemperatura(previsaoVendasPorTemperaturaDto)
                .resumoDeVendas(resumoDoMesesDto)
                .produtosMaisVendidos(produtosMaisVendidosDto)
                .produtosMenosVendidos(produtosMenosVendidosDto)
                .produtosBaixoEstoque(produtosBaixoEstoqueDto)
                .build();
    }
}
