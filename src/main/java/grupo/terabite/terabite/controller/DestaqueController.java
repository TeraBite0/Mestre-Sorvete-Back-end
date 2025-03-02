package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.DestaqueMapper;
import grupo.terabite.terabite.dto.response.DestaqueResponseDTO;
import grupo.terabite.terabite.dto.update.DestaqueUpdateDTO;
import grupo.terabite.terabite.service.DestaqueService;
import grupo.terabite.terabite.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos/destaque")
public class DestaqueController {

    private final ProdutoService produtoService;
    private final DestaqueService destaqueService;

    @Operation(summary = "Busca o destaque atual", description = "Retorna o produto aleatório que representa o destaque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto que é o destaque"),
            @ApiResponse(responseCode = "204", description = "Operação sucedida, nenhum produto cadastrado")
    })
    @GetMapping()
    public ResponseEntity<DestaqueResponseDTO> destaque() {
        return ResponseEntity.ok(DestaqueMapper.toDestaqueResponseDTO(destaqueService.destaque()));
    }

    @Operation(summary = "Atualiza o produto do destaque atual", description = "Retorna o produto que representa o destaque atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera e retorna o produto que é o destaque"),
            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    @PutMapping()
    public ResponseEntity<DestaqueResponseDTO> alterarDestaque(@RequestBody @Valid DestaqueUpdateDTO recomendacaoDTO) {
        return ResponseEntity.ok(DestaqueMapper.toDestaqueResponseDTO(destaqueService.alterarDestaque(DestaqueMapper.toDestaque(recomendacaoDTO, produtoService))));
    }
}
