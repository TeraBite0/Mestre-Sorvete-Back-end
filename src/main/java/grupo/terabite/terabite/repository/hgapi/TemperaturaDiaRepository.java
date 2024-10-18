package grupo.terabite.terabite.repository.hgapi;

import grupo.terabite.terabite.entity.hgapi.TemperaturaDia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperaturaDiaRepository extends JpaRepository<TemperaturaDia, Integer> {
}
