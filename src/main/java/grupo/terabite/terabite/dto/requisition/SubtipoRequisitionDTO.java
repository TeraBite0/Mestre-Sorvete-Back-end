package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubtipoRequisitionDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String nomeTipo;
}
