package grupo.terabite.terabite.dto.update;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerdaUpdateDTO {

    @NotNull
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdPerda;
}
