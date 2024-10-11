package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.EstoqueProdutoMapper;
import grupo.terabite.terabite.dto.response.EstoqueProdutoResponseDTO;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.service.LoteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/estoque")
public class LoteController {

    @Autowired
    private LoteService service;

    @Operation(summary = "Lista todos produtos com informações de estoque", description = "Retorna todos os produtos com lotes registrados")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida,Estoque listado com sucesso"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    public ResponseEntity<List<EstoqueProdutoResponseDTO>> listarEstoque() {
        return ResponseEntity.ok(service.estoque().stream().map(EstoqueProdutoMapper::toResponseDTO).toList());
    }

    @Operation(summary = "Busca um lote pelo ID", description = "Retorna um lote com base no seu ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem sucedida, Lote retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum lote não encontrado")
    })
    public ResponseEntity<Lote> buscarPorId(@PathVariable Integer id) {
        // return ResponseEntity.ok(service.buscarPorId(id));
        return null;
    }

    @Operation(summary = "Registra um lote", description = "Retorna o lote registrado")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lote registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    public ResponseEntity<Lote> adicionarLote(@RequestBody Lote novoLote) {
        return ResponseEntity.ok(service.criarLote(novoLote));
    }

    @Operation(summary = "Atualiza um lote apartir de um id", description = "Retorna o lote atualizado com base no seu ID")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lote atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    public ResponseEntity<Lote> atualizarLote(@PathVariable Integer id, @RequestBody Lote loteAtualizado) {
        return ResponseEntity.ok(service.atualizarLote(id, loteAtualizado));
    }

    @Operation(summary = "Deleta um lote pelo ID", description = "Deleta lote e retorna o sucesso da exclusão")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lote deletado com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Lote não encontrado")
    })
    public ResponseEntity<Void> deletarLote(@PathVariable Integer id) {
        service.deletarLote(id);
        return ResponseEntity.noContent().build();
    }
}