package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.repository.LoteRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Any;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final ProdutoService produtoService;
    // private final VendaProdutoRepository vendaProdutoRepository;
    // private final PerdaRepository perdaRepository;
    private final NotificacaoService notificacaoService;

    private List<Lote> listarLote() {
        List<Lote> lotes = loteRepository.findAll();
        if (lotes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return lotes;
    }

    public Lote buscarPorId(Integer id) {
        Lote lote = loteRepository.findById(id).orElse(null);
        if (lote == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return lote;
    }

    public Lote criarLote(Lote novoLote) {
        novoLote.setId(null);
        Produto p = produtoService.buscarPorId(novoLote.getProduto().getId());

        //        if (!p.getEmEstoque()) { // atualiza o status de estoque do produto apenas se necessário
        //            p.setEmEstoque(true);
        //            produtoService.atualizarProduto(p.getId(), p);
        //            notificacaoService.notificarProdutoEmEstoque(p);
        //        }

        return loteRepository.save(novoLote);
    }

    public Lote atualizarLote(Integer id, Lote lote) {
        if (!loteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        lote.setId(id);
        Lote novoLote = loteRepository.save(lote);

        if (produtoEmEstoque(lote.getProduto().getId()) < 1) {
            Produto p = produtoService.buscarPorId(lote.getProduto().getId());
            // p.setEmEstoque(false);
            produtoService.atualizarProduto(p.getId(), p);
        }

        return novoLote;
    }

    public void deletarLote(Integer id) {
        if (!loteRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        loteRepository.deleteById(id);
    }

    public Integer produtoEmEstoque(Integer produtoId) {
        return null;
//        Integer qtdEmEstoque = 0;
//        List<VendaProduto> vendaProdutos = vendaProdutoRepository.findByProdutoId(produtoId);
//        List<Lote> lotes = loteRepository.findByProdutoId(produtoId);
//        List<Perda> perdas = perdaRepository.findByProdutoId(produtoId);
//
//
//        for (Lote l : lotes) {
//            qtdEmEstoque += l.getQtdProdutoComprado();
//        }
//        for (VendaProduto v : vendaProdutos) {
//            qtdEmEstoque -= v.getQtdProdutosVendido();
//        }
//        for (Perda p : perdas) {
//            qtdEmEstoque -= p.getQtdProduto();
//        }
//
//        return qtdEmEstoque;
    }

    public List<Lote> buscarPorProdutoId(Integer id) {
        List<Lote> lotes = loteRepository.findByProdutoId(id);
        if (lotes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return lotes;
    }

    public List<Any> estoque() {
        return null;
//        List<Produto> produtos = produtoService.listarProduto();
//        List<Lote> lotes = loteRepository.findAll();
//        List<Perda> perdas = perdaRepository.findAll();
//        List<VendaProduto> vendas = vendaProdutoRepository.findAll();
//        List<EstoqueProduto> estoque = new ArrayList<>();
//
//        for (Produto p : produtos) {
//            Integer qtdEmEstoque = 0;
//            List<Lote> lotesDoProduto = lotes.stream()
//                    .filter(lote -> lote.getProduto().getId().equals(p.getId())).toList();
//            List<VendaProduto> vendasDoProduto = vendas.stream()
//                    .filter(venda -> venda.getProduto().getId().equals(p.getId())).toList();
//            List<Perda> perdasDoProduto = perdas.stream()
//                    .filter((perda -> perda.getProduto().getId().equals(p.getId()))).toList();
//
//            for (Lote l : lotesDoProduto) {
//                qtdEmEstoque += l.getQtdProdutoComprado();
//            }
//            for (VendaProduto v : vendasDoProduto) {
//                qtdEmEstoque -= v.getQtdProdutosVendido();
//            }
//            for (Perda pe : perdasDoProduto) {
//                qtdEmEstoque -= pe.getQtdProduto();
//            }
//
//            estoque.add(new EstoqueProduto(
//                    p.getId(),
//                    p.getNome(),
//                    p.getMarca().getNome(),
//                    p.getPreco(),
//                    qtdEmEstoque
//            ));
//        }
//
//        return estoque;
    }
}
