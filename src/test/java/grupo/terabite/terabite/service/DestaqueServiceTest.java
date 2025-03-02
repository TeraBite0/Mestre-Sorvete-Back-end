package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.factory.DataFactory;
import grupo.terabite.terabite.repository.DestaqueRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Destaque")
class DestaqueServiceTest extends DataFactory {

    @Mock
    private DestaqueRepository destaqueRepository;

    @Mock
    private ProdutoService produtoService;

    @InjectMocks
    private DestaqueService destaqueService;


    @Test
    @DisplayName("Altera o destaque do dia")
    void alterarDestaque() {
        Destaque novoDestaque = new Destaque(produtos.get(0), java.time.LocalDate.now());

        Mockito.when(produtoService.buscarPorId(novoDestaque.getProduto().getId())).thenReturn(novoDestaque.getProduto());
        Mockito.when(destaqueRepository.findByDtRecomendacao(Mockito.any())).thenReturn(null);
        Mockito.when(destaqueRepository.save(Mockito.any())).thenReturn(novoDestaque);

        Destaque resultado = destaqueService.alterarDestaque(novoDestaque);

        assertNotNull(resultado, "O destaque alterado não pode ser nulo");
        assertEquals(novoDestaque.getProduto(), resultado.getProduto(), "O produto do destaque deve ser o esperado");

        Mockito.when(destaqueRepository.findByDtRecomendacao(Mockito.any())).thenReturn(destaques.get(0));

        resultado = destaqueService.alterarDestaque(novoDestaque);

        assertNotNull(resultado, "O destaque alterado não pode ser nulo");
        assertEquals(novoDestaque.getProduto(), resultado.getProduto(), "O produto do destaque deve ser o esperado");
    }

    @Test
    @DisplayName("Exibe o destaque do dia")
    void exibirDestaque() {
        Destaque destaqueEsperado = destaques.get(0);

        Mockito.when(destaqueRepository.findByDtRecomendacao(Mockito.any())).thenReturn(destaqueEsperado);

        Destaque destaqueRecebido = destaqueService.destaque();

        assertNotNull(destaqueRecebido, "O destaque do dia não pode ser nulo");
        assertNotNull(destaqueRecebido.getProduto(), "O produto do destaque não pode ser nulo");
        assertEquals(destaqueEsperado.getProduto(), destaqueRecebido.getProduto(), "O produto do destaque deve ser o esperado");

        Mockito.when(destaqueRepository.findAll()).thenReturn(destaques);
        Mockito.when(destaqueRepository.findByDtRecomendacao(Mockito.any())).thenReturn(null);
        Mockito.when(destaqueRepository.save(Mockito.any())).thenReturn(destaqueEsperado);
        Mockito.when(produtoService.listarProduto()).thenReturn(produtos);
        Mockito.when(destaqueRepository.findByDtRecomendacaoBefore(Mockito.any())).thenReturn(List.of(destaques.get(3)));

        destaqueRecebido = destaqueService.destaque();

        assertNotNull(destaqueRecebido, "O destaque do dia não pode ser nulo");
        assertNotNull(destaqueRecebido.getProduto(), "O produto do destaque não pode ser nulo");
    }
}