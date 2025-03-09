package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarcaRequisitionDTO {

    @NotBlank
    private String nome;
}
