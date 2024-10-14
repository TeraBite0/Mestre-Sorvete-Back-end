package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Perda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerdaRepository extends JpaRepository<Perda, Integer> {
    List<Perda> findByProdutoId(Integer produtoId);
}
