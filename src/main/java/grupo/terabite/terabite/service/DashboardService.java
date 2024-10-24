package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.entity.dashboard.*;
import grupo.terabite.terabite.entity.hgapi.TemperaturaDia;
import grupo.terabite.terabite.entity.hgapi.TemperaturaMes;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.hgapi.TemperaturaDiaRepository;
import grupo.terabite.terabite.repository.hgapi.TemperaturaMesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class DashboardService {

    //Não consumir de outras services pois a dashboard é um caso isolado de toda regra de negócio, e também por possiveis exceptions não desejadas
    private final VendaProdutoRepository vendaProdutoRepository;
    private final ProdutoRepository produtoRepository;
    private final TemperaturaMesRepository temperaturaMesRepository;
    private final TemperaturaDiaRepository temperaturaDiaRepository;

    public Dashboard gerarDashboard() {
        List<ResumoDoMes> resumoDosMeses = getResumoDoMes();
        return Dashboard.builder()
                .resumoDeVendas(resumoDosMeses)
                .previsaoVendasPorTemperatura(getPrevisaoVendasPorTemperatura(resumoDosMeses))
                .produtosMaisVendidos(getProdutosMaisVendidos())
                .produtosMenosVendidos(getProdutosMenosVendidos())
                .produtosEstoque(getProdutosMenosEstoque())
                .build();
    }


    private List<ResumoDoMes> getResumoDoMes() {
        // PARTE 1

        List<ResumoDoMes> resumoDoMeses = new ArrayList<>();
        List<TemperaturaMes> temperaturaMeses = temperaturaMesRepository.findAll();
        List<VendaProduto> vendaProdutos = vendaProdutoRepository.findAll();
        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < 6; i++) { // LOOP PARA CALCULAR POR CADA MÊS

            LocalDate dataIteracao = hoje.minusMonths(i);
            Double faturamentoDoMes = 0.0;
            String dataResposta = dataIteracao.getMonthValue() + "/" + dataIteracao.getYear();
            Double temperaturaMediaMesIteracao = 0.0;

//            List<VendaProduto> vendaProdutosIteracao = vendaProdutoRepository.procurarVendasPorMesEAno(dataIteracao.getMonthValue(), dataIteracao.getYear()).stream()
//                    .sorted(Comparator.comparing(vendaProduto -> vendaProduto.getProduto().getId())).toList();
            List<VendaProduto> vendaProdutosIteracao = vendaProdutos.stream()
                    .filter(vendaProduto -> vendaProduto.getVenda().getDataCompra().getMonthValue() == dataIteracao.getMonthValue()).toList();

            for(VendaProduto vp : vendaProdutosIteracao){ // PARA CADA VENDA DESTE MÊS, INCREMENTE O FATURAMENTO COM O VALOR DAS VENDAS
                faturamentoDoMes += vp.getQtdProdutosVendido() * vp.getProduto().getPreco();
            }

            for(TemperaturaMes tm : temperaturaMeses){
                if(tm.getDtMes().getMonthValue() == dataIteracao.getMonthValue()){
                    temperaturaMediaMesIteracao = tm.getTemperaturaMedia();
                }
            }

            resumoDoMeses.add(new ResumoDoMes(dataResposta, faturamentoDoMes, temperaturaMediaMesIteracao));
        }

        return resumoDoMeses;
    }

    private List<PrevisaoVendasPorTemperatura> getPrevisaoVendasPorTemperatura(List<ResumoDoMes> resumoDosMeses){
        // PARTE 2


        //APLICAR REGRA DE 3
        //SE EM TAL DIA EU VENDI X COM TEMPERATURA Y
        //HOJE EU VENDEREI XX COM TEMPERATURA YY

        LocalDate hoje = LocalDate.now();
        LocalDate mesPassado = hoje.minusDays(hoje.getDayOfMonth() - 1);
        List<TemperaturaDia> temperaturaDias = temperaturaDiaRepository.buscarPorMes(mesPassado);
        List<PrevisaoVendasPorTemperatura> previsaoVendasPorTemperaturas = new ArrayList<>();
        List<VendaProduto> vendaProdutos = vendaProdutoRepository.procurarVendasPorMesEAno(mesPassado.getMonthValue(), mesPassado.getYear());
        List<Double> previsoes = new ArrayList<>();

        for(TemperaturaDia td : temperaturaDias){
            List<VendaProduto> vendaProdutosDiaIteracao = vendaProdutos.stream().filter(vendaProduto -> {return vendaProduto.getVenda().getDataCompra().toLocalDate().isAfter(td.getDtTemperatura());}).toList();
            Double x = null;
            Double y = null;
            Double xx = null; // QUERO DESCOBRIR
            Double yy = null; // FAZER REQUISIÇÃO DA TEMPERATURA
        }

        return previsaoVendasPorTemperaturas;
    }

    private List<ProdutoVendido> getProdutosMaisVendidos(){
        // PARTE 3

        List<ProdutoVendido> produtoMaisVendidos= new ArrayList<>();

        return produtoMaisVendidos;
    }

    private List<ProdutoVendido> getProdutosMenosVendidos(){
        // PARTE 4

        List<ProdutoVendido> produtoMenosVendidos = new ArrayList<>();

        return produtoMenosVendidos;
    }

    private List<ProdutoEstoque> getProdutosMenosEstoque() {
        // PARTE 5

        List<ProdutoEstoque> produtosMenosEstoques = new ArrayList<>();

        return produtosMenosEstoques;
    }
}
