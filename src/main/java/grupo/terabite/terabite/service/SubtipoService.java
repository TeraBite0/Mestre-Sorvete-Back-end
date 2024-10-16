package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.repository.SubtipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubtipoService {

    private final SubtipoRepository subtipoRepository;
    private final TipoService tipoService;

    public List<Subtipo> listarSubtipo() {
        List<Subtipo> subtipos = subtipoRepository.findAll();
        if (subtipos.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return subtipos;
    }

    public Subtipo buscarPorId(Integer id) {
        return subtipoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Subtipo buscarPorNomeSubtipo(String nomeSubtipo){
        if(nomeSubtipo.isBlank()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }

        if(subtipoRepository.findByNomeIgnoreCase(nomeSubtipo) == null){
            Subtipo novoSubtipo = new Subtipo();
            novoSubtipo.setNome(nomeSubtipo);
            novoSubtipo.setTipoPai(tipoService.buscarPorId(1));
            criarSubtipo(novoSubtipo);
        }
        return subtipoRepository.findByNomeIgnoreCase(nomeSubtipo);
    }
    public Subtipo criarSubtipo(Subtipo novoSubtipo) {
        return subtipoRepository.save(novoSubtipo);
    }
}
