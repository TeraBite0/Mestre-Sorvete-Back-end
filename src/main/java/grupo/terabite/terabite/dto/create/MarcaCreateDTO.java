package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarcaCreateDTO {
    @NotBlank
    private String nome;
}
