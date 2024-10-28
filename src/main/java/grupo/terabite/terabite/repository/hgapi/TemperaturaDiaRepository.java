package grupo.terabite.terabite.repository.hgapi;

import grupo.terabite.terabite.entity.hgapi.TemperaturaDia;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TemperaturaDiaRepository extends JpaRepository<TemperaturaDia, Integer> {

    @Query("SELECT td FROM TemperaturaDia td WHERE MONTH(td.dtTemperatura) = MONTH(:dtMes) ORDER BY td.temperaturaMedia")
    List<TemperaturaDia> buscarPorMes(@Param("dtMes") LocalDate dtMes);

    @Modifying
    @Transactional
    @Query("DELETE FROM TemperaturaDia td WHERE td.dtTemperatura < :data")
    void deletarDadosAntigos(@Param("data") LocalDate data);
}
