package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.repository.TipoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoService {

    private final TipoRepository tipoRepository;

    public List<Tipo> listarTipo() {
        List<Tipo> tipos = tipoRepository.findAll();
        if (tipos.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return tipos;
    }

    public Tipo buscarPorId(Integer id) {
        return tipoRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Tipo buscarPorNomeTipo(String nomeTipo) {
        if (nomeTipo.isBlank()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }

        Tipo tipo = tipoRepository.findByNomeIgnoreCase(nomeTipo);

        if (tipoRepository.findByNomeIgnoreCase(nomeTipo) == null) tipo = criarTipo(new Tipo(null, nomeTipo));

        return tipo;
    }

    public Tipo criarTipo(Tipo novoTipo) {
        return tipoRepository.save(novoTipo);
    }
}
