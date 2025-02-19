package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Vendas")
class VendaServiceTest {

    @Mock
    private VendaRepository vendaRepository;

    @Mock
    private VendaProdutoRepository vendaProdutoRepository;

    @Mock
    private LoteService loteService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private VendaService vendaService;

    List<Marca> marcas;
    List<Tipo> tipos;
    List<Subtipo> subtipos;
    List<Produto> produtos;
    List<VendaProduto> vendasProdutos;
    List<Venda> vendas;

    @BeforeEach
    protected void setup() {
        marcas = List.of(
                new Marca(1, "Senhor Sorvete"),
                new Marca(2, "Artegel"),
                new Marca(3, "Cream Coler"),
                new Marca(4, "Fruta Brasileira")
        );

        tipos = List.of(
                new Tipo(1,"Cone"),
                new Tipo(2,"Palheta"),
                new Tipo(3,"Açai"),
                new Tipo(4,"Açai Pequeno")
        );

        subtipos = List.of(
                new Subtipo(1, tipos.get(0), "Palhetas"),
                new Subtipo(2, tipos.get(1), "Extrusado sem cobertura"),
                new Subtipo(3, tipos.get(2), "Torpedinhos"),
                new Subtipo(4, tipos.get(3), "Infantil")
        );

        produtos = List.of(
                new Produto(1, "Gelo gelado", subtipos.get(0), marcas.get(0), 7.0, true, true, false, false),
                new Produto(2, "Gelo geladinho", subtipos.get(1), marcas.get(1), 7.0, true, true, false, false),
                new Produto(3, "Gelo quentinho", subtipos.get(2), marcas.get(2), 10.0, true, true, false, false),
                new Produto(4, "Gelo quente", subtipos.get(3), marcas.get(3), 10.0, true, true, false, false)
        );

        vendasProdutos = List.of(
                new VendaProduto(1, new Venda(LocalDateTime.now()), produtos.get(0), 10)
        );

        vendas = List.of(
                new Venda(1, LocalDateTime.now(), vendasProdutos),
                new Venda(2, LocalDateTime.now(), vendasProdutos),
                new Venda(3, LocalDateTime.now(), vendasProdutos),
                new Venda(4, LocalDateTime.now(), vendasProdutos)
        );
    }

    @Test
    @DisplayName("Quando o banco de dados não possui vendas, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExcecaoQuandoListarVendaEstiverVazia() {
        when(vendaRepository.findAllByOrderByDataCompraDesc()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.listarVenda());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui vendas, o serviço deve retornar a lista correta")
    void deveRetornarListarVendaQuandoExisitir() {
        when(vendaRepository.findAllByOrderByDataCompraDesc()).thenReturn(vendas);

        List<Venda> vendaRetornados;
        try{
            vendaRetornados = vendaService.listarVenda();
        } catch (Exception e) {
            fail("Erro ao buscar venda: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertEquals(vendas, vendaRetornados, "As listas retornadas não são iguais às esperadas");
        Mockito.verify(vendaRepository, Mockito.times(1)).findAllByOrderByDataCompraDesc();
    }

    @Test
    @DisplayName("Quando buscar venda por Id inexistente, deve lançar exceção 204 (NO_CONTENT)")
    void deveLancarExcecaoQuandoNaoTiverVendaComIdPassado() {
        when(vendaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.buscarVendaPorId(10));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "O status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando buscar por Id existente, deve retornar o tipo correspondente")
    void deveRetornarQuandoTiverVendaComIdPassado() {
        Venda vendaExistente = vendas.get(0);
        when(vendaRepository.findById(1)).thenReturn(Optional.of(vendaExistente));

        Venda resultado;
        try{
            resultado = vendaService.buscarVendaPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar venda com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "A venda retornada não deveria ser nula");
        assertEquals(vendaExistente.getId(), resultado.getId(), "O Id da venda retornada não está correto");
        assertEquals(vendaExistente.getDataCompra(), resultado.getDataCompra(), "A data da compra da venda retornada não está correto");
    }

    @Test
    @DisplayName("Deve lançar exception quando o produto estiver inativo, retornando 400 (BAD_REQUEST)")
    void deveLancarExcecaoQuandoProdutoEstiverInativoNaCriacaoDeVenda() {
        // Marca todos os produtos como inativos para validar a exceção.
        produtos.forEach(produto -> produto.setIsAtivo(false));
        when(loteService.produtoEmEstoque(produtos.get(0).getId())).thenReturn(2);

        ResponseStatusException exceptionAtivoInativo = assertThrows(ResponseStatusException.class, () -> vendaService.criarVenda(vendasProdutos));
        assertEquals(HttpStatus.BAD_REQUEST, exceptionAtivoInativo.getStatusCode());
    }

    @Test
    @DisplayName("Deve lançar exception quando a quantidade de venda for maior que o estoque disponível, retornando 400 (BAD_REQUEST)")
    void deveLancarExcecaoQuandoQuantidadeDeVendaExcederEstoqueDisponivel() {
        // Configura a quantidade de produtos vendidos como 50 para testar a condição de estoque insuficiente.
        vendasProdutos.forEach(venda -> venda.setQtdProdutosVendido(50));
        when(loteService.produtoEmEstoque(produtos.get(0).getId())).thenReturn(45);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.criarVenda(vendasProdutos));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
    }

    @Test
    @DisplayName("Deve atualizar o produto para sem estoque quando a diferença entre o estoque e a quantidade vendida for menor que 1")
    void deveAtualizarProdutoParaSemEstoqueQuandoEstoqueRestanteForMenorQueUmaUnidade() {
        int idProduto = produtos.get(0).getId();
        when(vendaRepository.save(any(Venda.class))).thenReturn(new Venda(LocalDateTime.now()));
        when(loteService.produtoEmEstoque(idProduto)).thenReturn(10);
        when(produtoService.buscarPorId(idProduto)).thenReturn(produtos.get(0));
        when(produtoService.atualizarProduto(idProduto, produtos.get(0))).thenReturn(produtos.get(0));

        Venda resultado;
        try{
            resultado = vendaService.criarVenda(vendasProdutos);
        } catch (Exception e) {
            fail("Erro ao buscar venda : " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Quando tiver uma lista de venda para cadastrar, deve cadastrar quando as vendas tiver produto ativo, quantida de estoque maior que quantidade de venda")
    void deveCadastrarListaDeVenda() {
        int idProduto = produtos.get(0).getId();
        when(vendaRepository.save(any(Venda.class))).thenReturn(new Venda(LocalDateTime.now()));
        when(loteService.produtoEmEstoque(idProduto)).thenReturn(200);

        Venda resultado;
        try{
            resultado = vendaService.criarVenda(vendasProdutos);
        } catch (Exception e) {
            fail("Erro ao buscar venda : " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Quando o banco de dados não possui vendas pelo produto, deve retornar lista vazia")
    void quandoNaoEncontradorProdutosPorVendaRetornaListaVazia() {
        when(vendaProdutoRepository.findByVendaId(Mockito.anyInt())).thenReturn(Collections.emptyList());

        List<VendaProduto> resultado;
        try{
            resultado = vendaService.buscarProdutosPorVenda(vendasProdutos.get(0).getId());
        } catch (Exception e) {
            fail("Erro ao buscar venda prdouto com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertEquals(new ArrayList<>(), resultado, "A venda retornada deveria ser vazia");
    }

    @Test
    @DisplayName("Quando o banco de dados possui vendas pelo produto pesquisado, deve retornar a lista correta")
    void deveBuscarProdutosPorVendaPassada() {
        when(vendaProdutoRepository.findByVendaId(Mockito.anyInt())).thenReturn(vendasProdutos);

        List<VendaProduto> resultado;
        try{
            resultado = vendaService.buscarProdutosPorVenda(vendasProdutos.get(0).getId());
        } catch (Exception e) {
            fail("Erro ao buscar venda produto com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "A venda retornada não deveria ser nula");
        assertEquals(vendasProdutos, resultado, "A lista encontrada não está igual a lista do banco");
    }

    @Test
    @DisplayName("Quando não existir venda por id para atualizar, deve lançar exceção com 404(NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoTerVendaParaAtualizar() {
        when(vendaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.atualizarVenda(10, vendasProdutos));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
    }

    @Test
    @DisplayName("Quando o produto não tiver em estoque, não deve atualizar venda, deve lançar exceção com 400(BAD_REQUEST)")
    void deveLancarExcecaoParaAtualizarVendaQuandoNaoTiverEmEstoque() {
        // Valida se o produto é inativo
        when(vendaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(vendas.get(0)));
        produtos.stream().forEach(produto -> {produto.setIsAtivo(false);});

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.atualizarVenda(1, vendasProdutos));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");

        // Valida se a quantidade de produtos vendidos é menor que 0 com
        vendasProdutos.stream().forEach(venda -> {venda.setQtdProdutosVendido(-1);});
        ResponseStatusException exceptionQtdProdutoVendidoMenorQueZero = assertThrows(ResponseStatusException.class, () -> vendaService.atualizarVenda(1, vendasProdutos));
        assertEquals(HttpStatus.BAD_REQUEST, exceptionQtdProdutoVendidoMenorQueZero.getStatusCode());
    }

    @Test
    @DisplayName("Quando existir venda por id no banco de dados, com produto ativo e quantidade de estoque acima da quantidade de venda, deve fazer a atualização")
    void deveAtualizarVendaQuandoProdutoAtivoEQuantidadeEstoqueOk() {
        when(vendaRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(vendas.get(1)));

        Venda resultado;
        try{
            resultado = vendaService.atualizarVenda(1, vendasProdutos);
        } catch (Exception e) {
            fail("Erro ao buscar venda prdouto com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado);
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para deletar uma venda, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirVendaPorIdPassadoNoMetodoDeletar(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> vendaService.deletarVenda(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um Id que existe no banco de dados, deve deletar a perda por id")
    void deveDeletarPerda() {
        Integer id = 1;
        when(vendaRepository.existsById(id)).thenReturn(true);

        try{
            vendaService.deletarVenda(id);
        } catch (Exception e) {
            fail("Erro ao buscar venda não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        verify(vendaRepository).existsById(id);
        verify(vendaProdutoRepository).deleteByVendaId(id);
        verify(vendaRepository).deleteById(id);
    }

    @Test
    @DisplayName("Quando não tiver lista venda por data, deve exibir lista vazia")
    void deveExibirListaVaziaQuandoNaoTiverVendaNaDataPassada() {
        LocalDate data = LocalDate.now();
        when(vendaRepository.findByDataCompraBetween(data.atStartOfDay(), data.atTime(LocalTime.MAX))).thenReturn(Collections.emptyList());

        List<Venda> resultado;
        try{
            resultado = vendaService.listarVendaPorData(data);
        }  catch (Exception e) {
            fail("Erro ao buscar venda não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertEquals(new ArrayList<>(), resultado, "A lista não está vazia");
        verify(vendaRepository, Mockito.times(1)).findByDataCompraBetween(data.atStartOfDay(), data.atTime(LocalTime.MAX));
    }

    @Test
    @DisplayName("Quando existir venda com a data passada, listar todas as vendas do dia")
    void deveExibirListaDasVendasQuandoTiverNaDataPassada() {
        LocalDate data = LocalDate.now();
        when(vendaRepository.findByDataCompraBetween(data.atStartOfDay(), data.atTime(LocalTime.MAX))).thenReturn(new ArrayList<>());

        List<Venda> resultado;
        try{
            resultado = vendaService.listarVendaPorData(data);
        }  catch (Exception e) {
            fail("Erro ao buscar venda não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "Lista de venda retoranda não está correta");
        verify(vendaRepository, Mockito.times(1)).findByDataCompraBetween(data.atStartOfDay(), data.atTime(LocalTime.MAX));
    }
}