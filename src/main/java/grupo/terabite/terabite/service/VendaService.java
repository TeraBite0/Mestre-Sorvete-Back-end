package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.VendaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VendaService {

    private final VendaRepository vendaRepository;
    private final VendaProdutoRepository vendaProdutoRepository;
    private final LoteService loteService;
    private final ProdutoService produtoService;

    public List<Venda> listarVenda() {
        List<Venda> vendas = vendaRepository.findAllByOrderByDataCompraDesc();
        if(vendas.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return vendas;
    }

    public Venda buscarVendaPorId(Integer id) {
        Optional<Venda> vendasOpt = vendaRepository.findById(id);
        if (vendasOpt.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return vendasOpt.get();
    }

    public Venda criarVenda(List<VendaProduto> vendaProdutos) {
        Venda novaVenda = new Venda(LocalDateTime.now());

        Venda venda = vendaRepository.save(novaVenda);
        for (VendaProduto vp : vendaProdutos) {
            vp.setVenda(venda);
            Integer qtdEmEstoqueProduto = loteService.produtoEmEstoque(vp.getProduto().getId());

            if (vp.getQtdProdutosVendido() < 0 || !vp.getProduto().getIsAtivo()) {
                String message = "Venda inválida, Produto inativo";
                if (qtdEmEstoqueProduto < vp.getQtdProdutosVendido()) {
                    message = "Produto fora de estoque";
                }
                vendaRepository.deleteById(venda.getId());
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), message);
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
        Venda venda = vendaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));

        vendaProdutoRepository.deleteByVendaId(id);

        for (VendaProduto vp : vendaProdutos) {
            vp.setVenda(venda);

            if (vp.getQtdProdutosVendido() < 0 || !vp.getProduto().getIsAtivo()) { // Validação da venda (falta validar se o protudo existe em estoque)
                vendaRepository.deleteById(venda.getId());
                throw new ResponseStatusException(HttpStatusCode.valueOf(400));
            }
        }

        return venda;
    }

    @Transactional
    public void deletarVenda(Integer id) {
        Venda venda = vendaRepository.findById(id).orElse(null);

        if (venda == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        vendaProdutoRepository.deleteByVendaId(id);
        vendaRepository.deleteById(id);
    }
}
