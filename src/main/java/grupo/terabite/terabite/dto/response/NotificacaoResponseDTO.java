package grupo.terabite.terabite.dto.response;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoResponseDTO {

    private Integer id;
    private String email;
    private Produto produto;
}
