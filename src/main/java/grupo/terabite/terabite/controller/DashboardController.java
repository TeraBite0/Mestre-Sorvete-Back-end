package grupo.terabite.terabite.controller;

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

@RequiredArgsConstructor
@RestController
@RequestMapping("dashboard/")
public class DashboardController {

    private final LoteService loteService;
    private final ProdutoService produtoService;
    private final NotificacaoService notificacaoService;

    @Operation(summary = "AMonta os dados que constituem a dashboard", description = "Uma lista de vendas conforme os mêses, previsão de venda com base em calor e produmos mais e menos vendidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    @GetMapping
    public void gerarDashboard(){
        return;
    }
}
