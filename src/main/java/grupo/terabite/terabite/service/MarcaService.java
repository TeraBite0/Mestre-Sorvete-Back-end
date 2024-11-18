package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
        return marcaRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Marca buscarPorNomeMarca(String nomeMarca) {
        if (nomeMarca.isBlank()) throw new ResponseStatusException(HttpStatusCode.valueOf(400));

        Marca marca = marcaRepository.findByNomeIgnoreCase(nomeMarca);

        if (marca == null) marca = criarMarca(new Marca(null, nomeMarca));

        return marca;
    }

    public Marca criarMarca(Marca novaMarca) {
        novaMarca.setId(null);
        return marcaRepository.save(novaMarca);
    }

    public Marca atualizarMarca(Integer id, Marca atualizarMarca) {
        if (!marcaRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        atualizarMarca.setId(null);
        return marcaRepository.save(atualizarMarca);
    }

    public void deletarMarca(Integer id) {
        if (!marcaRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        marcaRepository.deleteById(id);
    }
}