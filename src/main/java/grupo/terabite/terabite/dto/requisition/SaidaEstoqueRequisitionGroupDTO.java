package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class SaidaEstoqueRequisitionGroupDTO {

    private LocalDate dtSaida;

    @NotNull @Size(max = 15, message = "A lista pode ter no máximo 15 itens.")
    private List<SaidaEstoqueRequisitionDTO> saidaEstoques;
}
