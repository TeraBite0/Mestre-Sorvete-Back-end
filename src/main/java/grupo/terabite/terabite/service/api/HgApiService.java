package grupo.terabite.terabite.service.api;

import grupo.terabite.terabite.dto.external.ForecastExternalDTO;
import grupo.terabite.terabite.dto.external.HgExternalDTO;
import grupo.terabite.terabite.dto.external.WeatherResultsExternalDTO;
import grupo.terabite.terabite.generic.IOrdenadorGeneric;
import org.springframework.cglib.core.Local;
import org.springframework.data.repository.core.RepositoryCreationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public ForecastExternalDTO buscaBinaria(List<ForecastExternalDTO> list, Object indBusca) {
        LocalDate busca = LocalDate.parse((String) indBusca);
        Integer indinf = 0;
        Integer indsup = list.size() - 1;
        while (indinf <= indsup){
            Integer meio = (indinf + indsup) / 2;
            String dia = list.get(meio).getDate().substring(0,2);
            String mes = list.get(meio).getDate().substring(3,5);
            String data = "2024-"+mes+"-"+dia;
            if(LocalDate.parse(data).equals(busca)){
                return list.get(meio);
            } else if (busca.isBefore(LocalDate.parse(data))){
                indsup = meio - 1;
            } else {
                indinf = meio + 1;
            }
        }
        throw new ResponseStatusException(HttpStatusCode.valueOf(404));
    }
}

