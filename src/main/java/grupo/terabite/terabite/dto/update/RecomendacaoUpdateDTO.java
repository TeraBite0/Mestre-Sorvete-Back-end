package grupo.terabite.terabite.dto.update;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecomendacaoUpdateDTO {
    @NotNull
    private Integer produtoId;
}
