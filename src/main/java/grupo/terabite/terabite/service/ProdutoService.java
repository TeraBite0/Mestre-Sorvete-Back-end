package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final MarcaService marcaService;
    private final TipoService tipoRepository;

    public List<Produto> listarProduto() {
        List<Produto> produtos = produtoRepository.findByIsAtivoTrue();
        // fazer lógica que caso seja passado um token de autentificação, será retornado todos produtos, mesmo inativos
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

    public Produto criarProduto(Produto produto, String nomeMarca, String nomeTipo) {
        produto.setMarca(marcaService.buscarPorNomeMarca(nomeMarca));
        // produto.setSubtipo((tipoRepository.buscarPorNomeTipo(nomeTipo))); CONSERTAR
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

