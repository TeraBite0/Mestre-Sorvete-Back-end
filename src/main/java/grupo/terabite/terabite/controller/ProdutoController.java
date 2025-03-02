package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.mapper.ProdutoMapper;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.dto.update.ProdutoUpdateDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.service.MarcaService;
import grupo.terabite.terabite.service.ProdutoService;
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
@RequiredArgsConstructor
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final SubtipoService subtipoService;
    private final MarcaService marcaService;

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
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toResponseDto).toList());
    }

    @Operation(summary = "Lista todos produtos que estão ativos em ordem alfabética", description = "Retorna uma lista com todos os produtos que estão ativos em ordem alfabética")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos listados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    @GetMapping("/isAtivos")
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosIsAtivo() {
        List<Produto> produtos = produtoService.listarProdutoIsAtivos();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toResponseDto).toList());
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
        return ResponseEntity.ok(ProdutoMapper.toResponseDto(produtoService.buscarPorId(id)));
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
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toResponseDto).toList());
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
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toResponseDto).toList());
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
        return ResponseEntity.ok(ProdutoMapper.toResponseDto(produtoService.buscarPorNomeProduto(nome)));
    }

    @Operation(summary = "Cria um produto", description = "Retorna o produto criado caso sucesso na criação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(@RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return ResponseEntity.created(null).body(
                ProdutoMapper.toResponseDto(
                        produtoService.criarProduto(
                                ProdutoMapper.toCreateProduto(produtoCreateDTO),
                                produtoCreateDTO.getNomeMarca(),
                                produtoCreateDTO.getNomeSubtipo())));
    }

    @Operation(summary = "Atualiza um produto", description = "Retorna o produto atualizado caso sucesso na criação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(@PathVariable Integer id, @RequestBody @Valid ProdutoUpdateDTO produtoUpdateDTO) {
        return ResponseEntity.ok(ProdutoMapper.toResponseDto(produtoService.atualizarProduto(id, ProdutoMapper.toAtualizar(produtoUpdateDTO, subtipoService, marcaService), produtoUpdateDTO.getNomeMarca(), produtoUpdateDTO.getNomeSubtipo())));
    }

//    RECOMENDAÇÃO E DESTAQUE



}
