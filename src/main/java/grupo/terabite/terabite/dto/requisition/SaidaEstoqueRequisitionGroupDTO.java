package grupo.terabite.terabite.dto.requisition;

import grupo.terabite.terabite.dto.response.SaidaEstoqueResponseDTO;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SaidaEstoqueRequisitionGroupDTO {

    private LocalDate dtSaida;

    @NotNull @Max(15)
    private List<SaidaEstoqueRequisitionDTO> saidaEstoques;
}
