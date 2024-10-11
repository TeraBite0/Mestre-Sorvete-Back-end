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
    private final TipoService tipoRepository;
    private final SubtipoService subtipoService;

    public List<Produto> listarProduto() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos;
    }

    public Produto buscarPorId(Integer id) {
        return produtoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Produto buscarPorNomeProduto(String nomeProduto){
        if(nomeProduto.isBlank()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        if(produtoRepository.findByNomeIgnoreCase(nomeProduto) == null){
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
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        produtoAtualizado.setId(id);
        return produtoRepository.save(produtoAtualizado);
    }
}

