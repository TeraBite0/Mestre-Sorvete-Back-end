package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public List<Marca> listarMarca() {
        List<Marca> marcas = marcaRepository.findAll();
        if (marcas.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(204));
        }
        return marcas;
    }

    public Marca buscarPorId(Integer id) {
        return marcaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(204)));
    }

    public Marca buscarPorNomeMarca(String marca) {
        if (marca.isBlank()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400));
        }
        if (marcaRepository.findByNomeIgnoreCase(marca) == null) {
            Marca novaMarca = new Marca();
            novaMarca.setNome(marca);
            criarMarca(novaMarca);
        }

        return marcaRepository.findByNomeIgnoreCase(marca);
    }


    public Marca criarMarca(Marca novaMarca) {
        novaMarca.setId(null);
        return marcaRepository.save(novaMarca);
    }

    public Marca atualizarMarca(Integer id, Marca atualizarMarca) {
        if (!marcaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        atualizarMarca.setId(null);
        return marcaRepository.save(atualizarMarca);
    }

    public void deletarMarca(Integer id) {
        if (!marcaRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404));
        }
        marcaRepository.deleteById(id);
    }
}