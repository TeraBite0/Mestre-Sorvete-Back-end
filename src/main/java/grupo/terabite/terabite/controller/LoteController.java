package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.LoteMapper;
import grupo.terabite.terabite.dto.requisition.LoteRequisitionDTO;
import grupo.terabite.terabite.dto.requisition.LoteStatusRequisitionDTO;
import grupo.terabite.terabite.dto.response.LoteResponseDTO;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lotes")
public class LoteController {

    private final LoteService loteService;

    private final LoteMapper loteMapper;

    @Operation(summary = "Busca um lote pelo ID", description = "Retorna um lote com base no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida, Lote retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum lote não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(loteMapper.toResponseDto(loteService.buscarPorId(id)));
    }

    @Operation(summary = "Busca um lote pelo ID de um produto", description = "Retorna lotes com base no ID do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida, Lotes retornados"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum lote não encontrado")
    })
    @GetMapping("produtos/{id}")
    public ResponseEntity<List<LoteResponseDTO>> listarPorIdProduto(@PathVariable Integer id) {
        List<Lote> lotes = loteService.listarPorIdProduto(id);
        if(lotes.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        return ResponseEntity.ok(lotes.stream().map(loteMapper::toResponseDto).toList());
    }

    @Operation(summary = "Registra um lote", description = "Retorna o lote registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lote registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @PostMapping
    public ResponseEntity<LoteResponseDTO> adicionarLote(@RequestBody LoteRequisitionDTO novoLote) {
        return ResponseEntity.created(null).body(loteMapper.toResponseDto(loteService.criarLote(LoteMapper.toEntity(novoLote), novoLote.getNomeFornecedor())));
    }

    @Operation(summary = "Atualiza um lote apartir de um id", description = "Retorna o lote atualizado com base no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> atualizarLote(@PathVariable Integer id, @RequestBody LoteRequisitionDTO loteAtualizado) {
        return ResponseEntity.ok(loteMapper.toResponseDto(loteService.atualizarLote(id, LoteMapper.toEntity(loteAtualizado))));
    }

    @Operation(summary = "Deleta um lote pelo ID", description = "Deleta lote e retorna o sucesso da exclusão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lote deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarLote(@PathVariable Integer id) {
        loteService.deletarLote(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza o status de um lote pelo ID", description = "Atualiza o status do lote e retorna o sucesso da atualização")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<LoteResponseDTO> atualizarStatusLote(@PathVariable Integer id, @RequestBody LoteStatusRequisitionDTO loteUpdateStatus) {
        return ResponseEntity.ok(loteMapper.toResponseDto(loteService.atualizarStatusLote(id, LoteMapper.toRequisitionUpdateStatusDTO(loteUpdateStatus))));
    }
}
