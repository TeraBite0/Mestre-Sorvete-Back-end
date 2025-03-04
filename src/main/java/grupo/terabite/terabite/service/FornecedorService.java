package grupo.terabite.terabite.service;

import grupo.terabite.terabite.entity.Fornecedor;
import grupo.terabite.terabite.repository.FornecedorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FornecedorService {

    private final FornecedorRepository fornecedorRepository;

    public List<Fornecedor> listarFornecedor() {
        return fornecedorRepository.findAll();
    }

    public Fornecedor buscarPorId(Integer id) {
        return fornecedorRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Fornecedor buscarPorNomeFornecedor(String nomeFornecedor) {
        if (nomeFornecedor.isBlank()) throw new ResponseStatusException(HttpStatusCode.valueOf(400));

        return fornecedorRepository.findByNomeIgnoreCase(nomeFornecedor);
    }

    public Fornecedor criarFornecedor(Fornecedor novoFornecedor) {
        validarFornecedorExistente(novoFornecedor.getNome());
        return fornecedorRepository.save(novoFornecedor);
    }

    public Fornecedor atualizarFornecedor(Integer id, Fornecedor atualizarFornecedor) {
        if (!fornecedorRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        atualizarFornecedor.setId(null);
        return fornecedorRepository.save(atualizarFornecedor);
    }

    public void excluirFornecedor(Integer id) {
        if (!fornecedorRepository.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        fornecedorRepository.deleteById(id);
    }

    private void validarFornecedorExistente(String nomeFornecedor) {
        if (buscarPorNomeFornecedor(nomeFornecedor) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
    }
}
