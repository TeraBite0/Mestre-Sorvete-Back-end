package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.repository.SubtipoRepository;
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
class SubtipoServiceTest {

    @Mock
    private SubtipoRepository subtipoRepository;

    @InjectMocks
    private SubtipoService subtipoService;

    @Mock
    private TipoService tipoService;

    private List<Tipo> tipos;

    private List<Subtipo> subtipos;

    @BeforeEach
    void setup(){
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
    }

    @Test
    @DisplayName("Quando o banco de dados não possui subtipos, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExecaoListarSubtipo() {
        when(subtipoRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.listarSubtipo());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui subtipo, o serviço deve retornar a lista correta")
    void deveRetornarListarSubtipoQuandoExisitir() {
        when(subtipoRepository.findAll()).thenReturn(subtipos);

        List<Subtipo> subtipoRetornadas;
        try{
            subtipoRetornadas = subtipoService.listarSubtipo();
        } catch (Exception e) {
            fail("Erro ao buscar subtipos: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertIterableEquals(subtipos, subtipoRetornadas, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar exeção 204 (NOT_FOUND)")
    void deveLancarExecaoBuscarPorId() {
        when(subtipoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.buscarPorId(2));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "O status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando buscar por Id existente, deve retornar o subtipo correspondente")
    void deveRetornarBuscarPorId() {
        Subtipo subtipoExistente = subtipos.get(0);
        when(subtipoRepository.findById(1)).thenReturn(Optional.of(subtipoExistente));

        Subtipo resultado;
        try{
            resultado = subtipoService.buscarPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar subtipo com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O subtipo retornada não deveria ser nula");
        assertEquals(subtipoExistente.getId(), resultado.getId(), "O ID do subtipo retornada não está correto");
        assertEquals(subtipoExistente.getNome(), resultado.getNome(), "O nome do subtipo retornada não está correto");
        assertEquals(subtipoExistente.getTipoPai(), resultado.getTipoPai(), "O tipoPai do subtipo retornada não está correto");
    }

    @Test
    @DisplayName("Qunado buscar por subtipo ignore case não existente, deve lançar exeção 404 (NOT_FOUND)")
    void deveLancarExecaoBuscarPorNomeIgnoreCaseSubtipo() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.buscarPorNomeSubtipo(" "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");
    }

    @Test
    @DisplayName("Quando passar um  que não existe no banco de dados, deve cadastrar uma novo subtipo")
    void deveCriarNovoSubtipoQuandoBuscarPorNomeNaoExistente(){
        String nomeSubtipo = "Novo subtipo";
        when(subtipoRepository.findByNomeIgnoreCase(nomeSubtipo)).thenReturn(null);
        when(tipoService.buscarPorId(Mockito.anyInt())).thenReturn(new Tipo(1, "Picolé"));

        Subtipo subtipo;
        try{
            subtipo = subtipoService.buscarPorNomeSubtipo(nomeSubtipo);
        } catch (Exception e) {
            fail("Erro ao buscar subtipo com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNull(subtipo);
    }

    @Test
    @DisplayName("Quando buscar por subtipo existente em diferentes cases, deve retornar a subtipo correspondente")
    void deveRetornarBuscarPorNomeSubtipo() {
        Subtipo subtipo = subtipos.get(0);
        when(subtipoRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(subtipo);

        Subtipo resultadoUpperCase = subtipoService.buscarPorNomeSubtipo("PALHETAS");
        Subtipo resultadoLowerCase = subtipoService.buscarPorNomeSubtipo("palhetas");
        Subtipo resultadoMixedCase = subtipoService.buscarPorNomeSubtipo("PalHEtas");

        assertNotNull(resultadoUpperCase, "A subtipo retornada com nome em UPPERCASE não deveria ser nula");
        assertEquals(subtipo.getId(), resultadoUpperCase.getId(), "O ID do subtipo retornada (UPPERCASE) não está correto");
        assertEquals(subtipo.getNome(), resultadoUpperCase.getNome(), "O nome da subtipo retornada (UPPERCASE) não está correto");

        assertNotNull(resultadoLowerCase, "A subtipo retornada com nome em lowercase não deveria ser nula");
        assertEquals(subtipo.getId(), resultadoLowerCase.getId(), "O ID da subtipo retornada (lowercase) não está correto");
        assertEquals(subtipo.getNome(), resultadoLowerCase.getNome(), "O nome da subtipo retornada (lowercase) não está correto");

        assertNotNull(resultadoMixedCase, "A subtipo retornada com nome em MixedCase não deveria ser nula");
        assertEquals(subtipo.getId(), resultadoMixedCase.getId(), "O ID da subtipo retornada (MixedCase) não está correto");
        assertEquals(subtipo.getNome(), resultadoMixedCase.getNome(), "O nome da subtipo retornada (MixedCase) não está correto");
    }

    @Test
    @DisplayName("Quando criar um novo subtipo, deve definir o ID como null e salvar no repositório")
    void deveRetornarCriarSubtipo() {
        Subtipo subtipoSalvo = new Subtipo(3, tipos.get(0),"Novo Subtipo");
        Subtipo novoSubtipo = new Subtipo(null, tipos.get(0),"Novo Subtipo");

        when(subtipoRepository.save(novoSubtipo)).thenReturn(subtipoSalvo);

        Subtipo resultado;
        try{
            resultado = subtipoService.criarSubtipo(novoSubtipo);
        } catch (Exception e) {
            fail("Erro ao buscar subtipo com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(subtipoSalvo.getId(), resultado.getId(), "O ID do subtipo salvo não está correto");
        assertEquals(subtipoSalvo.getNome(), resultado.getNome(), "O nome da subtipo salva não está correto");
        assertEquals(subtipoSalvo.getTipoPai(), resultado.getTipoPai(), "O tipoPai do subtipo salva não está correto");

        verify(subtipoRepository).save(ArgumentMatchers.argThat(subtipo -> subtipo.getId() == null && "Novo Subtipo".equals(subtipo.getNome())));
    }
}