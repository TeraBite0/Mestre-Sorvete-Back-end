package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.requisition.ProdutoRequisitionDTO;
import grupo.terabite.terabite.dto.mapper.ProdutoMapper;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.service.ProdutoService;
import grupo.terabite.terabite.service.api.AwsBucketService;
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
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    private final AwsBucketService awsBucketService;

    private final ProdutoMapper produtoMapper = new ProdutoMapper(awsBucketService);

    @Operation(summary = "Lista todos produtos", description = "Retorna uma lista com todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos listadps"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<Produto> produtos = produtoService.listarProduto();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(produtos.stream().map(produtoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Lista todos produtos que estão ativos em ordem alfabética", description = "Retorna uma lista com todos os produtos que estão ativos em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
    })
    @GetMapping("/ativos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosIsAtivo() {
        List<Produto> produtos = produtoService.listarProdutoIsAtivos();
        return ResponseEntity.ok(produtos.stream().map(produtoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Busca um produto pelo ID", description = "Retorna um produto com base no seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produto retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(produtoMapper.toResponseDto(produtoService.buscarPorId(id)));
    }

    @Operation(summary = "Busca produtos, com base no nome ou tipo", description = "Retorna todos os produtos conforme nome ou tipo passados. Parâmetros: nomeProduto (Opcional), nomeTipo (Opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos retornados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    @GetMapping("/filtrar-nome-tipo")
    public ResponseEntity<List<ProdutoResponseDTO>> listarComFiltroTipoOuNome(@RequestParam(required = false) String termo) {
        List<Produto> produtos = produtoService.buscarPorFiltroTipoOuNome(termo, termo);
        if (produtos.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        return ResponseEntity.ok(produtos.stream().map(produtoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Lista produtos, com base no termo passado", description = "Retorna todos os produtos conforme nome e/ou marca passados. Parâmetros: nomeProduto (Opcional), nomeMarca (Opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos retornados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    @GetMapping("/filtrar-nome-marca")
    public ResponseEntity<List<ProdutoResponseDTO>> pesquisarPorNomeProuduto(@RequestParam @Valid String termo) {
        List<Produto> produtos = produtoService.buscarPorTermo(termo, termo);
        if (produtos.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        return ResponseEntity.ok(produtos.stream().map(produtoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Busca um produto pelo nome com IgnoreCase", description = "Retorna um produto com base no seu nome passado como variável")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produto retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    @GetMapping("/nome/{nome}")
    public ResponseEntity<ProdutoResponseDTO> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(produtoMapper.toResponseDto(produtoService.buscarPorNomeProduto(nome)));
    }

    @Operation(summary = "Cria um produto", description = "Retorna o produto criado caso sucesso na criação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoRequisitionDTO produtoRequisitionDTO) {
        return ResponseEntity.created(null).body(
                ProdutoMapper.toResponseDto(
                        produtoService.criarProduto(
                                produtoMapper.toCreateProduto(produtoRequisitionDTO),
                                produtoRequisitionDTO.getNomeMarca(),
                                produtoRequisitionDTO.getNomeSubtipo())));
    }

    @Operation(summary = "Atualiza um produto", description = "Retorna o produto atualizado caso sucesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Integer id, @RequestBody @Valid ProdutoRequisitionDTO produtoUpdateDTO) {
        return ResponseEntity.ok(produtoMapper.toResponseDto(produtoService.atualizarProduto(id, produtoMapper.toCreateProduto(produtoUpdateDTO), produtoUpdateDTO.getNomeMarca(), produtoUpdateDTO.getNomeSubtipo())));
    }

    @Operation(summary = "Ativa ou desativa um produto", description = "Retorna o produto atualizado caso sucesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
    })
    @PatchMapping("/ativar/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProdutoAtivo(@PathVariable Integer id, @RequestParam boolean isAtivo) {
        return ResponseEntity.ok(produtoMapper.toResponseDto(produtoService.atualizarProdutoAtivo(id, isAtivo)));
    }

    @Operation(summary = "Define o produto disponivel ou indisponivel para agendamento", description = "Retorna o produto atualizado caso sucesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
    })
    @PatchMapping("/disponivel/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProdutoDisponivel(@PathVariable Integer id, @RequestParam boolean isDisponivel) {
        return ResponseEntity.ok(produtoMapper.toResponseDto(produtoService.atualizarProdutoDisponivel(id, isDisponivel)));
    }
}
