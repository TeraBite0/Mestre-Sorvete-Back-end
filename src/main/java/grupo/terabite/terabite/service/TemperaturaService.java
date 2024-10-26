package grupo.terabite.terabite.service;

import grupo.terabite.terabite.dto.external.ForecastExternalDTO;
import grupo.terabite.terabite.entity.hgapi.TemperaturaDia;
import grupo.terabite.terabite.entity.hgapi.TemperaturaMes;
import grupo.terabite.terabite.repository.hgapi.TemperaturaDiaRepository;
import grupo.terabite.terabite.repository.hgapi.TemperaturaMesRepository;
import grupo.terabite.terabite.service.api.HgApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemperaturaService {

    private final HgApiService hgApiService;
    private final TemperaturaDiaRepository temperaturaDiaRepository;
    private final TemperaturaMesRepository temperaturaMesRepository;

    @Scheduled(cron = "0 0 12 * * ?", zone = "America/Sao_Paulo") // ESPECIFICA MÉTODO PARA RODAR TODO DIA 12AM
    public void salvarTemperatura(){
        LocalDate hoje = LocalDate.now();

        ForecastExternalDTO previsao = hgApiService.buscarPrevisao().get().get(0);
        Double temperaturaMediaHoje = (Double.valueOf(previsao.getMin()) + Double.valueOf(previsao.getMax())) / 2;

        if(hoje.getDayOfMonth() == 1){
            List<TemperaturaDia> temperaturaDias = temperaturaDiaRepository.buscarPorMes(hoje.minusDays(10));

            Double temperaturaMediaMes = temperaturaDias.get(temperaturaDias.size() / 2).getTemperaturaMedia(); // PEGA MEDIANA DO MÊS

            temperaturaMesRepository.save(new TemperaturaMes(null, hoje.minusMonths(1), temperaturaMediaMes));

            temperaturaDiaRepository.deletarDadosAntigos(hoje.minusMonths(2));
            temperaturaMesRepository.deletarDadosAntigos(hoje.minusMonths(6));
        }
        temperaturaDiaRepository.save(new TemperaturaDia(null, hoje, temperaturaMediaHoje));
    }

}
