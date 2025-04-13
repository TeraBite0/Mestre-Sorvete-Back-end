package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.SaidaEstoqueRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Saida Estoque")
class SaidaEstoqueServiceTest extends DataFactory {

    @Mock
    private SaidaEstoqueRepository saidaEstoqueRepository;

    @Mock
    private LoteService loteService;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private SaidaEstoqueService saidaEstoqueService;


    @Test
    void listar_DeveRetornarLista_QuandoExistemSaidas() {
        when(saidaEstoqueRepository.findAll()).thenReturn(List.of(saidaEstoque));
        List<SaidaEstoque> resultado = saidaEstoqueService.listar();
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
    }

    @Test
    void listar_DeveLancarException_QuandoNaoExistemSaidas() {
        when(saidaEstoqueRepository.findAll()).thenReturn(List.of());
        assertThrows(ResponseStatusException.class, () -> saidaEstoqueService.listar());
    }

    @Test
    void registrarSaida_DeveSalvarSaidas_QuandoValidas() {
        when(produtoService.buscarPorId(1)).thenReturn(produtos.get(0));
        when(saidaEstoqueRepository.saveAll(anyList())).thenReturn(List.of(saidaEstoque));

        List<SaidaEstoque> resultado = saidaEstoqueService.registrarSaida(List.of(saidaEstoque));
        assertEquals(1, resultado.size());
        verify(loteService, times(1)).atualizarEstoqueProduto(anyList());
    }

    @Test
    void editarSaida_DeveAtualizarSaida_QuandoIdExiste() {
        SaidaEstoque saidaAtualizada = new SaidaEstoque();
        saidaAtualizada.setId(1);
        saidaAtualizada.setProduto(produtos.get(1));

        when(saidaEstoqueRepository.findById(1)).thenReturn(Optional.of(saidaEstoque));
        when(saidaEstoqueRepository.save(any(SaidaEstoque.class))).thenReturn(saidaAtualizada);
        when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(produtos.get(1));

        SaidaEstoque resultado = saidaEstoqueService.editarSaida(1, saidaAtualizada);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(loteService, times(1)).atualizarEstoqueProduto(anyList());
    }

    @Test
    void editarSaida_DeveLancarException_QuandoIdNaoExiste() {
        when(saidaEstoqueRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> saidaEstoqueService.editarSaida(1, saidaEstoque));
    }

    @Test
    void deletarSaidas_DeveDeletar_QuandoTodasExistem() {
        when(saidaEstoqueRepository.findAllById(anySet())).thenReturn(List.of(saidaEstoque));
        doNothing().when(saidaEstoqueRepository).deleteAllById(anySet());
        when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(produtos.get(0));
        doNothing().when(loteService).atualizarEstoqueProduto(Mockito.anyList());

        assertDoesNotThrow(() -> saidaEstoqueService.deletarSaidas(List.of(saidaEstoque)));
        verify(loteService, times(1)).atualizarEstoqueProduto(anyList());
        verify(saidaEstoqueRepository, times(1)).deleteAllById(anySet());
    }

    @Test
    void deletarSaidas_DeveLancarException_QuandoAlgumaSaidaNaoExiste() {
        when(saidaEstoqueRepository.findAllById(anySet())).thenReturn(List.of());
        assertThrows(ResponseStatusException.class, () -> saidaEstoqueService.deletarSaidas(List.of(saidaEstoque)));
    }

    @Test
    void editarSaida_DeveAtualizarSaida() {
        SaidaEstoque saidaAtualizada = new SaidaEstoque();
        saidaAtualizada.setId(1);
        saidaAtualizada.setProduto(produtos.get(0));

        when(saidaEstoqueRepository.findById(1)).thenReturn(Optional.of(saidaEstoque));
        when(saidaEstoqueRepository.save(any(SaidaEstoque.class))).thenReturn(saidaAtualizada);

        SaidaEstoque resultado = saidaEstoqueService.editarSaida(1, saidaAtualizada);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
        verify(loteService, times(1)).atualizarEstoqueProduto(anyList());
    }
}
