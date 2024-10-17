package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.DashboardMapper;
import grupo.terabite.terabite.dto.response.DashboardResponseDTO;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.service.DashboardService;
import grupo.terabite.terabite.service.LoteService;
import grupo.terabite.terabite.service.NotificacaoService;
import grupo.terabite.terabite.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("dashboard/")
public class DashboardController {

//    private final LoteService loteService;
//    private final ProdutoService produtoService;
//    private final NotificacaoService notificacaoService;
    private final DashboardService dashboardService;

    // para teste
    private final VendaProdutoRepository vendaProdutoRepository;

    @Operation(summary = "AMonta os dados que constituem a dashboard", description = "Uma lista de vendas conforme os mêses, previsão de venda com base em calor e produmos mais e menos vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard montada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public DashboardResponseDTO gerarDashboard(){
        return DashboardMapper.toResponseDTO(dashboardService.gerarDashboard());
    }

    @GetMapping("teste")
    public List<VendaProduto> testeDeQuery(){
        return vendaProdutoRepository.procurarVendasMesAtual();
    }
}
