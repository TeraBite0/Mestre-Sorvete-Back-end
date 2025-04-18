package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.repository.MarcaRepository;
import grupo.terabite.terabite.repository.ProdutoRepository;
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
    private final ProdutoRepository produtoRepository;

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

        return marcaRepository.findByNomeIgnoreCase(nomeMarca);
    }

    public Marca criarMarca(Marca novaMarca) {
        validarMarcaExistente(novaMarca.getNome());
        return marcaRepository.save(novaMarca);
    }

    public Marca atualizarMarca(Integer id, Marca atualizarMarca) {
        if (!marcaRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        atualizarMarca.setId(null);
        return marcaRepository.save(atualizarMarca);
    }

    public void deletarMarca(Integer id) {
        if (!marcaRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        List<Produto> produtosPorMarca = produtoRepository.findByMarcaId(id);
        if(!produtosPorMarca.isEmpty()) throw new ResponseStatusException(HttpStatus.CONFLICT, "Um ou mais produtos estão associado a esta marca!");
        marcaRepository.deleteById(id);
    }

    private void validarMarcaExistente(String nomeMarca) {
        if (buscarPorNomeMarca(nomeMarca) != null) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Marca já cadastrada");
        }
    }
}