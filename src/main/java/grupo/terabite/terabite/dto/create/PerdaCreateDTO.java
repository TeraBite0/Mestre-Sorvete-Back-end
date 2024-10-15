package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerdaCreateDTO {

    @NotNull
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdPerda;
}
