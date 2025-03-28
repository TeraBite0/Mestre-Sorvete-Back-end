package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecomendacaoRequisitionDTO {

    @NotNull
    @Positive
    private Integer produtoId;
}
