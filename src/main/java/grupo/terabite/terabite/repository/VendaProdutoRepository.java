package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Integer> {

    List<VendaProduto> findByVendaId(Integer vendaId);

    void deleteByVendaId(Integer vendaId);
}
