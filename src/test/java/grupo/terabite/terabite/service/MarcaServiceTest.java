package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.repository.MarcaRepository;
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

@ExtendWith(MockitoExtension.class)
@DisplayName("Marca")
class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @Test
    @DisplayName("Quando o banco de dados não possui marcas, o serviço deve lançar ResponseStatusException com status 204")
    void deveLancarExcecaoQuandoNaoExistemMarcas() {
        Mockito.when(marcaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.listarMarca());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui marcas, o serviço deve retornar a lista correta")
    void deveRetornarListaDeMarcasQuandoExistirem() {
        List<Marca> marcasEsperadas = List.of(
                new Marca(1, "Senhor Sorvete"),
                new Marca(2, "PimPinella"),
                new Marca(3, "Gelone"),
                new Marca(4, "Artegel")
        );

        Mockito.when(marcaRepository.findAll()).thenReturn(marcasEsperadas);

        List<Marca> marcasRetornadas = marcaService.listarMarca();

        /*assertInterableEquals executa esse código, porem mais limpo:
         * marcasRetornadas.forEach(empresa -> {
         *  int index = marcasRetornadas.indexOf(empresa);
         *  assertEquals(marcasRetornadas.get(index).getId(), empresa.getId());
         *  assertEquals(marcasRetornadas.get(index).getNome(), empresa.getNome());
         * });
         * */
        assertIterableEquals(marcasEsperadas, marcasRetornadas, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID existente, deve retornar a marca correspondente")
    void deveRetornarMarcaQuandoPassadoSeuId() {
        Mockito.when(marcaRepository.findById(1)).thenReturn(Optional.of(new Marca(1, "Senhor Sorvete")));

        Marca resultado = marcaService.buscarPorId(1);

        assertNotNull(resultado, "A marca retornada não deveria ser nula");
        assertEquals(1, resultado.getId(), "O ID da marca retornada não está correto");
        assertEquals("Senhor Sorvete", resultado.getNome(), "O nome da marca retornada não está correto");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 204")
    void deveLancarExecaoQuandoNaoEncontrarMarcaPorID() {
        Mockito.when(marcaRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.buscarPorId(2));
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "O status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando buscar por nome existente em diferentes cases, deve retornar a marca correspondente")
    void deveRetornarMarcaQuandoPassadoNomeComCasesDiferentes() {
        Marca marca = new Marca(1, "Senhor Sorvete");
        Mockito.when(marcaRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(marca);

        Marca resultadoUpperCase = marcaService.buscarPorNomeMarca("SENHOR SORVETE");
        Marca resultadoLowerCase = marcaService.buscarPorNomeMarca("senhor sorvete");
        Marca resultadoMixedCase = marcaService.buscarPorNomeMarca("SenHoR SoRvetE");

        assertNotNull(resultadoUpperCase, "A marca retornada com nome em UPPERCASE não deveria ser nula");
        assertEquals(marca.getId(), resultadoUpperCase.getId(), "O ID da marca retornada (UPPERCASE) não está correto");
        assertEquals(marca.getNome(), resultadoUpperCase.getNome(), "O nome da marca retornada (UPPERCASE) não está correto");

        assertNotNull(resultadoLowerCase, "A marca retornada com nome em lowercase não deveria ser nula");
        assertEquals(marca.getId(), resultadoLowerCase.getId(), "O ID da marca retornada (lowercase) não está correto");
        assertEquals(marca.getNome(), resultadoLowerCase.getNome(), "O nome da marca retornada (lowercase) não está correto");

        assertNotNull(resultadoMixedCase, "A marca retornada com nome em MixedCase não deveria ser nula");
        assertEquals(marca.getId(), resultadoMixedCase.getId(), "O ID da marca retornada (MixedCase) não está correto");
        assertEquals(marca.getNome(), resultadoMixedCase.getNome(), "O nome da marca retornada (MixedCase) não está correto");
    }

    @Test
    @DisplayName("Quando criar uma nova marca, deve definir o ID como null e salvar no repositório")
    void deveCriarNovaMarca() {
        Marca marcaSalva = new Marca(1, "Nova Marca");
        Marca novaMarca = new Marca(null, "Nova Marca");

        Mockito.when(marcaRepository.save(novaMarca)).thenReturn(marcaSalva);

        Marca resultado = marcaService.criarMarca(novaMarca);

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(1, resultado.getId(), "O ID da marca salva não está correto");
        assertEquals("Nova Marca", resultado.getNome(), "O nome da marca salva não está correto");

        Mockito.verify(marcaRepository).save(ArgumentMatchers.argThat(marca -> marca.getId() == null && "Nova Marca".equals(marca.getNome())));
    }
}