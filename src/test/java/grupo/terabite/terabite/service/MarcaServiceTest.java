package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.MarcaRepository;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Marca")
class MarcaServiceTest extends DataFactory {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @Test
    @DisplayName("Quando o banco de dados não possui marcas, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExcecaoQuandoNaoExistemMarcas() {
        when(marcaRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.listarMarca());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possui marcas, o serviço deve retornar a lista correta")
    void deveRetornarListaDeMarcasQuandoExistirem() {
        when(marcaRepository.findAll()).thenReturn(marcas);

        List<Marca> marcasRetornadas;

        try{
            marcasRetornadas = marcaService.listarMarca();
        } catch (Exception e) {
            fail("Erro ao buscar marca: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertIterableEquals(marcas, marcasRetornadas, "As listas retornadas não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoEncontrarMarcaPorID() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.buscarPorId(2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
    }

    @Test
    @DisplayName("Quando buscar por Id existente, deve retornar a marca correspondente")
    void deveRetornarMarcaQuandoPassadoSeuId() {
        Marca marca = marcas.get(0);
        when(marcaRepository.findById(1)).thenReturn(Optional.of(marca));

        Marca resultado;
        try{
            resultado = marcaService.buscarPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar marca com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "A marca retornada não deveria ser nula");
        assertEquals(marca.getId(), resultado.getId(), "O ID da marca retornada não está correto");
        assertEquals(marca.getNome(), resultado.getNome(), "O nome da marca retornada não está correto");
    }

    @Test
    @DisplayName("Quando buscar por marca isBlanck, deve lançar exceção 400 (BAD_REQUEST)")
    void deveLancarExcecaoQuandoPassarNomeVazio(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.buscarPorNomeMarca(" "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");
    }

    @Test
    @DisplayName("Quando passar uma marca que não existe no banco de dados, deve cadastrar uma nova marca")
    void deveCriarNovaMarcaQuandoBuscarPorNomeNaoExistente(){
        String nomeMarca = "Nova Marca";
        when(marcaRepository.findByNomeIgnoreCase(nomeMarca)).thenReturn(null);
        when(marcaRepository.save(any())).thenReturn(new Marca(100, nomeMarca));

        Marca marca;
        try{
            marca = marcaService.buscarPorNomeMarca(nomeMarca);
        } catch (Exception e) {
            fail("Erro ao buscar marca com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(marca);
        assertEquals(nomeMarca, marca.getNome(), "O nome da marca retornada não está correto");
    }

    @Test
    @DisplayName("Quando buscar por nome existente em diferentes cases, deve retornar a marca correspondente")
    void deveRetornarMarcaQuandoPassadoNomeComCasesDiferentes() {
        Marca marca = new Marca(1, "Senhor Sorvete");
        when(marcaRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(marca);

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

        when(marcaRepository.save(novaMarca)).thenReturn(marcaSalva);

        Marca resultado = marcaService.criarMarca(novaMarca);

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(1, resultado.getId(), "O ID da marca salva não está correto");
        assertEquals("Nova Marca", resultado.getNome(), "O nome da marca salva não está correto");

        verify(marcaRepository).save(ArgumentMatchers.argThat(marca -> marca.getId() == null && "Nova Marca".equals(marca.getNome())));
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para atualizar a marca, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirMarcaPorIdPassadoMetodoAtualizarMarca(){
        Marca marca = new Marca();
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.atualizarMarca(50,marca));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar uma marca que existe no banco de dados, deve atualizar marca")
    void deveAtualizarMarcaSeIdExistir() {
        Marca marcaExistente = new Marca(1,"Marca Original");

        Marca marcaAtualizada = new Marca();
        marcaAtualizada.setNome("Marca Atualizada");

        when(marcaRepository.existsById(marcaExistente.getId())).thenReturn(true);
        when(marcaRepository.save(any(Marca.class))).thenReturn(marcaAtualizada);

        Marca resultado = marcaService.atualizarMarca(marcaExistente.getId(), marcaAtualizada);

        assertNotNull(resultado);
        assertEquals("Marca Atualizada", resultado.getNome());
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para deletar a marca, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirMarcaPorIdPassadoNoMetodoDeletar(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> marcaService.deletarMarca(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um Id que existe no banco de dados, deve deletar se a marca por id")
    void deveDeletarMarcaSeExistende() {
        Integer id = 1;
        when(marcaRepository.existsById(id)).thenReturn(true);

        try{
            marcaService.deletarMarca(id);
        } catch (Exception e) {
            fail("Erro ao buscar marca com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        verify(marcaRepository).existsById(id);
        verify(marcaRepository).deleteById(id);
    }
}