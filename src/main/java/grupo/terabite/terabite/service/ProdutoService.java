package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarcaService marcaService;
    private final SubtipoService subtipoService;
    private final VendaProdutoRepository vendaProdutoRepository;

    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Produto buscarPorNomeProduto(String nomeProduto) {
        if (nomeProduto.isBlank()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        if (produtoRepository.findByNomeIgnoreCase(nomeProduto) == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return produtoRepository.findByNomeIgnoreCase(nomeProduto);
    }

    public Produto criarProduto(Produto produto, String nomeMarca, String nomeSubtipo) {
        produto.setMarca(marcaService.buscarPorNomeMarca(nomeMarca));
        produto.setEmEstoque(false);
        produto.setSubtipo((subtipoService.buscarPorNomeSubtipo(nomeSubtipo)));
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, Produto produtoAtualizado) {
        Produto produtoAntigo = produtoRepository.findById(id).orElse(null);
        if (produtoAntigo == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        produtoAtualizado.setId(id);
        if (produtoAtualizado.getEmEstoque() == null) {
            produtoAtualizado.setEmEstoque(produtoAntigo.getEmEstoque());
        }
        return produtoRepository.save(produtoAtualizado);
    }

    public List<Produto> popular() {
        List<VendaProduto> vendas = vendaProdutoRepository.findTop5ByOrderByQtdProdutosVendidoDesc();
        return vendas.stream().map(VendaProduto::getProduto).collect(Collectors.toList());
    }
}

