package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.DashboardMapper;
import grupo.terabite.terabite.dto.response.DashboardResponseDTO;
import grupo.terabite.terabite.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("dashboard/")
public class DashboardController {

    private final DashboardService dashboardService;

    @Operation(summary = "AMonta os dados que constituem a dashboard", description = "Uma lista de vendas conforme os mêses, previsão de venda com base em calor e produmos mais e menos vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dashboard montada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public DashboardResponseDTO gerarDashboard() {
        return DashboardMapper.toResponseDTO(dashboardService.gerarDashboard());
    }
}
