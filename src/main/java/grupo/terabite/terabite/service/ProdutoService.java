package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.enums.OperacaoEstoque;
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
        return produtoRepository.findAllByOrderByNome();
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
        validarMarcaESubtipoExistentes(nomeMarca, nomeSubtipo, produto);
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
        validarMarcaESubtipoExistentes(nomeMarca, nomeSubtipo, produtoAtualizado);
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

    private void validarMarcaESubtipoExistentes(String nomeMarca, String nomeSubtipo, Produto produto) {
        Marca marca = marcaService.buscarPorNomeMarca(nomeMarca);
        Subtipo subtipo = subtipoService.buscarPorNomeSubtipo(nomeSubtipo);
        if (marca == null || subtipo == null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }

        produto.setMarca(marca);
        produto.setSubtipo(subtipo);
    }

    public Produto atualizarQtdCaixaEstoque(Integer idProduto, Integer qtdCaixaEstoque, OperacaoEstoque operacao) {
        Produto produto = buscarPorId(idProduto);
        validarQtdCaixaEstoque(produto, qtdCaixaEstoque, operacao);
        return produtoRepository.save(produto);
    }

    private void validarQtdCaixaEstoque(Produto produto, Integer novaQtdCaixaEstoque, OperacaoEstoque operacao) {
        int qtdCaixaAtual = produto.getQtdCaixasEstoque();

        if(operacao == OperacaoEstoque.INSERIR) {
            qtdCaixaAtual += novaQtdCaixaEstoque;

        } else if (operacao == OperacaoEstoque.RETIRAR){
            qtdCaixaAtual -= novaQtdCaixaEstoque;

            if(qtdCaixaAtual < 0){
                throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Quantidade de caixas em estoque insuficiente");
            }
        }

        produto.setQtdCaixasEstoque(qtdCaixaAtual);
    }

    public Produto atualizarProdutoAtivo(Integer id, boolean isAtivo) {
        Produto produto = buscarPorId(id);
        produto.setIsAtivo(isAtivo);

        return produtoRepository.save(produto);
    }
}