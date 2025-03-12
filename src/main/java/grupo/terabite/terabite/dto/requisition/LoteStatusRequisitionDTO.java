package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoteStatusRequisitionDTO {

    @NotBlank
    private String status;

    private String observacao;
}
