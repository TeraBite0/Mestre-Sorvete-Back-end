package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.SaidaEstoque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SaidaEstoqueRepository extends JpaRepository<SaidaEstoque, Integer> {
    List<SaidaEstoque> findByDtSaidaAfter(LocalDate dtSaida);
}
