package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Integer> {
    List<Recomendacao> findByProdutoId(Integer produtoId);
}