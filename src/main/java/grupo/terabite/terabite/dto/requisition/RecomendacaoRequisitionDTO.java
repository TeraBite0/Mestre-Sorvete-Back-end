package grupo.terabite.terabite.dto.requisition;

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

    @Positive
    private Integer produtoId;
}
