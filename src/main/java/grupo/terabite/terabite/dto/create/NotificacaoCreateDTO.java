package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoCreateDTO {

    @NotBlank
    private String email;

    @NotBlank
    private String nomeProduto;
}
