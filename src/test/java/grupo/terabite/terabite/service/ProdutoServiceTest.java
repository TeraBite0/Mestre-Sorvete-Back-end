package grupo.terabite.terabite.service;

import grupo.terabite.terabite.dto.internal.ProdutoQuantidadeDTO;
import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.VendaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Produtos")
class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private MarcaService marcaService;

    @Mock
    private SubtipoService subtipoService;

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendaProdutoRepository vendaProdutoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    List<Tipo> tipos;
    List<Subtipo> subtipos;
    List<Marca> marcas;
    List<Produto> produtos;
    List<Venda> vendas;
    List<VendaProduto> vendaProdutos;

    @BeforeEach
    protected void setup() {
        tipos = List.of(
                new Tipo(1, "Congelados"),
                new Tipo(2, "Quente")
        );

        subtipos = List.of(
                new Subtipo(1, tipos.get(0), "Super-congelado"),
                new Subtipo(2, tipos.get(0), "Semi-congelado"),
                new Subtipo(3, tipos.get(1), "Quente")
        );

        marcas = List.of(
                new Marca(1, "Penguim"),
                new Marca(2, "Urso polar")
        );

        produtos = List.of(
                new Produto(1, "Gelo gelado", subtipos.get(0), marcas.get(1), 9.0, true, false),
                new Produto(2, "Gelo geladinho", subtipos.get(0), marcas.get(0), 6.0, true, false),
                new Produto(3, "Gelo quente", subtipos.get(2), marcas.get(1), 8.0, false, true),
                new Produto(4, "Gelo quentinho", subtipos.get(2), marcas.get(0), 10.0, false, true),
                new Produto(5, "Neve gelada", subtipos.get(1), marcas.get(1), 12.0, true, true),
                new Produto(6, "Neve geladinha", subtipos.get(1), marcas.get(0), 10.0, true, true),
                new Produto(7, "Neve quente", subtipos.get(2), marcas.get(1), 10.5, true, true),
                new Produto(8, "Neve quentinha", subtipos.get(2), marcas.get(0), 6.5, true, true)
        );

        vendas = List.of(
                new Venda(LocalDateTime.now()),
                new Venda(LocalDateTime.now().minusDays(1)),
                new Venda(LocalDateTime.now().minusDays(10)),
                new Venda(LocalDateTime.now().minusDays(100)),
                new Venda(LocalDateTime.now().minusDays(100))
        );

        vendaProdutos = List.of(
                new VendaProduto(1, vendas.get(0), produtos.get(0), 2),
                new VendaProduto(1, vendas.get(0), produtos.get(0), 2),
                new VendaProduto(2, vendas.get(0), produtos.get(1), 1),
                new VendaProduto(3, vendas.get(0), produtos.get(2), 3),
                new VendaProduto(4, vendas.get(1), produtos.get(1), 5),
                new VendaProduto(5, vendas.get(1), produtos.get(3), 2),
                new VendaProduto(6, vendas.get(2), produtos.get(4), 7),
                new VendaProduto(7, vendas.get(2), produtos.get(5), 4),
                new VendaProduto(8, vendas.get(2), produtos.get(6), 1),
                new VendaProduto(9, vendas.get(3), produtos.get(2), 6),
                new VendaProduto(10, vendas.get(3), produtos.get(7), 2),
                new VendaProduto(11, vendas.get(3), produtos.get(0), 3),
                new VendaProduto(11, vendas.get(4), produtos.get(0), 7)
        );
    }

    @Test
    @DisplayName("Lista todos produtos")
    void listarProduto() {
        Mockito.when(produtoRepository.findAll()).thenReturn(produtos);

        List<Produto> produtosResposta = new ArrayList<>();

        try {
            produtosResposta = produtoService.listarProduto();
        } catch (Exception e) {
            fail("Erro ao listar Produtos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertEquals(produtos.size(), produtosResposta.size(), "A quantidade de produtos retornado é diferente do esperado");

        // Não faz sentido adicionar exception a este método seguindo nossa regra de negócio
        // Mockito.when(produtoRepository.findAll()).thenReturn(new ArrayList<>());
        // ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> produtoService.listarProduto());
        // assertEquals(HttpStatusCode.valueOf(204), exception.getStatusCode(), "O status da resposta não é o correto");
    }

    @Test
    @DisplayName("Lista todos produtos ativos")
    void listarProdutoIsAtivos() {
        Mockito.when(produtoRepository.findByIsAtivoTrue()).thenReturn(produtos.stream().filter(Produto::getIsAtivo).toList());
        produtos = produtos.stream().filter(Produto::getIsAtivo).toList();
        List<Produto> produtosResposta = new ArrayList<>();

        try {
            produtosResposta = produtoService.listarProdutoIsAtivos();
        } catch (Exception e) {
            fail("Erro ao listar Produtos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(produtosResposta, "Os produtos encontrados não podem ser nulo");
        assertEquals(produtos.size(), produtosResposta.size(), "A quantidade de produtos retornado é diferente do esperado");

        Mockito.when(produtoRepository.findByIsAtivoTrue()).thenReturn(new ArrayList<>());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> produtoService.listarProdutoIsAtivos(), "Nenhum produto deveria ser listado");
        assertEquals(HttpStatusCode.valueOf(204), exception.getStatusCode(), "O status da resposta não é o correto");
    }

    @Test
    @DisplayName("Busca por id corretamente")
    void buscarPorId() {
        Mockito.when(produtoRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocation -> {
                    Integer id = invocation.getArgument(0);
                    return produtos.stream()
                            .filter(vendaProduto -> vendaProduto.getId().equals(id))
                            .findFirst();
                });

        Produto produto = produtos.get(0);
        Produto produtoResposta = null;

        try {
            produtoResposta = produtoService.buscarPorId(produto.getId());
        } catch (Exception e) {
            fail("Erro ao buscar Produto: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(produtoResposta, "O produto encontrado não pode ser nulo");
        assertEquals(produto, produtoResposta, "O produto encontrado está incorreto");

        Mockito.when(produtoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> produtoService.buscarPorId(100), "Produto não deveria ser encontrado");
        assertEquals(HttpStatusCode.valueOf(204), exception.getStatusCode(), "O status da resposta não é o correto");
    }

    @Test
    @DisplayName("Cria corretamente")
    void criarProduto() {
        Produto produto = new Produto(100, "Gelo esquecido", subtipos.get(0), marcas.get(0), 5.0, null, null);
        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);
        Mockito.when(marcaService.buscarPorNomeMarca(Mockito.any())).thenReturn(marcas.get(0));
        Mockito.when(subtipoService.buscarPorNomeSubtipo(Mockito.any())).thenReturn(subtipos.get(0));
        Produto produtoResposta = null;

        try {
            produtoResposta = produtoService.criarProduto(produto, marcas.get(0).getNome(), subtipos.get(0).getNome());
        } catch (Exception e) {
            fail("Erro ao criar Produto: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(produtoResposta, "O produto criado não pode ser nulo");
        assertEquals(produto.getNome(), produtoResposta.getNome(), "Nome alterado na criação");
        assertEquals(marcas.get(0).getNome(), produtoResposta.getMarca().getNome(), "Nome da marca alterado na criação");
        assertEquals(subtipos.get(0).getNome(), produtoResposta.getSubtipo().getNome(), "Nome do subtipo alterado na criação");
        assertTrue(produtoResposta.getIsAtivo(), "IsAtivo deve ser verdadeiro na criação");
        assertFalse(produtoResposta.getEmEstoque(), "emEstoque deve ser false na criação");
    }


    @Test
    @DisplayName("Atualiza corretamente")
    void atualizarProduto() {
        Produto produto = produtos.get(1);
        Marca marca = marcas.get(0);
        Subtipo subtipo = subtipos.get(0);
        produto.setNome("Neve geladinha atualizada");
        produto.setMarca(marca);
        produto.setSubtipo(subtipo);
        produto.setPreco(13.33);
        produto.setIsAtivo(false);
        Produto produtoResposta = produto;
        produtoResposta.setEmEstoque(null);

        Mockito.when(produtoRepository.save(Mockito.any())).thenReturn(produto);
        Mockito.when(produtoRepository.findById(Mockito.anyInt()))
                .thenAnswer(invocation -> {
                    Integer id = invocation.getArgument(0);
                    return produtos.stream()
                            .filter(vendaProduto -> vendaProduto.getId().equals(id))
                            .findFirst();
                });

        try {
            produtoResposta = produtoService.atualizarProduto(2, produtoResposta);
        } catch (Exception e) {
            fail("Erro ao atualizar Produto: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(produtoResposta, "O produto atualizado não pode ser nulo");
        assertEquals(produto.getNome(), produtoResposta.getNome(), "O nome do produto não foi atualizado corretamente");
        assertEquals(marca, produtoResposta.getMarca(), "A marca do produto não foi atualizada corretamente");
        assertEquals(subtipo, produtoResposta.getSubtipo(), "O subtipo do produto não foi atualizado corretamente");
        assertEquals(produto.getPreco(), produtoResposta.getPreco(), "O preço do produto não foi atualizado corretamente");
        assertFalse(produtoResposta.getIsAtivo(), "O status de ativo do produto não foi atualizado corretamente");
        assertEquals(produto.getEmEstoque(), produtoResposta.getEmEstoque(), "O estoque não deveria ser atualizado");

        Mockito.when(produtoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> produtoService.atualizarProduto(1, null), "Não deve ser possivel atualizar um produto passando um id inválido como argumento");
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode(), "O código de erro HTTP está incorreto");
    }

    @Test
    @DisplayName("Lista todos produtos por termo")
    void buscarPorTermo() {
        Marca marca = marcas.get(0);

        try {
            Mockito.when(produtoRepository.findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getNome().toLowerCase().contains("neve")).toList()
                    );
            List<Produto> produtos = produtoService.buscarPorTermo("Neve", "");

            assertNotNull(produtos, "Os produtos encontrados não podem ser nulo");
            assertEquals(4, produtos.size(), "O número de produtos retornados não é o esperado");
            assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Neve gelada")), "Produto esperado não encontrado");
            assertTrue(produtos.stream().anyMatch(p -> p.getNome().equals("Neve quente")), "Produto esperado não encontrado");

            Mockito.when(produtoRepository.findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getMarca().getNome().toLowerCase().contains(marca.getNome().toLowerCase())).toList()
                    );
            produtos = produtoService.buscarPorTermo("", marca.getNome());

            assertNotNull(produtos, "Os produtos encontrados não podem ser nulo");
            assertEquals(4, produtos.size(), "O número de produtos retornados não é o esperado");
            assertTrue(produtos.stream().allMatch(p -> p.getMarca().equals(marca)), "Produto com marca inesperada encontrado");

            Mockito.when(produtoRepository.findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCase(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getNome().toLowerCase().contains("neve") ||
                                    p.getMarca().getNome().toLowerCase().contains(marca.getNome().toLowerCase())).toList()
                    );
            produtos = produtoService.buscarPorTermo("Gelo", marca.getNome());

            assertNotNull(produtos, "Os produtos encontrados não podem ser nulo");
            assertEquals(6, produtos.size(), "O número de produtos retornados não é o esperado");
        } catch (Exception e) {
            fail("Erro ao buscar Produtos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }
    }

    @Test
    @DisplayName("Busca por nome corretamente")
    void buscarPorNomeProduto() {
        Produto produto = null;

        try {
            Mockito.when(produtoRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(produtos.get(4));
            produto = produtoService.buscarPorNomeProduto("Neve Gelada");
        } catch (Exception e) {
            fail("Erro ao buscar Produtos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(produto, "O produto encontrado não pode ser nulo");
        assertNotNull(produto, "Produto não encontrado");
        assertEquals(produtos.get(4).getNome(), produto.getNome(), "Nome do produto retornado não é o esperado");

        Mockito.when(produtoRepository.findByNomeIgnoreCase(Mockito.any())).thenReturn(null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> produtoService.buscarPorNomeProduto(null), "Nenhum produto deve ser encontrado com nome null");

        assertEquals(404, exception.getStatusCode().value(), "Status da exceção não corresponde ao esperado");
    }

    @Test
    @DisplayName("Lista todos por tipo ou nome corretamente")
    void buscarPorFiltroTipoOuNome() {
        try {
            // Cenário 1: Buscar produtos por nome contendo o termo
            Mockito.when(produtoRepository.findByNomeIgnoreCaseContainingOrSubtipo_TipoPai_NomeIgnoreCaseContaining(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getNome().toLowerCase().contains("neve")).toList()
                    );
            List<Produto> produtosResposta = produtoService.buscarPorFiltroTipoOuNome("Neve", "");

            assertNotNull(produtosResposta, "Os produtos encontrados não podem ser nulo");
            assertEquals(4, produtosResposta.size(), "Cenário 1 falhou: Número de produtos retornados por nome incorreto");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Neve gelada")), "Cenário 1 falhou: Produto esperado não encontrado");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Neve quente")), "Cenário 1 falhou: Produto esperado não encontrado");

            // Cenário 2: Buscar produtos por tipo contendo o termo
            Mockito.when(produtoRepository.findByNomeIgnoreCaseContainingOrSubtipo_TipoPai_NomeIgnoreCaseContaining(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getSubtipo().getNome().toLowerCase().contains("quente")).toList()
                    );
            produtosResposta = produtoService.buscarPorFiltroTipoOuNome("", "Quente");

            assertNotNull(produtosResposta, "Os produtos encontrados não podem ser nulo");
            assertEquals(4, produtosResposta.size(), "Cenário 2 falhou: Número de produtos retornados por tipo incorreto");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Gelo quentinho")), "Cenário 2 falhou: Produto esperado não encontrado");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Gelo quente")), "Cenário 2 falhou: Produto esperado não encontrado");

            // Cenário 3: Buscar produtos por nome e tipo contendo o termo
            Mockito.when(produtoRepository.findByNomeIgnoreCaseContainingOrSubtipo_TipoPai_NomeIgnoreCaseContaining(Mockito.anyString(), Mockito.anyString()))
                    .thenReturn(
                            this.produtos.stream().filter(p -> p.getSubtipo().getNome().toLowerCase().contains("quente") ||
                                    p.getNome().toLowerCase().contains("neve")).toList()
                    );
            produtosResposta = produtoService.buscarPorFiltroTipoOuNome("Neve", "Quente");

            assertNotNull(produtosResposta, "Os produtos encontrados não podem ser nulo");
            assertEquals(6, produtosResposta.size(), "Cenário 3 falhou: Número de produtos retornados por nome e tipo incorreto");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Neve gelada")), "Cenário 3 falhou: Produto esperado não encontrado");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Neve quente")), "Cenário 3 falhou: Produto esperado não encontrado");
            assertTrue(produtosResposta.stream().anyMatch(p -> p.getNome().equals("Gelo quente")), "Cenário 3 falhou: Produto esperado não encontrado");

        } catch (Exception e) {
            fail("Erro ao buscar Produtos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }
    }

    @Test
    @DisplayName("Lista todos por populares corretamente")
    void popular() {
        Mockito.when(vendaProdutoRepository.qtdVendidosPorMesEAno(Mockito.any(), Mockito.any())).thenReturn(
                List.of( new ProdutoQuantidadeDTO(produtos.get(0),  10L),
                        new ProdutoQuantidadeDTO(produtos.get(2), 9L),
                        new ProdutoQuantidadeDTO(produtos.get(4), 7L),
                        new ProdutoQuantidadeDTO(produtos.get(1), 6L),
                        new ProdutoQuantidadeDTO(produtos.get(6), 5L),
                        new ProdutoQuantidadeDTO(produtos.get(7), 5L)
                        )
        );

        List<Produto> populares = null;

        try {
            populares = produtoService.popular();

        } catch (Exception e) {
            fail("Erro ao buscar Produtos Populares: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
        }

        assertNotNull(populares, "Os produtos encontrados não podem ser nulo");
        assertTrue(populares.size() <= 5, "Deve retornar no máximo 5 produtos.");
    }
}