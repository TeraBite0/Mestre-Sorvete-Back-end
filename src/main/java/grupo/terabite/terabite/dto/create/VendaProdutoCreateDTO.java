package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaProdutoCreateDTO {

    @NotNull
    @Positive
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdVendida;
}
