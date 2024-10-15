package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoCreateDTO {

    private Integer id;

    @NotBlank
    private String email;

    @NotNull
    private Produto produto;
}
