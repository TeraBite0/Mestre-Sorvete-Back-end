package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.TipoRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.TipoMapper;
import grupo.terabite.terabite.dto.response.TipoResponseDTO;
import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.service.TipoService;
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
@RequestMapping("/tipos")
@RequiredArgsConstructor
public class TipoController {

    private final TipoService tipoService;

    @Operation(summary = "Lista todos tipos", description = "Retorna a lista de tipos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Tipos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem Tipos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<TipoResponseDTO>> listarTipos(){
        List<Tipo> tipos = tipoService.listarTipo();
        return ResponseEntity.ok(tipos.stream().map(TipoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Registra um tipo", description = "Retorna o tipo registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tipo criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<TipoResponseDTO> criarTipo(@RequestBody @Valid TipoRequisitionDTO tipoCreateDTO) {
        return ResponseEntity.created(null).body(TipoMapper.toResponseDto(tipoService.criarTipo(TipoMapper.toCreateDto(tipoCreateDTO))));
    }
}
