package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.SubtipoRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.SubtipoMapper;
import grupo.terabite.terabite.dto.response.SubtipoResponseDTO;
import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.service.SubtipoService;
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
@RequestMapping("/subtipos")
@RequiredArgsConstructor
public class SubtipoController {

    private final SubtipoService subtipoService;

    @Operation(summary = "Lista todos subtipos", description = "Retorna a lista de subtipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Subtipos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem Subtipos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<SubtipoResponseDTO>> listarSubtipos(){
        List<Subtipo> subtipos = subtipoService.listarSubtipo();
        return ResponseEntity.ok(subtipos.stream().map(SubtipoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Registra um subtipo", description = "Retorna o subtipo registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Subtipo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<SubtipoResponseDTO> criarSubtipo(@RequestBody @Valid SubtipoRequisitionDTO subtipoRequisitionDTO) {
        return ResponseEntity.created(null).body(SubtipoMapper.toResponseDto(subtipoService.criarSubtipo(SubtipoMapper.toCreateDto(subtipoRequisitionDTO), subtipoRequisitionDTO.getNomeTipo())));
    }

    @Operation(summary = "Deleta um subtipo", description = "Deleta a subtipo registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marca deletada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Subtipo associado a um ou mais produtos")
    })
    @DeleteMapping
    public ResponseEntity<Void> deletarSubtipo(@RequestBody @Valid Integer id) {
        subtipoService.deletarSubtipo(id);
        return ResponseEntity.noContent().build();
    }
}
