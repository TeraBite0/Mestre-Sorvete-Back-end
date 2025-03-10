package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.autenticacao.dto.AzureTokenDto;
import grupo.terabite.terabite.service.api.AzureService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/azure")
public class AzureController {

    @Autowired
    AzureService azureService;

    @Operation(summary = "Gera um token SAS para o usuário conseguir realizar o upload de imagens para o blob do azure (válido por 1hr)", description = "Retorna um string que representa um token SAS")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida,Token gerado e retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    public ResponseEntity<AzureTokenDto> gerarTokenSas() {
        return ResponseEntity.ok(AzureTokenDto.builder().SasToken(azureService.gerarTokenSAS()).build());
    }
}
