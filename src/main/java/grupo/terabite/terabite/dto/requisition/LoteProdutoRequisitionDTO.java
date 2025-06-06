package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoteProdutoRequisitionDTO {
    @NotNull
    @Positive
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdCaixasCompradas;
}