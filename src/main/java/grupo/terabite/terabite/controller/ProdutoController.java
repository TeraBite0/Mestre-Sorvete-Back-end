package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.mapper.ProdutoMapper;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.dto.update.ProdutoUpdateDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.service.ProdutoService;
import grupo.terabite.terabite.service.RecomendacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private RecomendacaoService recomendacaoService;

    @Operation(summary = "Lista todos produtos", description = "Retorna uma lista com todos os produtos")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos listadps"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodos() {
        List<Produto> produtos = produtoService.listarProduto();
        if (produtos.isEmpty()) return ResponseEntity.noContent().build();
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toDetalhe).toList());
    }

    @Operation(summary = "Busca um produto pelo ID", description = "Retorna um produto com base no seu ID")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produto retornado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    public ResponseEntity<ProdutoResponseDTO> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(produtoService.buscarPorId(id)));
    }

    @GetMapping("/nome-produto")
    @Operation(summary = "Busca produtos, com um filtro opcional",
            description = "Retorna todos os produtos, ou retorna produtos conforme nome e/ou marca passados. Parâmetros: nomeProduto (Opcional), nomeMarca (Opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos retornados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    public ResponseEntity<List<Produto>> listarComFiltro(@RequestParam(required = false) String nomeProduto,
                                                         @RequestParam(required = false) String nomeMarca) {
        return null;
    }

    @Operation(summary = "Cria um produto", description = "Retorna o produto criado caso sucesso na criação")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),

    })
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return ResponseEntity.created(null).body(
                ProdutoMapper.toDetalhe(
                        produtoService.criarProduto(
                                ProdutoMapper.toCriarProduto(produtoCreateDTO),
                                produtoCreateDTO.getNomeMarca(),
                                produtoCreateDTO.getNomeTipo())));
    }

    @Operation(summary = "Atualiza um produto", description = "Retorna o produto atualizado caso sucesso na criação")
    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),
    })
    public ResponseEntity<ProdutoResponseDTO> atualizarProduto(
            @PathVariable Integer id,
            @RequestBody @Valid ProdutoUpdateDTO produtoUpdateDTO) {
        return ResponseEntity.status(201).body(
                ProdutoMapper.toDetalhe(
                        produtoService.atualizarProduto(id,
                                ProdutoMapper.toAtualizar(produtoUpdateDTO))));
    }

    @Operation(summary = "Busca a recomendação do dia atual", description = "Retorna o produto aleatório que representa a recomendação do dia")
    @GetMapping("/recomendacao-do-dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto que é a recomendação do dia"),
            @ApiResponse(responseCode = "204", description = "Operação sucedida, nenhum produto cadastrado")
    })
    public ResponseEntity<ProdutoResponseDTO> recomendacaoDoDia() {
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(recomendacaoService.recomendacaoDoDia()));
    }
    @Operation(summary = "Atualiza o produto da recomendação do dia atual", description = "Retorna o produto que representa a recomendação do dia atualizado")
    @PostMapping("/recomendacao-do-dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera e retorna o produto que é a recomendação do dia"),
            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    public ResponseEntity<ProdutoResponseDTO> alterarRecomendacaoDoDia(@RequestBody @Valid ProdutoUpdateDTO produtoNovo) {
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(recomendacaoService.alterarRecomendacaoDoDia(ProdutoMapper.toAtualizar(produtoNovo))));
    }
}
