package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.factory.DataFactory;
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
@DisplayName("Subtipos")
class SubtipoServiceTest extends DataFactory {

    @Mock
    private SubtipoRepository subtipoRepository;

    @InjectMocks
    private SubtipoService subtipoService;

    @Mock
    private TipoService tipoService;

    @Test
    @DisplayName("Quando o banco de dados não possui subtipos, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExcecaoListarSubtipo() {
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
    @DisplayName("Quando buscar subtipo por ID inexistente, deve lançar exceção 204 (NO_CONTENT)")
    void deveLancarExcecaoBuscarSubtipoPorId() {
        when(subtipoRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.buscarPorId(2));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "O status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando buscar subtipo por Id existente, deve retornar o subtipo correspondente")
    void deveRetornarBuscarSubtipoPorId() {
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
        assertEquals(subtipoExistente.getTipo(), resultado.getTipo(), "O tipo do subtipo retornada não está correto");
    }

    @Test
    @DisplayName("Qunado buscar por subtipo passando argumento vazio, deve lançar exceção 400 (BAD_REQUEST)")
    void deveLancarExcecaoBuscarPorSubtipoArgumentoVazio() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.buscarPorNomeSubtipo(" "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");
    }


    @Test
    @DisplayName("Quando buscar por subtipo existente em diferentes cases, deve retornar a subtipo correspondente")
    void deveRetornarSubtipoBuscarPorNomeSubtipoIgnoreCase() {
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
    @DisplayName("Quando tentar cadastrar um subtipo passando nome e tipo já existende, deve lançar exceção 409 (CONFLICT)")
    void deveLancarExecaoQuandoCadastrarSubtipoComNomeETipoJaExistende() {
        Subtipo subtipo = subtipos.get(0);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> subtipoService.validarSubtipoExistende(subtipo, subtipo));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode(), "O status HTTP esperado é 409 (CONFLICT)");
    }

    @Test
    @DisplayName("Quando criar um novo subtipo, deve definir o ID como null e salvar no repositório")
    void deveRetornarCriarSubtipo() {
        Tipo tipo = tipos.get(0);
        Subtipo novoSubtipo = subtipos.get(1);

        when(tipoService.buscarPorId(tipo.getId())).thenReturn(tipo);
        when(subtipoRepository.save(novoSubtipo)).thenReturn(novoSubtipo);

        Subtipo resultado;
        try{
            resultado = subtipoService.criarSubtipo(novoSubtipo, novoSubtipo.getTipo().getId());
        } catch (Exception e) {
            fail("Erro ao buscar subtipo com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(novoSubtipo.getId(), resultado.getId(), "O ID do subtipo salvo não está correto");
        assertEquals(novoSubtipo.getNome(), resultado.getNome(), "O nome da subtipo salvo não está correto");
        assertEquals(novoSubtipo.getTipo(), resultado.getTipo(), "O tipo do subtipo salvo não está correto");

        verify(subtipoRepository).save(ArgumentMatchers.argThat(subtipo -> subtipo.getId() == 2 && "Semi-congelado".equals(subtipo.getNome())));
    }
}