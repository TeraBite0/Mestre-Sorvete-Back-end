package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Fornecedor;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.FornecedorRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Fornecedor")
public class FornecedorServiceTest extends DataFactory {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private FornecedorService fornecedorService;

    @Test
    @DisplayName("Quando o banco de dados não possuir fornecedores, o serviço deve lançar ResponseStatusException com status 204 (NO_CONTENT)")
    void deveLancarExecaoQuandoNaoExisterFornecedores(){
        when(fornecedorRepository.findAll()).thenReturn(Collections.emptyList());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.listarFornecedor());
        assertEquals(HttpStatus.NO_CONTENT, exception.getStatusCode(), "Status HTTP esperado é 204 (NO_CONTENT)");
    }

    @Test
    @DisplayName("Quando o banco de dados possuir fornecedores, o serviço deve retornar a lista correta")
    void deveRetornarListaDeFornecedoresQuandoExistirem() {
        when(fornecedorRepository.findAll()).thenReturn(fornecedores);

        List<Fornecedor> fornecedorRetornado;

        try{
            fornecedorRetornado = fornecedorService.listarFornecedor();
        } catch (Exception e) {
            fail("Erro ao buscar fornecedor: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertIterableEquals(fornecedores, fornecedorRetornado, "As listas retornadas dos fornecedores não são iguais às esperadas");
    }

    @Test
    @DisplayName("Quando buscar por ID inexistente, deve lançar ResponseStatusException com status 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoEncontrarFornecedorPorID() {
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.buscarPorId(2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT_FOUND)");
    }

    @Test
    @DisplayName("Quando buscar por Id existente, deve retornar o fornecedor correspondente")
    void deveRetornarFornecedorQuandoPassadoSeuId() {
        Fornecedor fornecedor = fornecedores.get(0);
        when(fornecedorRepository.findById(1)).thenReturn(Optional.of(fornecedor));

        Fornecedor resultado;
        try{
            resultado = fornecedorService.buscarPorId(1);
        } catch (Exception e) {
            fail("Erro ao buscar fornecedor com ID existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        assertNotNull(resultado, "O fornecedor retornada não deveria ser nula");
        assertEquals(fornecedor.getId(), resultado.getId(), "O ID da fornecedor retornada não está correto");
        assertEquals(fornecedor.getNome(), resultado.getNome(), "O nome da fornecedor retornada não está correto");
    }

    @Test
    @DisplayName("Quando buscar por fornecedor isBlanck, deve lançar exceção 400 (BAD_REQUEST)")
    void deveLancarExcecaoQuandoPassarNomeisBlanck(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.buscarPorNomeFornecedor(" "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode(), "O status HTTP esperado é 400 (BAD_REQUEST)");
    }

    @Test
    @DisplayName("Quando buscar por nome existente em diferentes cases, deve retornar o fornecedor correspondente")
    void deveRetornarFornecedorQuandoPassadoNomeComCasesDiferentes() {
        Fornecedor fornecedor = fornecedores.get(0);
        when(fornecedorRepository.findByNomeIgnoreCase(Mockito.anyString())).thenReturn(fornecedor);

        Fornecedor resultadoUpperCase = fornecedorService.buscarPorNomeFornecedor("FORNECEDOR");
        Fornecedor resultadoLowerCase = fornecedorService.buscarPorNomeFornecedor("fornecedor");
        Fornecedor resultadoMixedCase = fornecedorService.buscarPorNomeFornecedor("ForNEcedor");

        assertNotNull(resultadoUpperCase, "O fornecedor retornado com nome em UPPERCASE não deveria ser nula");
        assertEquals(fornecedor.getId(), resultadoUpperCase.getId(), "O ID do fornecedor retornada (UPPERCASE) não está correto");
        assertEquals(fornecedor.getNome(), resultadoUpperCase.getNome(), "O nome do fornecedor retornada (UPPERCASE) não está correto");

        assertNotNull(resultadoLowerCase, "O fornecedor retornado com nome em lowercase não deveria ser nula");
        assertEquals(fornecedor.getId(), resultadoLowerCase.getId(), "O ID do fornecedor retornada (lowercase) não está correto");
        assertEquals(fornecedor.getNome(), resultadoLowerCase.getNome(), "O nome do fornecedor retornada (lowercase) não está correto");

        assertNotNull(resultadoMixedCase, "O fornecedor retornado com nome em MixedCase não deveria ser nula");
        assertEquals(fornecedor.getId(), resultadoMixedCase.getId(), "O ID do fornecedor retornada (MixedCase) não está correto");
        assertEquals(fornecedor.getNome(), resultadoMixedCase.getNome(), "O nome do fornecedor retornada (MixedCase) não está correto");
    }

    @Test
    @DisplayName("Quando criar um novo fornecedor, deve definir o ID como null e salvar no repositório")
    void deveCriarNovoFornecedor() {
        Fornecedor fornecedorSalvo = fornecedores.get(0);
        Fornecedor novoFornecedor = new Fornecedor(null, "Fornecedor");

        when(fornecedorRepository.save(novoFornecedor)).thenReturn(fornecedorSalvo);

        Fornecedor resultado = fornecedorService.criarFornecedor(novoFornecedor);

        assertNotNull(resultado, "O resultado não deve ser nulo");
        assertEquals(fornecedorSalvo.getId(), resultado.getId(), "O ID do fornecedor salvo não está correto");
        assertEquals(fornecedorSalvo.getNome(), resultado.getNome(), "O nome do fornecedor salvo não está correto");

        verify(fornecedorRepository).save(ArgumentMatchers.argThat(fornecedor -> fornecedor.getId() == null && "Fornecedor".equals(fornecedor.getNome())));
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para atualizar o fornecedor, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirFornecedorPorIdPassadoMetodoAtualizarFornecedor(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.atualizarFornecedor(50,fornecedores.get(0)));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um fornecedor que existe no banco de dados, deve atualizar fornecedor")
    void deveAtualizarFornecedorSeIdExistir() {
        Fornecedor fornecedorExistente = fornecedores.get(0);
        Fornecedor fornecedorAtualizada = new Fornecedor(null, "Fornecedor Atualizado");

        when(fornecedorRepository.existsById(fornecedorExistente.getId())).thenReturn(true);
        when(fornecedorRepository.save(any(Fornecedor.class))).thenReturn(fornecedorAtualizada);

        Fornecedor resultado = fornecedorService.atualizarFornecedor(fornecedorExistente.getId(), fornecedorAtualizada);

        assertNotNull(resultado);
        assertEquals(fornecedorAtualizada.getNome(), resultado.getNome());
    }

    @Test
    @DisplayName("Quando passar um Id que não existe no banco de dados para deletar o fornecedor, deve lançar exceção 404 (NOT_FOUND)")
    void deveLancarExcecaoQuandoNaoExistirFornecedorPorIdPassadoNoMetodoDeletar(){
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.deletarFornecedorPorId(50));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode(), "O status HTTP esperado é 404 (NOT FOUND)");
    }

    @Test
    @DisplayName("Quando passar um Id que existe no banco de dados, deve deletar fornecedor por id")
    void deveDeletarFornecedorSeExistente() {
        Integer id = 1;
        when(fornecedorRepository.existsById(id)).thenReturn(true);

        try{
            fornecedorService.deletarFornecedorPorId(id);
        } catch (Exception e) {
            fail("Erro ao buscar fornecedor com nome não existente: " + (e.getMessage() != null ? e.getMessage() : e.getCause()));
            return;
        }

        verify(fornecedorRepository).existsById(id);
        verify(fornecedorRepository).deleteById(id);
    }

    @Test
    @DisplayName("Quando passar um nome de fornecedor que já existe no banco de dados, deve lançar exceção 409 (CONFLICT)")
    void deveLancarExecaoQuandoPassarUmFornecedorQueJaExiste(){
        Fornecedor fornecedor = fornecedores.get(0);
        when(fornecedorService.buscarPorNomeFornecedor(fornecedor.getNome())).thenReturn(fornecedor);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> fornecedorService.criarFornecedor(fornecedor));
        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode(), "O status HTTP esperado é 409 (CONFLICT)");
    }
}
