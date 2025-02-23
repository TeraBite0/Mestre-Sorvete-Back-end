package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.MarcaCreateDTO;
import grupo.terabite.terabite.dto.mapper.MarcaMapper;
import grupo.terabite.terabite.dto.response.MarcaResponseDTO;
import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.service.MarcaService;
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
@RequestMapping("/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @Operation(summary = "Lista todas marcas", description = "Retorna a lista de marcas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Marcas listadas"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem Marcas"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> listarMarcas(){
        List<Marca> marcas = marcaService.listarMarca();
        if(marcas.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        return ResponseEntity.ok(MarcaMapper.toListResposeDto(marcas));
    }

    @Operation(summary = "Registra uma marca", description = "Retorna a marca registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Marca criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<MarcaResponseDTO> criarMarca(@RequestBody @Valid MarcaCreateDTO marcaCreateDTO) {
        return ResponseEntity.created(null).body(MarcaMapper.toResponseDto(MarcaMapper.toCreateMarca(marcaCreateDTO)));
    }
}
