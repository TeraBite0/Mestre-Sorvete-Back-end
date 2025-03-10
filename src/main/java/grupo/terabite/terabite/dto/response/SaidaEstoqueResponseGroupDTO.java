package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SaidaEstoqueResponseGroupDTO {
    private LocalDate dtSaida;

    private List<SaidaEstoqueResponseDTO> saidaEstoques;
}
