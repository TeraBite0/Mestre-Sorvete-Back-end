package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Produto findByNomeIgnoreCase(String nome);

    List<Produto> findByIsAtivoTrue();

    List<Produto> findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCase(String termo, String termoMarca);

    List<Produto> findByNomeIgnoreCaseContainingOrSubtipo_TipoPai_NomeIgnoreCaseContaining(String termo, String termoTipo);
}
