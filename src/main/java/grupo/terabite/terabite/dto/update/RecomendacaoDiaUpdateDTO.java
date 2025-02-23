package grupo.terabite.terabite.dto.update;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecomendacaoDiaUpdateDTO {

    @NotNull
    private ProdutoCreateDTO produto;

    @NotBlank
    private String texto;
}
