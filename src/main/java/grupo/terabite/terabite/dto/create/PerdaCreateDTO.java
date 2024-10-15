package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerdaCreateDTO {

    @NotBlank
    private String nome;

    @NotNull
    private Integer qtdPerda;
}
