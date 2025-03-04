package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecomendacaoCreateDTO {

    @Positive
    private Integer produtoId;
}
