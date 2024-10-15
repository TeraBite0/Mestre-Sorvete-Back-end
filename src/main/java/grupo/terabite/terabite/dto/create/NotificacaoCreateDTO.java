package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoCreateDTO {

    @NotBlank
    private String email;
    @NotBlank
    private Produto produto;
}
