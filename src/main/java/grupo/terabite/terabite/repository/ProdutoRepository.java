package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Produto findByNomeIgnoreCase(String nome);

    List<Produto> findByIsAtivoTrue();
}
