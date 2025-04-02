package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Fornecedor;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.LoteProduto;
import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.LoteProdutoRepository;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.SaidaEstoqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Lotes")
class LoteServiceTest extends DataFactory {


    @Mock
    private LoteRepository loteRepository;

    @Mock
    private LoteProdutoRepository loteProdutoRepository;

    @Mock
    private SaidaEstoqueRepository saidaEstoqueRepository;

    @Mock
    private ProdutoService produtoService;

    @Mock
    private FornecedorService fornecedorService;

    @InjectMocks
    private LoteService loteService;


    @Test
    void buscarPorId_DeveRetornarLote_QuandoIdExiste() {
        when(loteRepository.findById(1)).thenReturn(Optional.of(lote));
        Lote resultado = loteService.buscarPorId(1);
        assertNotNull(resultado);
        assertEquals(1, resultado.getId());
    }

    @Test
    void buscarPorId_DeveLancarException_QuandoIdNaoExiste() {
        when(loteRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> loteService.buscarPorId(1));
    }

    @Test
    void criarLote_DeveSalvarLote_QuandoDadosValidos() {
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(1);
        when(fornecedorService.buscarPorNomeFornecedor("Fornecedor"))
                .thenReturn(fornecedor);
        when(loteRepository.save(any(Lote.class))).thenReturn(lote);

        LoteProduto loteProduto = new LoteProduto(3, null, produtos.get(1), 20);
        Lote novoLote = new Lote(1, fornecedores.get(1), LocalDate.now(), LocalDate.now(), LocalDate.now(), 2000.0, null, null, List.of(loteProduto));
        novoLote.setLoteProdutos(List.of());

        Lote resultado = loteService.criarLote(novoLote, "Fornecedor");
        assertNotNull(resultado);
        assertEquals(LoteStatusEnum.AGUARDANDO_ENTREGA, resultado.getStatus());
    }

    @Test
    void atualizarLote_DeveAtualizarLote_QuandoIdExiste() {
        when(loteRepository.existsById(1)).thenReturn(true);
        when(loteRepository.save(any(Lote.class))).thenReturn(lote);
        when(produtoService.buscarPorId(Mockito.anyInt())).thenReturn(produtos.get(0));
        lote.setStatus(LoteStatusEnum.ENTREGUE);

        Lote resultado = loteService.atualizarLote(1, lote);
        assertNotNull(resultado);
        assertEquals(LoteStatusEnum.ENTREGUE, resultado.getStatus());
    }

    @Test
    void deletarLote_DeveDeletarLote_QuandoIdExiste() {
        when(loteRepository.findById(1)).thenReturn(Optional.of(lote));
        when(loteProdutoRepository.findByLoteId(1)).thenReturn(List.of());
        doNothing().when(loteProdutoRepository).deleteAll(anyList());
        doNothing().when(loteRepository).deleteById(1);

        loteService.deletarLote(1);
        verify(loteRepository, times(1)).deleteById(1);
    }

    @Test
    void atualizarStatusLote_DeveLancarException_QuandoStatusEntregue() {
        lote.setStatus(LoteStatusEnum.ENTREGUE);
        when(loteRepository.findById(1)).thenReturn(Optional.of(lote));

        Lote atualizarLote = new Lote(1, null, null, null, null, null, LoteStatusEnum.AGUARDANDO_ENTREGA, null, null);

        assertThrows(ResponseStatusException.class, () -> loteService.atualizarStatusLote(1, atualizarLote));
    }
}