package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Venda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends JpaRepository<Venda, Integer> {
    List<Venda> findAllByOrderByDataCompraDesc();
    List<Venda> findByDataCompra(LocalDateTime data);
}
