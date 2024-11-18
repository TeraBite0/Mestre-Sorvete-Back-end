package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.repository.*;
import org.junit.jupiter.api.Assertions;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Perda")
class PerdaServiceTest {

    @Mock
    private PerdaRepository perdaRepository;

    @InjectMocks
    private PerdaService perdaService;

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @Mock
    private SubtipoRepository subtipoRepository;

    @InjectMocks
    private SubtipoService subtipoService;

    @Mock
    private TipoRepository tipoRepository;

    @InjectMocks
    private TipoService tipoService;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Mock
    private LoteRepository loteRepository;

    @InjectMocks
    private LoteService loteService;

    @Mock
    private VendaProdutoRepository vendaProdutoRepository;

    @InjectMocks
    private VendaService vendaService;

    public List<Marca> marcas;

    public List<Tipo> tipos;

    public List<Subtipo> subtipos;

    public List<Produto> produtos;

    public List<Lote> lotes;

    public List<Perda> perdasEsperadas;


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
                new Produto(1, "Gelo gelado", subtipos.get(0), marcas.get(0), 7.0, true, true),
                new Produto(2, "Gelo geladinho", subtipos.get(1), marcas.get(1), 7.0, true, true),
                new Produto(3, "Gelo quentinho", subtipos.get(2), marcas.get(2), 10.0, false, true),
                new Produto(4, "Gelo quente", subtipos.get(3), marcas.get(3), 10.0, true, false));

        perdasEsperadas = List.of(
                new Perda(1, 2, LocalDate.now(), produtos.get(0)),
                new Perda(2, 3, LocalDate.now(), produtos.get(1)),
                new Perda(3, 4, LocalDate.now(), produtos.get(2)),
                new Perda(4, 1, LocalDate.now(), produtos.get(0)),
                new Perda(5, 1, LocalDate.now(), produtos.get(1))
        );

        lotes = List.of(
                new Lote(1, LocalDate.parse("2024-10-02"), LocalDate.parse("2024-10-05"), LocalDate.parse("2025-05-20"), 50, 150.00, produtos.get(0)),
                new Lote(1, LocalDate.parse("2024-10-02"), LocalDate.parse("2024-10-05"), LocalDate.parse("2025-05-20"), 50, 150.00, produtos.get(0)),
                new Lote(1, LocalDate.parse("2024-10-02"), LocalDate.parse("2024-10-05"), LocalDate.parse("2025-05-20"), 50, 150.00, produtos.get(0)),
                new Lote(1, LocalDate.parse("2024-10-02"), LocalDate.parse("2024-10-05"), LocalDate.parse("2025-05-20"), 50, 150.00, produtos.get(0))
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
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 404")
    void deveLancarExecaoQuandoNaoEncontrarPerdaPorID() {
        when(perdaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> perdaService.buscarPerdaPorId(2));
        assertEquals(HttpStatusCode.valueOf(404), exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
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
    void deveCriarNovaPerda() {
    }

    @Test
    @DisplayName("Deve atualizar")
    void atualizarPerda() {
    }

    @Test
    @DisplayName("Quando deletar por ID existente, deve executar sem impetimentos")
    void deletarPerda() {
    }

    @Test
    @DisplayName("deve buscar por produto id")
    void buscarPerdaPorProdutoId() {
    }
}