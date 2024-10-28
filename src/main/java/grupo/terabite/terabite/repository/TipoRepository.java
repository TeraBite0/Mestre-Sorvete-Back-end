package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Tipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

    Tipo findByNomeIgnoreCase(String tipo);
}
