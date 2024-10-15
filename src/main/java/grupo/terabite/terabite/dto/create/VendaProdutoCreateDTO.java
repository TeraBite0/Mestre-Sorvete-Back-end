package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaProdutoCreateDTO {

    @NotNull
    private Integer produtoId;

    @NotNull
    private Integer qtdVendida;
}
