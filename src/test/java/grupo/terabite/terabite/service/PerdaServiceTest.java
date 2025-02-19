package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.repository.PerdaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Perda")
class PerdaServiceTest {

    @Mock
    private PerdaRepository perdaRepository;

    @InjectMocks
    private PerdaService perdaService;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private LoteService loteService;

    List<Marca> marcas;
    List<Tipo> tipos;
    List<Subtipo> subtipos;
    List<Produto> produtos;
    List<Lote> lotes;
    List<Perda> perdasEsperadas;

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
                new Produto(3, "Gelo quentinho", subtipos.get(2), marcas.get(2), 10.0, false, true, false, false),
                new Produto(4, "Gelo quente", subtipos.get(3), marcas.get(3), 10.0, true, false, false, false));

        perdasEsperadas = List.of(
                new Perda(1, 2, LocalDate.now(), produtos.get(0)),
                new Perda(2, 3, LocalDate.now(), produtos.get(1)),
                new Perda(3, 4, LocalDate.now(), produtos.get(2)),
                new Perda(4, 1, LocalDate.now(), produtos.get(0)),
                new Perda(5, 1, LocalDate.now(), produtos.get(1))
        );

        lotes = List.of(
                new Lote(1,  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15), 50, 150.00, produtos.get(0)),
                new Lote(1,  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15), 50, 150.00, produtos.get(0)),
                new Lote(1,  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15), 50, 150.00, produtos.get(0)),
                new Lote(1,  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15),  LocalDate.of(2024, 10, 15), 50, 150.00, produtos.get(0))
        );
    }

    @Test
    @DisplayName("Quando o banco de dados não possui perdas, o serviço deve lançar ResponseStatusException com status 204")
    void deveLancarExcecaoQuandoNaoExistemPerdas() {
        when(perdaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.listarPerda());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui perdas, o serviço deve retornar a lista correta")
    void deveRetornarListaDePerdasQuandoExistirem() {
        when(perdaRepository.findAll()).thenReturn(perdasEsperadas);

        List<Perda> perdasRetornadas;

        try{
            perdasRetornadas = perdaService.listarPerda();
        } catch (Exception e) {
            fail("Erro ao buscar perda com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertIterableEquals(perdasEsperadas, perdasRetornadas, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoEncontrarPerdaPorID() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.buscarPerdaPorId(10));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
    }

    @Test
    @DisplayName("Quando buscar por ID existente, deve retornar a perda correspondente")
    void deveRetornarPerdaQuandoPassadoSeuId() {
        Perda perda = perdasEsperadas.get(0);
        when(perdaRepository.findById(1)).thenReturn(Optional.of(perda));

        Perda resultado;
        try{
            resultado = perdaService.buscarPerdaPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar perda com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "A perda retornada não deveria ser nula");
        assertEquals(perda.getId(), resultado.getId(), "O ID da perda retornada não está correto");
        assertEquals(perda.getQtdProduto(), resultado.getQtdProduto(), "A quantidade de perdas retornada não está correto");
        assertEquals(perda.getProduto(), resultado.getProduto(), "O produto que teve perda retornada não está correto");
    }

    @Test
    @DisplayName("Quando criar uma nova perda, deve definir o ID como null e salvar no repositório")
    void deveCriarNovaPerdaQuandoEstoqueForMenorQueUm() {
        Perda perda = perdasEsperadas.get(0);
        when(perdaRepository.save(perda)).thenReturn(perdasEsperadas.get(0));
        when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(produtos.get(0));
        when(loteService.produtoEmEstoque(Mockito.anyInt())).thenReturn(0);
        when(produtoService.atualizarProduto(1,lotes.get(0).getProduto())).thenReturn(lotes.get(0).getProduto());

        Perda resultado;
        try{
            resultado = perdaService.criarPerda(perda);
        } catch (Exception e) {
            fail("Erro ao buscar perda com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado);
        assertEquals(perda.getId(), resultado.getId(), "O ID do perda salvo não está correto");
        assertEquals(perda.getQtdProduto(), resultado.getQtdProduto(), "A quantidade de perda salva não está correto");
        assertEquals(perda.getDataPerda(), resultado.getDataPerda(), "A data da perda salva não está correto");
        assertEquals(perda.getProduto(), resultado.getProduto(), "O produto da perda salva não está correto");

        when(loteService.produtoEmEstoque(Mockito.anyInt())).thenReturn(2);
        perdaService.criarPerda(perda);
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para atualizar a perda, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirPerdaPorIdPassadoMetodoAtualizarPerda(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.atualizarPerda(50,perdasEsperadas.get(0)));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passado uma perda que existe no banco de dados, deve atualizar a perda com os dados novos")
    void atualizarPerda() {
        Perda perdaExistente = perdasEsperadas.get(0);

        Perda perdaAtualizada = new Perda();
        perdaAtualizada.setProduto(produtos.get(3));

        when(perdaRepository.existsById(perdaExistente.getId())).thenReturn(true);
        when(perdaRepository.save(any(Perda.class))).thenReturn(perdaAtualizada);

        Perda resultado;
        try{
            resultado = perdaService.atualizarPerda(perdaExistente.getId(), perdaAtualizada);
        } catch (Exception e) {
            fail("Erro ao buscar perda com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado);
        assertEquals(perdaAtualizada.getQtdProduto(), resultado.getQtdProduto(), "A quantidade de perdas não foi atualizada com o valor da nova perda");
        assertEquals(perdaAtualizada.getDataPerda(), resultado.getDataPerda(), "A data da perda não foi atualizada com o valor da nova data");
        assertEquals(perdaAtualizada.getProduto(), resultado.getProduto(), "O produto da perda não foi atualizada com o valor do novo produto");
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para deletar a perda, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirPerdaPorIdPassadoNoMetodoDeletar(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.deletarPerda(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um Id que existe no banco de dados, deve deletar a perda por id")
    void deletarPerda() {
        Integer id = 1;
        when(perdaRepository.existsById(id)).thenReturn(true);

        try{
            perdaService.deletarPerda(id);
        } catch (Exception e) {
            fail("Erro ao buscar marca com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        verify(perdaRepository).existsById(id);
        verify(perdaRepository).deleteById(id);
    }
}