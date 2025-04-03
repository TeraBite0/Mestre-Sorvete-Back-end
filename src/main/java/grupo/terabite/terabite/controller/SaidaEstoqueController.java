package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.mapper.SaidaEstoqueMapper;
import grupo.terabite.terabite.dto.requisition.SaidaEstoqueRequisitionDTO;
import grupo.terabite.terabite.dto.requisition.SaidaEstoqueRequisitionGroupDTO;
import grupo.terabite.terabite.dto.response.SaidaEstoqueResponseDTO;
import grupo.terabite.terabite.dto.response.SaidaEstoqueResponseGroupDTO;
import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.service.SaidaEstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/saidas-estoque")
@RequiredArgsConstructor
public class SaidaEstoqueController {
    private final SaidaEstoqueService saidaEstoqueService;

    @Operation(summary = "Lista todas saidas de estoque", description = "Retorna a saidas de estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Saida de estoque listadas"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem Saida de estoque"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<SaidaEstoqueResponseGroupDTO>> listarSaidas(){
        List<SaidaEstoque> saidaEstoques = saidaEstoqueService.listar();
        return ResponseEntity.ok(SaidaEstoqueMapper.toSaidaEstoqueResponseGroupDTO(saidaEstoques));
    }

    @Operation(summary = "Registra varias saidas de estoque", description = "Retorna a o grupo de saidas estoques criados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Saidas estoques criadas com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<List<SaidaEstoqueResponseGroupDTO>> criarMarca(@RequestBody @Valid SaidaEstoqueRequisitionGroupDTO saidaEstoqueRequisitionGroupDTO) {
        return ResponseEntity.created(null).body(SaidaEstoqueMapper.toSaidaEstoqueResponseGroupDTO(saidaEstoqueService.registrarSaida(SaidaEstoqueMapper.toEntityList(saidaEstoqueRequisitionGroupDTO))));
    }

    @Operation(summary = "Atualiza uma saida de estoque", description = "Retorna a saida de estoque atualizada caso sucesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, saida de estoque criada"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhuma saida de estoque encontrada"),
            //@ApiResponse(responseCode = "409", description = "Saida de estoque duplicada"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<SaidaEstoqueResponseDTO> atualizarSaidaEstoque(@PathVariable Integer id, @RequestBody @Valid SaidaEstoqueRequisitionDTO saidaEstoqueRequisitionDTO) {
        return ResponseEntity.ok(SaidaEstoqueMapper.toSaidaEstoqueResponseDTO(saidaEstoqueService.editarSaida(id, SaidaEstoqueMapper.toSaidaEstoque(saidaEstoqueRequisitionDTO))));
    }

    @Operation(summary = "Deleta um conjunto de saidas Estoque por seus IDs", description = "Deleta as saidas estoques e retorna o sucesso da exclusão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Saidas estoques deletadas com sucesso"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Saida Estoque(s) especificada(s) não encontrada")
    })
    @DeleteMapping()
    public ResponseEntity<Void> deletarSaidasEstoque(@RequestBody @Valid SaidaEstoqueRequisitionGroupDTO saidaEstoqueRequisitionGroupDTO) {
        saidaEstoqueService.deletarSaidas(SaidaEstoqueMapper.toEntityList(saidaEstoqueRequisitionGroupDTO));
        return ResponseEntity.noContent().build();
    }
}
