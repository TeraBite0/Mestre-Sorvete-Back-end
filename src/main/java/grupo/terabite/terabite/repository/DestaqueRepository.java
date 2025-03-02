package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Destaque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DestaqueRepository extends JpaRepository<Destaque, Integer> {

    Destaque findByDtRecomendacao(LocalDate dtRecomendacao);

    List<Destaque> findByDtRecomendacaoBefore(LocalDate dtRecomendacao);
}
