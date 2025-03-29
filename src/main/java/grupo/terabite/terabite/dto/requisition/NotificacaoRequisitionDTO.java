package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificacaoRequisitionDTO {

    @NotBlank
    private String email;

    @NotEmpty @Size(max = 20, message = "A lista pode ter no máximo 20 itens.")
    private List<Integer> idProdutos;
}
