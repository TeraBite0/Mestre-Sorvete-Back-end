package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    List<Notificacao> findByProdutoId(Integer produtoId);

    void deleteByProdutoId(Integer produtoId);
}
