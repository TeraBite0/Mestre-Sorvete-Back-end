package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.repository.VendaProdutoRepository;
import grupo.terabite.terabite.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VendaProdutoRepository vendaProdutoRepository;

    public List<Venda> listarVenda(){
        List<Venda> vendas = vendaRepository.findAllByOrderByDtVendaDesc();
        if(vendas.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return vendas;
    }

    public Venda buscarVendaPorId(Integer id){
        Optional<Venda> vendasOpt = vendaRepository.findById(id);
        if(vendasOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return vendasOpt.get();
    }

    public Venda criarVenda(List<VendaProduto> vendaProdutos){
        Venda novaVenda = new Venda(null, LocalDateTime.now());

        Venda venda =  vendaRepository.save(novaVenda);
        for(VendaProduto vp: vendaProdutos){
            vp.setVenda(venda);

            if(vp.getQtdProdutosVendido() < 0 || !vp.getProduto().getIsAtivo()){ // Validação da venda (falta validar se o protudo existe em estoque)
                vendaRepository.deleteById(venda.getId());
                throw new ResponseStatusException(HttpStatusCode.valueOf(400));
            }
        }

        vendaProdutoRepository.saveAll(vendaProdutos);
        return venda;
    }


    public List<VendaProduto> buscarProdutosPorVenda(Integer vendaId){
        return vendaProdutoRepository.findByVenda(vendaId);
    }
    public Venda atualizarVenda(Integer id, Venda atualizarVenda){
        if(!vendaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        atualizarVenda.setId(null);
        return vendaRepository.save(atualizarVenda);
    }

    public void deletarVenda(Integer id){
        if(!vendaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        vendaRepository.deleteById(id);
    }
}
