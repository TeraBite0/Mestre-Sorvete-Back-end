package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.FornecedorRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.FornecedorMapper;
import grupo.terabite.terabite.dto.response.FornecedorResponseDTO;
import grupo.terabite.terabite.entity.Fornecedor;
import grupo.terabite.terabite.repository.FornecedorRepository;
import grupo.terabite.terabite.service.FornecedorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/fornecedores")
public class FornecedorController {

    private final FornecedorService FornecedorService;
    private final FornecedorRepository FornecedorRepository;

    @Operation(summary = "Lista todos Fornecedores", description = "Retorna a lista de Fornecedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, Fornecedores listadas"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, Sem Fornecedores"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<FornecedorResponseDTO>> listarFornecedors(){
        List<Fornecedor> fornecedores = FornecedorService.listarFornecedor();
        if(fornecedores.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }

        return ResponseEntity.ok(FornecedorMapper.toListResposeDto(fornecedores));
    }

    @Operation(summary = "Registra uma Fornecedor", description = "Retorna a Fornecedor registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos")
    })
    @PostMapping
    public ResponseEntity<FornecedorResponseDTO> criarFornecedor(@RequestBody @Valid FornecedorRequisitionDTO fornecedorCreateDTO) {
        return ResponseEntity.created(null).body(FornecedorMapper.toResponseDto(FornecedorService.criarFornecedor(FornecedorMapper.toCreateDto(fornecedorCreateDTO))));
    }
}
