package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.VendaCreateDTO;
import grupo.terabite.terabite.dto.mapper.VendaMapper;
import grupo.terabite.terabite.dto.response.VendaResponseDTO;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.service.ProdutoService;
import grupo.terabite.terabite.service.VendaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vendas")
public class VendaController {

    private final VendaService service;
    private final ProdutoService produtoService;

    @Operation(summary = "Lista todas as vendas feitas", description = "Retorna uma lista de vendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, vendas listadas"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem vendas"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<VendaResponseDTO>> listarVenda() {
        List<VendaResponseDTO> vendas = new ArrayList<>();
        for(Venda v :service.listarVenda()){
            vendas.add(VendaMapper.toResponseDTO(v, service));
        }
        if (vendas.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return ResponseEntity.ok(vendas);
    }

    @Operation(summary = "Busca uma venda pelo ID", description = "Retorna uma venda com base no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, venda listadas"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhuma venda encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(VendaMapper.toResponseDTO(service.buscarVendaPorId(id), service));
    }

    @Operation(summary = "Registra uma venda", description = "Retorna a venda registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, venda registrada"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado")
    })
    @PostMapping
    public ResponseEntity<VendaResponseDTO> criarVenda(@RequestBody @Valid VendaCreateDTO novaVenda) {
        return ResponseEntity.status(201).body(VendaMapper.toResponseDTO(service.criarVenda(VendaMapper.toEntity(novaVenda, produtoService)), service));
    }

    @Operation(summary = "Atualiza uma venda pelo ID", description = "Retorna a venda atualizada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, venda atualizada"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, Parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VendaResponseDTO> atualizarVenda(@PathVariable Integer id, @RequestBody @Valid VendaCreateDTO atualizarVenda) {
        Venda vendaAtualizada = service.atualizarVenda(id, VendaMapper.toEntity(atualizarVenda, produtoService));
        return ResponseEntity.ok(VendaMapper.toResponseDTO(vendaAtualizada, service));
    }

    @Operation(summary = "Deleta uma venda pelo ID", description = "Retorna sucesso para a exclusão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, venda deletada"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarVenda(@PathVariable Integer id) {
        service.deletarVenda(id);
        return ResponseEntity.noContent().build();
    }
}
