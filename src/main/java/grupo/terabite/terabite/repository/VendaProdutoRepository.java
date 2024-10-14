package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Integer> {

    List<VendaProduto> findByVendaId(Integer vendaId);

    List<VendaProduto> findByProdutoId(Integer produtoId);
    void deleteByVendaId(Integer vendaId);


//    @Query("SELECT vp FROM VendaProduto vp JOIN vp.produto p ORDER BY vp.qtdProdutosVendido DESC")
//    List<VendaProduto> findTop5ByQtdProdutosVendido();
    List<VendaProduto> findTop5ByOrderByQtdProdutosVendidoDesc();
}
