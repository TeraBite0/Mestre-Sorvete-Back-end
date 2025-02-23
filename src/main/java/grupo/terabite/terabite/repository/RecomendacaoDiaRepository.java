package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.RecomendacaoDia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface RecomendacaoDiaRepository extends JpaRepository<RecomendacaoDia, Integer> {

    RecomendacaoDia findByDtRecomendacao(LocalDate dtRecomendacao);

    List<RecomendacaoDia> findByDtRecomendacaoBefore(LocalDate dtRecomendacao);
}
