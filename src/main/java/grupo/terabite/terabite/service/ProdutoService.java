package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarcaService marcaService;
    private final SubtipoService subtipoService;

    public List<Produto> listarProduto() {
        return produtoRepository.findAll();
    }

    public List<Produto> listarProdutoIsAtivos() {
        List<Produto> produtos = produtoRepository.findByIsAtivoTrueOrderByNome();
        if(produtos.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return produtos;
    }

    public List<Produto> buscarPorTermo(String termo, String marca) {
        List<Produto> produtos = produtoRepository.findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCaseOrderByNome(termo, marca);
        return produtos;
    }

    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Produto buscarPorNomeProduto(String nomeProduto) {
        if (produtoRepository.findByNomeIgnoreCaseOrderByNome(nomeProduto) == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        return produtoRepository.findByNomeIgnoreCaseOrderByNome(nomeProduto);
    }

    public List<Produto> buscarPorFiltroTipoOuNome(String nome, String tipo) {
        return produtoRepository.findByNomeIgnoreCaseContainingOrSubtipo_Tipo_NomeIgnoreCaseContainingOrderByNome(nome, tipo);
    }

    public Produto criarProduto(Produto produto, String nomeMarca, String nomeSubtipo) {
        produto.setMarca(marcaService.buscarPorNomeMarca(nomeMarca));
        produto.setSubtipo((subtipoService.buscarPorNomeSubtipo(nomeSubtipo)));
        produto.setIsAtivo(true);
        produto.setDisponivel(false);
        produto.setQtdCaixasEstoque(0);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, Produto produtoAtualizado, String nomeMarca, String nomeSubtipo) {
        Produto produtoAntigo = produtoRepository.findById(id).orElse(null);
        if (produtoAntigo == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        produtoAtualizado.setId(id);
        produtoAtualizado.setMarca(marcaService.buscarPorNomeMarca(nomeMarca));
        produtoAtualizado.setSubtipo((subtipoService.buscarPorNomeSubtipo(nomeSubtipo)));
        return produtoRepository.save(produtoAtualizado);
    }

    protected Produto atualizarProduto(Integer id, Produto produtoAtualizado){
        Produto produtoAntigo = produtoRepository.findById(id).orElse(null);
        if (produtoAntigo == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        produtoAtualizado.setId(id);
        return produtoRepository.save(produtoAtualizado);
    }
}