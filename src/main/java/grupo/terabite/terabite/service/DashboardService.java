package grupo.terabite.terabite.service;

import grupo.terabite.terabite.dto.external.ForecastExternalDTO;
import grupo.terabite.terabite.dto.internal.ProdutoQuantidadeDTO;
import grupo.terabite.terabite.entity.EstoqueProduto;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.entity.dashboard.*;
import grupo.terabite.terabite.entity.hgapi.TemperaturaDia;
import grupo.terabite.terabite.entity.hgapi.TemperaturaMes;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.hgapi.TemperaturaDiaRepository;
import grupo.terabite.terabite.repository.hgapi.TemperaturaMesRepository;
import grupo.terabite.terabite.service.api.HgApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DashboardService {

    //Não consumir de outras services pois a dashboard é um caso isolado de toda regra de negócio, e também por possiveis exceptions não desejadas
    private final VendaProdutoRepository vendaProdutoRepository;
    private final LoteService loteService;
    private final TemperaturaMesRepository temperaturaMesRepository;
    private final TemperaturaDiaRepository temperaturaDiaRepository;
    private final HgApiService hgApiService;

    public Dashboard gerarDashboard() {
        LocalDate hoje = LocalDate.now();
        List<ResumoDoMes> resumoDosMeses = getResumoDoMes(hoje);
        List<ProdutoQuantidadeDTO> produtoQuantidadeDTOS = vendaProdutoRepository.qtdVendidosPorMesEAno(hoje.getMonthValue(), hoje.getYear());

        return Dashboard.builder()
                .resumoDeVendas(resumoDosMeses)
                .previsaoVendasPorTemperatura(getPrevisaoVendasPorTemperatura(hoje, resumoDosMeses))
                .produtosMaisVendidos(getProdutosMaisVendidos(produtoQuantidadeDTOS))
                .produtosMenosVendidos(getProdutosMenosVendidos(produtoQuantidadeDTOS))
                .produtosEstoque(getProdutosMenosEstoque())
                .build();
    }


    private List<ResumoDoMes> getResumoDoMes(LocalDate hoje) {
        // PARTE 1

        List<ResumoDoMes> resumoDoMeses = new ArrayList<>();
        List<TemperaturaMes> temperaturaMeses = temperaturaMesRepository.findAll();
        List<VendaProduto> vendaProdutos = vendaProdutoRepository.findAll();

        for (int i = 0; i < 6; i++) { // LOOP PARA CALCULAR POR CADA MÊS

            LocalDate dataIteracao = hoje.minusMonths(i);
            Double faturamentoDoMes = 0.0;
            String dataResposta = dataIteracao.getMonthValue() + "/" + dataIteracao.getYear();
            Double temperaturaMediaMesIteracao = 0.0;
            for (TemperaturaMes tm : temperaturaMeses) {
                if (tm.getDtMes().getMonthValue() == hoje.getMonthValue() && tm.getDtMes().getYear() == hoje.getYear()) {
                    temperaturaMediaMesIteracao = tm.getTemperaturaMedia();
                }
            }

            List<VendaProduto> vendaProdutosIteracao = vendaProdutos.stream()
                    .filter(vendaProduto -> vendaProduto.getVenda().getDataCompra().getMonthValue() == dataIteracao.getMonthValue()).toList();

            for (VendaProduto vp : vendaProdutosIteracao) { // PARA CADA VENDA DESTE MÊS, INCREMENTE O FATURAMENTO COM O VALOR DAS VENDAS
                faturamentoDoMes += vp.getQtdProdutosVendido() * vp.getProduto().getPreco();
            }

            for (TemperaturaMes tm : temperaturaMeses) {
                if (tm.getDtMes().getMonthValue() == dataIteracao.getMonthValue()) {
                    temperaturaMediaMesIteracao = tm.getTemperaturaMedia();
                }
            }

            resumoDoMeses.add(new ResumoDoMes(dataResposta, faturamentoDoMes, temperaturaMediaMesIteracao));
        }

        return resumoDoMeses;
    }

    private List<PrevisaoVendasPorTemperatura> getPrevisaoVendasPorTemperatura(LocalDate hoje, List<ResumoDoMes> resumoDosMeses) {
        // PARTE 2

        LocalDate mesPassado = hoje.minusDays(hoje.getDayOfMonth() - 1);
        List<TemperaturaDia> temperaturaDias = temperaturaDiaRepository.buscarPorMes(mesPassado);
        List<PrevisaoVendasPorTemperatura> previsaoVendasPorTemperaturas = new ArrayList<>();
        List<VendaProduto> vendaProdutos = vendaProdutoRepository.procurarVendasPorMesEAno(mesPassado.getMonthValue(), mesPassado.getYear());
        Double faturamentoDoMesPassado = vendaProdutos.stream().mapToDouble((p) -> p.getQtdProdutosVendido() * p.getProduto().getPreco()).sum();
        List<ForecastExternalDTO> previsoesTemperatura = hgApiService.buscarPrevisao().get().stream().limit(4).toList();
        Map<String, Double> previsoesVenda = new HashMap<>();

        for (TemperaturaDia td : temperaturaDias) {
            List<VendaProduto> vendaProdutosDiaIteracao = vendaProdutos.stream().filter(vendaProduto -> {
                return vendaProduto.getVenda().getDataCompra().toLocalDate().isAfter(td.getDtTemperatura());
            }).toList();
            Map<String, Double> previsoesVendaIteracao = new HashMap<>();

            // REGRA DE 3
            //SE EM TAL DIA EU VENDI X COM TEMPERATURA Y
            //HOJE EU VENDEREI XX COM TEMPERATURA YY

            Double x = vendaProdutosDiaIteracao.stream().mapToDouble((p) -> p.getQtdProdutosVendido() * p.getProduto().getPreco()).sum();
            Double y = td.getTemperaturaMedia();

            Double faturamentoPorTemperaturaUnitaria = x / y;

            for (ForecastExternalDTO f : previsoesTemperatura) {
                Double yy = (((Integer) f.getMax()).doubleValue() + ((Integer) f.getMin()).doubleValue()) / 2.0;
                Double xx = faturamentoPorTemperaturaUnitaria * yy;

                previsoesVendaIteracao.put(f.getDate(), xx);
            }

            // ESTE MAP MONSTRO CALCULA A PREVISAO DE VENDA MÉDIA COM BASE NOS VALORES INSERIDOS NO MAP "previsoesVendaIteracao"
            Map<String, Double> mediaPorData = previsoesVendaIteracao.entrySet().stream()
                    .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));

            previsoesVenda.putAll(mediaPorData);
        }

        previsoesVenda = previsoesVenda.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.averagingDouble(Map.Entry::getValue)));

        previsoesVenda.forEach((data, media) ->
                previsaoVendasPorTemperaturas.add(
                        PrevisaoVendasPorTemperatura.builder()
                                .data(data)
                                .porcentagemVenda(media / (faturamentoDoMesPassado / 30.00))
                                .build())
        );
        return previsaoVendasPorTemperaturas;
    }

    private List<ProdutoVendido> getProdutosMaisVendidos(List<ProdutoQuantidadeDTO> produtoQuantidadeDTOS) {
        // PARTE 3
        List<ProdutoQuantidadeDTO> produtoQuantidadeDTOMaisVendidos = produtoQuantidadeDTOS.stream().sorted((p1, p2) -> Long.compare(p2.getQtdVendida(), p1.getQtdVendida())).limit(5).toList();
        List<ProdutoVendido> produtoMaisVendidos = new ArrayList<>();

        for (ProdutoQuantidadeDTO dto : produtoQuantidadeDTOMaisVendidos) {
            produtoMaisVendidos.add(ProdutoVendido.builder()
                    .nome(dto.getProduto().getNome())
                    .qtdVendido(dto.getQtdVendida().intValue())
                    .build());
        }
        return produtoMaisVendidos;
    }

    private List<ProdutoVendido> getProdutosMenosVendidos(List<ProdutoQuantidadeDTO> produtoQuantidadeDTOS) {
        // PARTE 4

        List<ProdutoQuantidadeDTO> produtoQuantidadeDTOMenosVendidos = produtoQuantidadeDTOS.stream().sorted((p1, p2) -> Long.compare(p1.getQtdVendida(), p2.getQtdVendida())).limit(5).toList();
        List<ProdutoVendido> produtoMenosVendidos = new ArrayList<>();

        for (ProdutoQuantidadeDTO dto : produtoQuantidadeDTOMenosVendidos) {
            produtoMenosVendidos.add(ProdutoVendido.builder()
                    .nome(dto.getProduto().getNome())
                    .qtdVendido(dto.getQtdVendida().intValue())
                    .build());
        }

        return produtoMenosVendidos;
    }

    private List<ProdutoEstoque> getProdutosMenosEstoque() {
        // PARTE 5
        List<EstoqueProduto> estoqueProdutosMenores = loteService.estoque().stream().sorted(Comparator.comparingInt(EstoqueProduto::getQtdEstoque)).limit(20).toList();
        List<ProdutoEstoque> produtosMenosEstoques = new ArrayList<>();

        for (EstoqueProduto ep : estoqueProdutosMenores) {
            produtosMenosEstoques.add(ProdutoEstoque.builder()
                    .nome(ep.getProduto())
                    .marca(ep.getMarca())
                    .qtdEmEstoque(ep.getQtdEstoque())
                    .valorUnitario(ep.getValorUnitario())
                    .build());
        }

        return produtosMenosEstoques;
    }
}
