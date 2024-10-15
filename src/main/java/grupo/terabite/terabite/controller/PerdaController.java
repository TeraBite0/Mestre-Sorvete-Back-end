package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.PerdaCreateDTO;
import grupo.terabite.terabite.dto.mapper.PerdaMapper;
import grupo.terabite.terabite.dto.response.PerdaResponseDTO;
import grupo.terabite.terabite.entity.Perda;
import grupo.terabite.terabite.service.PerdaService;
import grupo.terabite.terabite.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perdas")
public class PerdaController {

    @Autowired
    private PerdaService perdaService;

    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Lista todas as perdas", description = "Retorna todas perdas registradas")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, perda listadas"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem perdas"),
    })
    public ResponseEntity<List<PerdaResponseDTO>> listarPerda() {
        List<Perda> perdas = perdaService.listarPerda();
        if (perdas.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(perdas.stream().map(PerdaMapper::toResponsePerda).toList());
    }

    @Operation(summary = "Busca uma perda pelo ID", description = "Retorna uma perda com base no seu ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, perda retornada"),
            @ApiResponse(responseCode = "404", description = "Nenhumm perda encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    public ResponseEntity<PerdaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(PerdaMapper.toResponsePerda(perdaService.buscarPerdaPorId(id)));
    }

    @Operation(summary = "Registra uma perda", description = "Retorna a perda registrada")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perda criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    public ResponseEntity<PerdaResponseDTO> criarPerda(
            @RequestBody @Valid PerdaCreateDTO perdaCreateDTO) {
        return ResponseEntity.created(null).body(
                PerdaMapper.toResponsePerda(
                        perdaService.criarPerda(
                                PerdaMapper.toEntity(perdaCreateDTO, produtoService)
                        )));
    }

    @Operation(summary = "Atualiza uma perda", description = "Atualiza a perda especificada pelo ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perda atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perda não encontrada")
    })
    public ResponseEntity<PerdaResponseDTO> atualizarPerda(@PathVariable Integer id, @RequestBody Perda perdaAtualizada) {
        return ResponseEntity.ok(
                PerdaMapper.toResponsePerda(
                        perdaService.atualizarPerda(id, perdaAtualizada)));
    }

    @Operation(summary = "Deleta uma perda pelo ID", description = "Deleta a perda e retorna o sucesso da exclusão")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perda deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perda não encontrada")
    })
    public ResponseEntity<Void> deletarPerda(@PathVariable Integer id) {
        perdaService.deletarPerda(id);
        return ResponseEntity.noContent().build();
    }
}
