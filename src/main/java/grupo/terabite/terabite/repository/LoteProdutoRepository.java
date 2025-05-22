package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.LoteProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoteProdutoRepository extends JpaRepository<LoteProduto, Integer> {
    List<LoteProduto> findByLoteId(Integer loteId);

    List<LoteProduto> findByProdutoIdAndLoteStatusId(Integer produtoId, Integer statusId);
}
