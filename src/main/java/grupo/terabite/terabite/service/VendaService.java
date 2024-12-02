package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final LoteService loteService;
    private final ProdutoService produtoService;

    public List<Venda> listarVenda() {
        List<Venda> vendas = vendaRepository.findAllByOrderByDataCompraDesc();
        if (vendas.isEmpty()) throw new ResponseStatusException(HttpStatus.NO_CONTENT);

        return vendas;
    }

    public Venda buscarVendaPorId(Integer id) {
        return vendaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    public Venda criarVenda(List<VendaProduto> vendaProdutos) {
        Venda novaVenda = new Venda(LocalDateTime.now());

        Venda venda = vendaRepository.save(novaVenda);
        for (VendaProduto vp : vendaProdutos) {
            vp.setVenda(venda);
            Integer qtdEmEstoqueProduto = loteService.produtoEmEstoque(vp.getProduto().getId());

            if (vp.getQtdProdutosVendido() < 0 || Boolean.TRUE.equals(!vp.getProduto().getIsAtivo())) {
                String message = "Venda inválida, Produto inativo";
                if (qtdEmEstoqueProduto < vp.getQtdProdutosVendido()) {
                    message = "Produto fora de estoque";
                }
                vendaRepository.deleteById(novaVenda.getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
            }

            if (qtdEmEstoqueProduto - vp.getQtdProdutosVendido() < 1) {
                Produto p = produtoService.buscarPorId(vp.getProduto().getId());
                p.setEmEstoque(false);
                produtoService.atualizarProduto(vp.getProduto().getId(), p);
            }
        }

        vendaProdutoRepository.saveAll(vendaProdutos);
        return venda;
    }


    public List<VendaProduto> buscarProdutosPorVenda(Integer vendaId) {
        return vendaProdutoRepository.findByVendaId(vendaId);
    }

    public Venda atualizarVenda(Integer id, List<VendaProduto> vendaProdutos) {
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        vendaProdutoRepository.deleteByVendaId(id);

        for (VendaProduto vp : vendaProdutos) {
            vp.setVenda(venda);

            if (vp.getQtdProdutosVendido() < 0 || Boolean.TRUE.equals(!vp.getProduto().getIsAtivo())) { // Validação da venda (falta validar se o protudo existe em estoque)
                vendaRepository.deleteById(venda.getId());
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
        }

        return venda;
    }

    @Transactional
    public void deletarVenda(Integer id) {
        if (!vendaRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        vendaProdutoRepository.deleteByVendaId(id);
        vendaRepository.deleteById(id);
    }

    public List<Venda> listarVendaPorData(LocalDate data) {
        LocalDateTime startOfDay = data.atStartOfDay();
        LocalDateTime endOfDay = data.atTime(LocalTime.MAX);

        return vendaRepository.findByDataCompraBetween(startOfDay, endOfDay);
    }
}
