package grupo.terabite.terabite.repository;

import grupo.terabite.terabite.entity.Subtipo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubtipoRepository extends JpaRepository<Subtipo, Integer> {

    Subtipo findByNomeIgnoreCase(String subtipo);
}
