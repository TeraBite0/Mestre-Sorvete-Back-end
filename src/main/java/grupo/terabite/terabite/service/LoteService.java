package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.LoteProduto;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import grupo.terabite.terabite.repository.LoteProdutoRepository;
import grupo.terabite.terabite.repository.LoteRepository;
import grupo.terabite.terabite.repository.SaidaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
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
    public Lote criarLote(Lote novoLote) {
        novoLote.setId(null);
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

        // return buscarPorId(novoLote.getId());
        return novoLote; // testar o retorno de loteProdutos
    }


    public Lote atualizarLote(Integer id, Lote lote) {
        if (!loteRepository.existsById(id)) throw new ResponseStatusException(HttpStatusCode.valueOf(404));

        lote.setId(id);
        validarLoteProdutos(lote.getLoteProdutos());
        Lote novoLote = loteRepository.save(lote);

        // preciso atualizar individualmente cada loteProduto ?
        // atualizar estoque dos mesmos

//        if (produtoEmEstoque(lote.getProduto().getId()) < 1) {
//            Produto p = produtoService.buscarPorId(lote.getProduto().getId());
//            // p.setEmEstoque(false);
//            produtoService.atualizarProduto(p.getId(), p);
//        }

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
        produtos.forEach((p) -> {
            List<LoteProduto> loteProdutos = loteProdutoRepository.findByProdutoId(p.getId());
            List<SaidaEstoque> saidaEstoques = saidaEstoqueRepository.findByProdutoId(p.getId());

            Integer qtdCaixasEntrada = loteProdutos.stream().mapToInt(LoteProduto::getQtdCaixasCompradas).sum();
            Integer qtdCaixasSaida = saidaEstoques.stream().mapToInt(SaidaEstoque::getQtdCaixasSaida).sum();

            p.setQtdCaixasEstoque(qtdCaixasEntrada - qtdCaixasSaida);
        });
    }

    // metodo responsável por atualizar os produtos de loteProduto e apontar conflito
    private void validarLoteProdutos(List<LoteProduto> loteProdutos) {
        Set<Integer> produtosId = loteProdutos.stream().map(loteProduto -> loteProduto.getProduto().getId()).collect(Collectors.toSet());
        if(produtosId.size() != loteProdutos.size()) throw new ResponseStatusException(HttpStatus.CONFLICT);

        loteProdutos.forEach((lp) -> lp.setProduto(produtoService.buscarPorId(lp.getProduto().getId())));
    }
}
