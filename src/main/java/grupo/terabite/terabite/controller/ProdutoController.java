package grupo.terabite.terabite.controller;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.mapper.ProdutoMapper;
import grupo.terabite.terabite.dto.mapper.ProdutoPopularesMapper;
import grupo.terabite.terabite.dto.response.ProdutoPopularesReponseDto;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.dto.update.ProdutoUpdateDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.service.MarcaService;
import grupo.terabite.terabite.service.ProdutoService;
import grupo.terabite.terabite.service.RecomendacaoService;
import grupo.terabite.terabite.service.SubtipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;
    @Autowired
    private RecomendacaoService recomendacaoService;

    @Autowired
    private SubtipoService subtipoService;

    @Autowired
    private MarcaService marcaService;

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
        return ResponseEntity.ok(produtos.stream().map(ProdutoMapper::toDetalhe).toList());
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
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(produtoService.buscarPorId(id)));
    }

    @Operation(summary = "Busca produtos, com um filtro opcional",
            description = "Retorna todos os produtos, ou retorna produtos conforme nome e/ou marca passados. Parâmetros: nomeProduto (Opcional), nomeMarca (Opcional)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operação bem-sucedida, produtos retornados"),
            @ApiResponse(responseCode = "204", description = "Operação bem-sucedida, sem produtos"),
            @ApiResponse(responseCode = "404", description = "Nenhum produto encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos")
    })
    @GetMapping("/nome-produto")
    public ResponseEntity<List<Produto>> listarComFiltro(@RequestParam(required = false) String nomeProduto,
                                                         @RequestParam(required = false) String nomeMarca) {
        return null;
    }

    @Operation(summary = "Cria um produto", description = "Retorna o produto criado caso sucesso na criação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Operação bem-sucedida, produto criado"),
            @ApiResponse(responseCode = "400", description = "Erro de requisição, parâmetros inválidos"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
            @ApiResponse(responseCode = "409", description = "Produto duplicado"),
    })
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criar(
            @RequestBody @Valid ProdutoCreateDTO produtoCreateDTO) {
        return ResponseEntity.created(null).body(
                ProdutoMapper.toDetalhe(
                        produtoService.criarProduto(
                                ProdutoMapper.toCriarProduto(produtoCreateDTO),
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
        return ResponseEntity.status(201).body(ProdutoMapper.toDetalhe(produtoService.atualizarProduto(id, ProdutoMapper.toAtualizar(produtoUpdateDTO, subtipoService, marcaService))));
    }

    @Operation(summary = "Busca a recomendação do dia atual", description = "Retorna o produto aleatório que representa a recomendação do dia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto que é a recomendação do dia"),
            @ApiResponse(responseCode = "204", description = "Operação sucedida, nenhum produto cadastrado")
    })
    @GetMapping("/recomendacao-do-dia")
    public ResponseEntity<ProdutoResponseDTO> recomendacaoDoDia() {
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(recomendacaoService.recomendacaoDoDia()));
    }

    @Operation(summary = "Atualiza o produto da recomendação do dia atual", description = "Retorna o produto que representa a recomendação do dia atualizado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Altera e retorna o produto que é a recomendação do dia"),
            @ApiResponse(responseCode = "404", description = "Produto inexistente"),
            @ApiResponse(responseCode = "401", description = "Erro de requisição, Não autorizado"),
    })
    @PostMapping("/recomendacao-do-dia")
    public ResponseEntity<ProdutoResponseDTO> alterarRecomendacaoDoDia(@RequestBody @Valid Integer produtoNovoId) {
        return ResponseEntity.ok(ProdutoMapper.toDetalhe(recomendacaoService.alterarRecomendacaoDoDia(produtoNovoId)));
    }

    @Operation(summary = "Busca os sorvetes mais populares no momento", description = "Retorna os 10 produtos mais populares que teve mais venda no momento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os produtos mais populares no momento"),
            @ApiResponse(responseCode = "204", description = "Operação sucedida, nenhum produto cadastrado")
    })
    @GetMapping("/populares")
    public ResponseEntity<List<ProdutoPopularesReponseDto>> populares() {
        List<Produto> produtos = produtoService.popular();
        if(produtos.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        return ResponseEntity.ok(produtos.stream().map(ProdutoPopularesMapper::toDetalheProdutoPopularDto).toList());
    }
}
