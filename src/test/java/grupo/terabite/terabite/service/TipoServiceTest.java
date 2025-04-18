package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tipos")
class TipoServiceTest extends DataFactory {

    @Mock
    private TipoRepository tipoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private TipoService tipoService;

    @Test
    @DisplayName("Quando o banco de dados não possui tipos, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExcecaoListarTipo() {
        when(tipoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.listarTipo());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui tipo, o serviço deve retornar a lista correta")
    void deveRetornarListarTipoQuandoExisitir() {
        when(tipoRepository.findAll()).thenReturn(tipos);

        List<Tipo> tipoRetornados;
        try{
            tipoRetornados = tipoService.listarTipo();
        } catch (Exception e) {
            fail("Erro ao buscar tipo: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertIterableEquals(tipos, tipoRetornados, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por Id inexistente, deve lançar exceção 204 (NO_CONTENT)")
    void deveLancarExcecaoBuscarTipoPorId() {
        when(tipoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.buscarPorId(2));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "O status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando buscar por Id existente, deve retornar o tipo correspondente")
    void deveRetornarBuscarTipoPorId() {
        Tipo tipoExistente = tipos.get(0);
        when(tipoRepository.findById(1)).thenReturn(Optional.of(tipoExistente));

        Tipo resultado;
        try{
            resultado = tipoService.buscarPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar tipo com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O tipo retornada não deveria ser nula");
        assertEquals(tipoExistente.getId(), resultado.getId(), "O Id do tipo retornada não está correto");
        assertEquals(tipoExistente.getNome(), resultado.getNome(), "O nome do tipo retornada não está correto");
    }

    @Test
    @DisplayName("Qunado buscar por tipo isBlanck não existente, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoBuscarPorNomeisBlanck() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.buscarPorNomeTipo(" "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");
    }

    @Test
    @DisplayName("Quando buscar por tipo existente em diferentes cases, deve retornar a tipo correspondente")
    void deveRetornarBuscarPorNomeTipo() {
        Tipo tipo = tipos.get(0);
        when(tipoRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(tipo);

        Tipo resultadoUpperCase = tipoService.buscarPorNomeTipo("CONGELADOS");
        Tipo resultadoLowerCase = tipoService.buscarPorNomeTipo("congelados");
        Tipo resultadoMixedCase = tipoService.buscarPorNomeTipo("ConGElAdos");

        assertNotNull(resultadoUpperCase, "A tipo retornada com nome em UPPERCASE não deveria ser nula");
        assertEquals(tipo.getId(), resultadoUpperCase.getId(), "O ID do tipo retornada (UPPERCASE) não está correto");
        assertEquals(tipo.getNome(), resultadoUpperCase.getNome(), "O nome da tipo retornada (UPPERCASE) não está correto");

        assertNotNull(resultadoLowerCase, "A tipo retornada com nome em lowercase não deveria ser nula");
        assertEquals(tipo.getId(), resultadoLowerCase.getId(), "O ID da tipo retornada (lowercase) não está correto");
        assertEquals(tipo.getNome(), resultadoLowerCase.getNome(), "O nome da tipo retornada (lowercase) não está correto");

        assertNotNull(resultadoMixedCase, "A tipo retornada com nome em MixedCase não deveria ser nula");
        assertEquals(tipo.getId(), resultadoMixedCase.getId(), "O ID da tipo retornada (MixedCase) não está correto");
        assertEquals(tipo.getNome(), resultadoMixedCase.getNome(), "O nome da tipo retornada (MixedCase) não está correto");
    }

    @Test
    @DisplayName("Quando criar um novo tipo com nome já existente, deve lançar exceção 409 (CONFLICT)")
    void deveLancarExecaoQuandoPassarUmTipoJaExistente(){
        Tipo tipoExistente = tipos.get(0);
        when(tipoService.buscarPorNomeTipo(tipoExistente.getNome())).thenReturn(tipoExistente);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.criarTipo(tipoExistente));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode(), "O status HTTP esperado é 409 (CONFLICT)");
    }

    @Test
    @DisplayName("Quando criar um novo tipo, deve definir o ID como null e salvar no repositório")
    void deveRetornarCriarTipo() {
        Tipo tipoSalvo = new Tipo(3, "Novo Tipo");
        Tipo novoTipo = new Tipo(null, "Novo Tipo");

        when(tipoRepository.save(novoTipo)).thenReturn(tipoSalvo);

        Tipo resultado;
        try{
            resultado = tipoService.criarTipo(novoTipo);
        } catch (Exception e) {
            fail("Erro ao buscar tipo com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(tipoSalvo.getId(), resultado.getId(), "O ID do tipo salvo não está correto");
        assertEquals(tipoSalvo.getNome(), resultado.getNome(), "O nome do tipo salva não está correto");

        verify(tipoRepository).save(ArgumentMatchers.argThat(tipo -> tipo.getId() == null && "Novo Tipo".equals(tipo.getNome())));
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para deletar o tipo, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirTipoPorIdPassadoNoMetodoDeletar() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.deletarTipo(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um Id que existe no banco de dados, deve deletar o tipo por id")
    void deveDeletarTipoSeExistente() {
        Integer id = 1;
        when(tipoRepository.existsById(id)).thenReturn(true);
        when(produtoRepository.findBySubtipoTipoId(id)).thenReturn(List.of());

        tipoService.deletarTipo(id);

        verify(tipoRepository).existsById(id);
        verify(tipoRepository).deleteById(id);

        tipoService.deletarTipo(id);

        when(produtoRepository.findBySubtipoTipoId(id)).thenReturn(List.of(produtos.get(0)));

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> tipoService.deletarTipo(id));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode(), "O status HTTP esperado é 409 (CONFLICT)");
    }
}