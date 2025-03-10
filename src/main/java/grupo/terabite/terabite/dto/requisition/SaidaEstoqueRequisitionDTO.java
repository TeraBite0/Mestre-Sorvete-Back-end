package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaidaEstoqueRequisitionDTO {
    private Integer id;

    @NotNull
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdCaixasSaida;
}
