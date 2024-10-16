package grupo.terabite.terabite.entity.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class PrevisaoVendasPorTemperatura {
    private String data;

    private Double porcentagemVenda;
}
