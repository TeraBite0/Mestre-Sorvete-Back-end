package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoRequisitionDTO {

    @NotBlank
    private String email;

    @NotNull
    @Positive
    private Integer idProduto;
}
