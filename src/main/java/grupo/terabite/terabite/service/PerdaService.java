package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Perda;
import grupo.terabite.terabite.repository.PerdaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PerdaService {

    private final PerdaRepository perdaRepository;
    private final ProdutoService  produtoService;

    public List<Perda> listarPerda(){
        List<Perda> perdas = perdaRepository.findAll();
        if(perdas.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return perdas;
    }

    public Perda buscarPerdaPorId(Integer id){
        Optional<Perda> perdasOpt = perdaRepository.findById(id);
        if(perdasOpt.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return perdasOpt.get();
    }

    public Perda criarPerda(Perda novaPerda, String nome){
        novaPerda.setProduto(produtoService.buscarPorNomeProduto(nome));
        return perdaRepository.save(novaPerda);
    }

    public Perda atualizarPerda(Integer id, Perda atualizarPerda){
        if(!perdaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        atualizarPerda.setId(null);
        return perdaRepository.save(atualizarPerda);
    }

    public void deletarPerda(Integer id){
        if(!perdaRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        perdaRepository.deleteById(id);
    }
}
