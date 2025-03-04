package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Produto findByNomeIgnoreCaseOrderByNome(String nome);

    List<Produto> findAllByOrderByNome();

    List<Produto> findByIsAtivoTrueOrderByNome();

    List<Produto> findByNomeContainingIgnoreCaseOrMarca_NomeContainingIgnoreCaseOrderByNome(String termo, String termoMarca);

    List<Produto> findByNomeIgnoreCaseContainingOrSubtipo_Tipo_NomeIgnoreCaseContainingOrderByNome(String termo, String termoTipo);
}
