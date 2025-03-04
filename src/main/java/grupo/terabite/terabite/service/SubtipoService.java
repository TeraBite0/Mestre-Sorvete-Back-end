package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.repository.SubtipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if (subtipos.isEmpty()) throw new ResponseStatusException(HttpStatusCode.valueOf(204));

        return subtipos;
    }

    public Subtipo buscarPorId(Integer id) {
        return subtipoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Subtipo buscarPorNomeSubtipo(String nomeSubtipo) {
        if (nomeSubtipo.isBlank()) throw new ResponseStatusException(HttpStatusCode.valueOf(400));

        return subtipoRepository.findByNomeIgnoreCase(nomeSubtipo);
    }

    public Subtipo criarSubtipo(Subtipo novoSubtipo, Integer idTipo) {
        novoSubtipo.setTipo(tipoService.buscarPorId(idTipo));
        Subtipo subtipo = buscarPorNomeSubtipo(novoSubtipo.getNome());
        validarSubtipoExistente(subtipo, novoSubtipo);

        return subtipoRepository.save(novoSubtipo);
    }

    public void validarSubtipoExistente(Subtipo subtipo, Subtipo novoSubtipo){
        if(subtipo != null){
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
