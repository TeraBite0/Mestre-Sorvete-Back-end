package grupo.terabite.terabite.service.api;

import grupo.terabite.terabite.dto.external.ForecastExternalDTO;
import grupo.terabite.terabite.dto.external.HgExternalDTO;
import grupo.terabite.terabite.dto.external.WeatherResultsExternalDTO;
import grupo.terabite.terabite.generic.IOrdenadorGeneric;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class HgApiService implements IOrdenadorGeneric {

    public Optional<HgExternalDTO> buscarCidade() {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("https://api.hgbrasil.com/weather?key=9c8d7cf9&city_name=São+Paulo,SP", "${hgbrasil.api.key}");
        HgExternalDTO list = restTemplate.getForObject(url, HgExternalDTO.class);
        return Optional.ofNullable(list);
    }

    public Optional<WeatherResultsExternalDTO> buscarClima(){
        return buscarCidade().map(HgExternalDTO::getResults);
    }

    public Optional<List<ForecastExternalDTO>> buscarPrevisao(){
        return buscarClima().map(WeatherResultsExternalDTO::getForecast);
    }

    @Override
    public List ordenarMaximo() {
    List<ForecastExternalDTO> list = buscarPrevisao().get();
        for (int i = 1; i < list.size(); ++i) {
            ForecastExternalDTO key = list.get(i);
            int j = i - 1;
            while (j >= 0 && (list.get(j).getMax() < key.getMax())) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
        return list;
    }
}
