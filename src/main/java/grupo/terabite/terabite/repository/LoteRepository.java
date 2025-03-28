package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LoteRepository extends JpaRepository<Lote, Integer> {

    List<Lote> findByDtEntregaBefore(LocalDate dtEntrega);
}
