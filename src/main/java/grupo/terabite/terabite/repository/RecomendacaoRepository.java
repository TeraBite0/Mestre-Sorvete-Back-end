package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Recomendacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecomendacaoRepository extends JpaRepository<Recomendacao, Integer> {
    List<Recomendacao> findByProdutoId(Integer produtoId);
}