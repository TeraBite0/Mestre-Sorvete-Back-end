package grupo.terabite.terabite.service.api;

import grupo.terabite.terabite.dto.external.ForecastExternalDTO;
import grupo.terabite.terabite.dto.external.HgExternalDTO;
import grupo.terabite.terabite.dto.external.WeatherResultsExternalDTO;
import grupo.terabite.terabite.generic.IOrdenadorGeneric;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HgApiService implements IOrdenadorGeneric<ForecastExternalDTO> {

    public Optional<HgExternalDTO> buscarCidade() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.hgbrasil.com/weather?key=9c8d7cf9&city_name=São+Paulo,SP", "${hgbrasil.api.key}");
        HgExternalDTO list = restTemplate.getForObject(url, HgExternalDTO.class);
        return Optional.ofNullable(list);
    }

    public Optional<WeatherResultsExternalDTO> buscarClima() {
        return buscarCidade().map(HgExternalDTO::getResults);
    }

    public Optional<List<ForecastExternalDTO>> buscarPrevisao() {
        return buscarClima().map(WeatherResultsExternalDTO::getForecast);
    }

    @Override
    public void quickSort(List<ForecastExternalDTO> list, int indInicio, int indFim) {
        int i = indInicio;
        int j = indFim;
        int pivo = list.get((indInicio + indFim) / 2).getMax();

        while (i <= j) {
            while (i < indFim && list.get(i).getMax() > pivo) i++;
            while (j > indInicio && list.get(j).getMax() < pivo) j--;

            if (i <= j) {
                ForecastExternalDTO aux = list.get(i);
                list.set(i, list.get(j));
                list.set(j, aux);
                i = i + 1;
                j = j - 1;
            }

            if (indInicio < j) quickSort(list, indInicio, j);
            if (i < indFim) quickSort(list, i, indFim);
        }
    }
}

