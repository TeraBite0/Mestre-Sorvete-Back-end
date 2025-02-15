package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.dto.internal.ProdutoQuantidadeDTO;
import grupo.terabite.terabite.entity.VendaProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VendaProdutoRepository extends JpaRepository<VendaProduto, Integer> {


    List<VendaProduto> findByVendaId(Integer vendaId);

    List<VendaProduto> findByProdutoId(Integer produtoId);

    void deleteByVendaId(Integer vendaId);

    @Query("SELECT vp FROM Venda v JOIN v.produtos vp WHERE MONTH(v.dataCompra) = :mes AND YEAR(v.dataCompra) = :ano")
    List<VendaProduto> procurarVendasPorMesEAno(@Param("mes") Integer mes, @Param("ano") Integer ano);


    @Query("SELECT new grupo.terabite.terabite.dto.internal.ProdutoQuantidadeDTO(vp.produto, SUM(vp.qtdProdutosVendido)) FROM VendaProduto vp WHERE MONTH(vp.venda.dataCompra) = :mes AND YEAR(vp.venda.dataCompra) = :ano AND vp.produto.isAtivo = true GROUP BY vp.produto")
    List<ProdutoQuantidadeDTO> qtdVendidosPorMesEAno(@Param("mes") Integer mes, @Param("ano") Integer ano);
}
