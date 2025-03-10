package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.SaidaEstoque;
import grupo.terabite.terabite.repository.SaidaEstoqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaidaEstoqueService {

    private final SaidaEstoqueRepository saidaEstoqueRepository;
    private final LoteService loteService;
    private  final ProdutoService produtoService;

    public List<SaidaEstoque> listar(){
        List<SaidaEstoque> saidaEstoques = saidaEstoqueRepository.findAll();
        if(saidaEstoques.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));

        return saidaEstoques;
    }

    public List<SaidaEstoque> registrarSaida(List<SaidaEstoque> saidaEstoques){
        saidaEstoques.forEach(se -> se.setProduto(produtoService.buscarPorId(se.getProduto().getId()))); // atualiza os seus produtos com seus respectivos ids
        List<SaidaEstoque> saidaEstoquesNova = saidaEstoqueRepository.saveAll(saidaEstoques);
        loteService.atualizarEstoqueProduto(saidaEstoques.stream().map(SaidaEstoque::getProduto).toList());
        return saidaEstoquesNova;
    }

    public SaidaEstoque editarSaida(Integer id, SaidaEstoque saidaEstoqueAtualizada){
        SaidaEstoque saidaEstoque = saidaEstoqueRepository.findById(id).orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND));

        saidaEstoqueAtualizada.setId(id);

        if(saidaEstoque.getProduto().getId() != saidaEstoqueAtualizada.getProduto().getId()){
            saidaEstoqueAtualizada.setProduto(produtoService.buscarPorId(saidaEstoqueAtualizada.getProduto().getId()));
        } else {
            saidaEstoqueAtualizada.setProduto(saidaEstoque.getProduto());
        }
        saidaEstoqueAtualizada.setDtSaida(saidaEstoque.getDtSaida());
        
        saidaEstoqueAtualizada = saidaEstoqueRepository.save(saidaEstoqueAtualizada);
        loteService.atualizarEstoqueProduto(List.of(saidaEstoqueAtualizada.getProduto()));
        return saidaEstoqueAtualizada;
    }

    public void deletarSaidas(List<SaidaEstoque> saidaEstoques){
        Set<Integer> ids = saidaEstoques.stream().map(SaidaEstoque::getId).collect(Collectors.toSet());

        if(saidaEstoqueRepository.findAllById(ids).size() != ids.size()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        saidaEstoqueRepository.deleteAllById(ids);
        loteService.atualizarEstoqueProduto(saidaEstoques.stream().map(SaidaEstoque::getProduto).toList());
    }
}
