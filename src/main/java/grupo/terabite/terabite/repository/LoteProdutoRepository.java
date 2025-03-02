package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.LoteProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoteProdutoRepository extends JpaRepository<LoteProduto, Integer> {
}
