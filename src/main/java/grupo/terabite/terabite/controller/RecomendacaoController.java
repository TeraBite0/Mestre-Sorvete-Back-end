package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.RecomendacaoRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.RecomendacaoMapper;
import grupo.terabite.terabite.dto.response.RecomendacaoResponseDTO;
import grupo.terabite.terabite.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/produtos/recomendacao")
public class RecomendacaoController {

    private final RecomendacaoMapper recomendacaoMapper;

    private final RecomendacaoService recomendacaoService;

    @Operation(summary = "Lista as recomendações", description = "Retorna a lista de produtos recomendados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retornado as recomendações"),
            @ApiResponse(responseCode = "204", description = "Operação sucedida, sem recomendações")
    })
    @GetMapping
    public ResponseEntity<List<RecomendacaoResponseDTO>> recomendacao() {
        return ResponseEntity.ok(recomendacaoService.listarRecomendacoes().stream().map(recomendacaoMapper::toRecomendacaoResponseDto).toList());
    }

//    @Operation(summary = "Cria recomendação", description = "Retorna o produto recomendado")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "cria e retorna o produto recomendado"),
//            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
//            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
//    })
//    @PostMapping
//    public ResponseEntity<RecomendacaoResponseDTO> criarRecomendacao(@RequestBody @Valid RecomendacaoRequisitionDTO recomendacaoDTO) {
//        return ResponseEntity.ok(RecomendacaoMapper.toRecomendacaoResponseDto(recomendacaoService.criarRecomendacao(recomendacaoDTO.getProdutoId())));
//    }

    @Operation(summary = "Atualiza o produto de uma recomendação", description = "Retorna o produto recomendado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera e retorna o produto recomendado"),
            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<RecomendacaoResponseDTO> alterarRecomendacao(@PathVariable Integer id, @RequestBody @Valid RecomendacaoRequisitionDTO recomendacaoDTO) {
        return ResponseEntity.ok(recomendacaoMapper.toRecomendacaoResponseDto(recomendacaoService.atualizarRecomendacao(id, recomendacaoDTO.getProdutoId())));
    }
}
