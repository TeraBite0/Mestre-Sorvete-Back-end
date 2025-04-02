package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import grupo.terabite.terabite.entity.enums.OperacaoEstoque;
import grupo.terabite.terabite.repository.LoteProdutoRepository;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.SaidaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoteService {

    private final LoteRepository loteRepository;
    private final LoteProdutoRepository loteProdutoRepository;
    private final SaidaEstoqueRepository saidaEstoqueRepository;
    private final ProdutoService produtoService;
    private final FornecedorService fornecedorService;

    private List<Lote> listarLote() {
        List<Lote> lotes = loteRepository.findAll();
        if (lotes.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return lotes;
    }

    public Lote buscarPorId(Integer id) {
        return loteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
    }

    // Este método cria o lote separados de seus lotes produtos e retorna todos
    public Lote criarLote(Lote novoLote, String nomeFornecedor) {
        Fornecedor fornedor = fornecedorService.buscarPorNomeFornecedor(nomeFornecedor);
        novoLote.setFornecedor(fornedor);
        novoLote.setId(null);
        novoLote.getFornecedor().setId(1);
        novoLote.setStatus(LoteStatusEnum.AGUARDANDO_ENTREGA);
        novoLote.setDtPedido(LocalDate.now());

        List<LoteProduto> loteProdutos = novoLote.getLoteProdutos();
        validarLoteProdutos(loteProdutos);
        novoLote.setLoteProdutos(null);

        // salva o lote sem os produtos
        novoLote = loteRepository.save(novoLote);

        // salva os loteProdutos
        final Lote finalNovoLote = novoLote;
        loteProdutos.forEach(loteProduto -> loteProduto.setLote(finalNovoLote));
        loteProdutoRepository.saveAll(loteProdutos);

        atualizarEstoqueProduto(loteProdutos.stream().map(LoteProduto::getProduto).toList());

        deletarLotesAntigos();

        // return buscarPorId(novoLote.getId());
        return novoLote; // testar o retorno de loteProdutos
    }


    public Lote atualizarLote(Integer id, Lote lote) {
        if (!loteRepository.existsById(id)) throw new ResponseStatusException(HttpStatusCode.valueOf(404));

        lote.setId(id);

        validarLoteProdutos(lote.getLoteProdutos());
        loteProdutoRepository.saveAll(lote.getLoteProdutos());
        lote.getLoteProdutos().forEach((lp) -> lp.setLote(lote));
        atualizarEstoqueProduto(lote.getLoteProdutos().stream().map(LoteProduto::getProduto).toList());

        Lote novoLote = loteRepository.save(lote);

        return novoLote;
    }

    public void deletarLote(Integer id) {
        Lote lote = loteRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        List<LoteProduto> loteProdutos = loteProdutoRepository.findByLoteId(id);
        List<Produto> produtos = loteProdutos.stream().map(LoteProduto::getProduto).toList();

        loteProdutoRepository.deleteAll(lote.getLoteProdutos());
        loteRepository.deleteById(id);

        atualizarEstoqueProduto(produtos);
    }


    // este método atualiza o estoque do produto com base na quantidade de entrada e saida
    protected void atualizarEstoqueProduto(List<Produto> produtos){
        produtos.forEach((p) -> { // refatorar sql
            List<LoteProduto> loteProdutos = loteProdutoRepository.findByProdutoId(p.getId());
            List<SaidaEstoque> saidaEstoques = saidaEstoqueRepository.findByProdutoId(p.getId());

//            Integer qtdCaixasEntrada = loteProdutos.stream().mapToInt(LoteProduto::getQtdCaixasCompradas).sum();
            Integer qtdCaixasSaida = saidaEstoques.stream().mapToInt(SaidaEstoque::getQtdCaixasSaida).sum();

            produtoService.atualizarQtdCaixaEstoque(p.getId(), qtdCaixasSaida, OperacaoEstoque.RETIRAR);
        });
    }

    // metodo responsável por atualizar os produtos de loteProduto e apontar conflito
    private void validarLoteProdutos(List<LoteProduto> loteProdutos) {
        Set<Integer> produtosId = loteProdutos.stream().map(loteProduto -> loteProduto.getProduto().getId()).collect(Collectors.toSet());
        if(produtosId.size() != loteProdutos.size()) throw new ResponseStatusException(HttpStatus.CONFLICT);

        loteProdutos.forEach((lp) -> lp.setProduto(produtoService.buscarPorId(lp.getProduto().getId())));
    }

    public Lote atualizarStatusLote(Integer id, Lote atualizarStatusLote) {
        Lote lote = buscarPorId(id);

        if(lote.getStatus().getStatus().equals("Entregue")){
            throw new ResponseStatusException(HttpStatusCode.valueOf(409));
        }

        lote.setStatus(atualizarStatusLote.getStatus());
        lote.setObservacao(atualizarStatusLote.getObservacao());

        loteRepository.save(lote);

        if(atualizarStatusLote.getStatus().getStatus().equals("Entregue")){
            lote.getLoteProdutos().forEach(produto -> {
                produtoService.atualizarQtdCaixaEstoque(produto.getProduto().getId(), produto.getQtdCaixasCompradas(), OperacaoEstoque.INSERIR);
            });
        }

        return lote;
    }

    private void deletarLotesAntigos() {
        List<Lote> lotesAntigos = loteRepository.findByDtEntregaBefore(LocalDate.now().minusYears(1)); // lotes com data de entrega > 1 ano
        List<LoteProduto> loteProdutosAntigos = new ArrayList<>();

        for (Lote l : lotesAntigos) {
            loteProdutosAntigos.addAll(l.getLoteProdutos());
        }

        loteRepository.deleteAll(lotesAntigos);
        loteProdutoRepository.deleteAll(loteProdutosAntigos);
    }
}
