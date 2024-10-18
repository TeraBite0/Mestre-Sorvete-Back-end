package grupo.terabite.terabite.repository.hgapi;

import grupo.terabite.terabite.entity.hgapi.TemperaturaMes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemperaturaMesRepository extends JpaRepository<TemperaturaMes, Integer> {
}
