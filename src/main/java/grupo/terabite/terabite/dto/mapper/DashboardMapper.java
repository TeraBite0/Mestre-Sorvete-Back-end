package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.DashboardResponseDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.dashboard.*;

import java.util.ArrayList;
import java.util.List;

public class DashboardMapper {
//    public static DashboardResponseDTO toResponseDTO(Dashboard dashboard){
//        if(dashboard == null) return null;
//
//        List<DashboardResponseDTO.ResumoDoMes> resumoDoMesesDto = new ArrayList<>();
//        List<DashboardResponseDTO.PrevisaoVendasPorTemperatura> previsaoVendasPorTemperaturaDto = new ArrayList<>();
//        List<DashboardResponseDTO.ProdutoVendido> produtosMaisVendidosDto = new ArrayList<>();
//        List<DashboardResponseDTO.ProdutoVendido> produtosMenosVendidosDto = new ArrayList<>();
//        List<DashboardResponseDTO.ProdutoEstoque> produtosBaixoEstoqueDto = new ArrayList<>();
//
//        for(ResumoDoMes rdm : dashboard.getResumoDeVendas()){
//            resumoDoMesesDto.add(DashboardResponseDTO.ResumoDoMes.builder()
//                            .data(rdm.getData())
//                            .faturamento(rdm.getFaturamento())
//                            .temperaturaMedia(rdm.getTemperaturaMedia())
//                    .build());
//        }
//
//        for(PrevisaoVendasPorTemperatura pvpt: dashboard.getPrevisaoVendasPorTemperatura()){
//            previsaoVendasPorTemperaturaDto.add(DashboardResponseDTO.PrevisaoVendasPorTemperatura.builder()
//                            .data(pvpt.getData())
//                            .porcentagemVenda(pvpt.getPorcentagemVenda())
//                    .build());
//        }
//
//        for(ProdutoVendido pv: dashboard.getProdutosMaisVendidos()){
//            produtosMaisVendidosDto.add(DashboardResponseDTO.ProdutoVendido.builder()
//                            .nome(pv.getNome())
//                            .qtdVendido(pv.getQtdVendido())
//                    .build());
//        }
//
//        for(ProdutoVendido pv: dashboard.getProdutosMenosVendidos()){
//            produtosMenosVendidosDto.add(DashboardResponseDTO.ProdutoVendido.builder()
//                    .nome(pv.getNome())
//                    .qtdVendido(pv.getQtdVendido())
//                    .build());
//        }
//
//        for(ProdutoEstoque pe : dashboard.getProdutosEstoque()){
//            produtosBaixoEstoqueDto.add(DashboardResponseDTO.ProdutoEstoque.builder()
//                            .nome(pe.getNome())
//                            .marca(pe.getMarca())
//                            .valorUnitario(pe.getValorUnitario())
//                            .qtdEmEstoque(pe.getQtdEmEstoque())
//                    .build());
//        }
//
//        return null;
//    }
}
