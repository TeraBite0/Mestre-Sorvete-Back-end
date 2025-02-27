package grupo.terabite.terabite.service;

import grupo.terabite.terabite.dto.internal.ProdutoQuantidadeDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.ProdutoRepository;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return produtoRepository.findByNomeIgnoreCaseContainingOrSubtipo_TipoPai_NomeIgnoreCaseContainingOrderByNome(nome, tipo);
    }

    public Produto criarProduto(Produto produto, String nomeMarca, String nomeSubtipo) {
        produto.setMarca(marcaService.buscarPorNomeMarca(nomeMarca));
        produto.setEmEstoque(false);
        produto.setSubtipo((subtipoService.buscarPorNomeSubtipo(nomeSubtipo)));
        produto.setIsAtivo(true);
        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Integer id, Produto produtoAtualizado, String nomeMarca, String nomeSubtipo) {
        Produto produtoAntigo = produtoRepository.findById(id).orElse(null);
        if (produtoAntigo == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        produtoAtualizado.setId(id);
        if (produtoAtualizado.getEmEstoque() == null) {
            produtoAtualizado.setEmEstoque(produtoAntigo.getEmEstoque());
        }
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
        if (produtoAtualizado.getEmEstoque() == null) {
            produtoAtualizado.setEmEstoque(produtoAntigo.getEmEstoque());
        }
        return produtoRepository.save(produtoAtualizado);
    }

    public List<Produto> popular() {
        List<Produto> populares = new ArrayList<>();
        LocalDate mesPassado = LocalDate.now().minusMonths(2);
        List<ProdutoQuantidadeDTO> produtoQuantidadeDTO = Optional.of(vendaProdutoRepository.qtdVendidosPorMesEAno(mesPassado.getMonthValue(), mesPassado.getYear()))
                .filter(list -> !list.isEmpty())
                .orElseThrow(() -> new IllegalStateException("Não tem produtos vendidos nesse mês ainda"));

        int tamanhoDaListaProduto = produtoQuantidadeDTO.size();
        if(tamanhoDaListaProduto < 5){
            produtoQuantidadeDTO.forEach(produto ->{
                populares.add(produto.getProduto());
            });
        } else {
            produtoQuantidadeDTO.stream().limit(5).forEach(produto ->{
                populares.add(produto.getProduto());
            });
        }

        return populares;
    }
}