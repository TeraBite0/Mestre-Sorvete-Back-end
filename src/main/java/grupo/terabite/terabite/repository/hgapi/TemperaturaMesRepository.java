package grupo.terabite.terabite.repository.hgapi;

import grupo.terabite.terabite.entity.hgapi.TemperaturaMes;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TemperaturaMesRepository extends JpaRepository<TemperaturaMes, Integer> {

    @Modifying
    @Transactional
    @Query("DELETE FROM TemperaturaMes tm WHERE tm.dtMes < :data")
    void deletarDadosAntigos(@Param("data") LocalDate data);
}
