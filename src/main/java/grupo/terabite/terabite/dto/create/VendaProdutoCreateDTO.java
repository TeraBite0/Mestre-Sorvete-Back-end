package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaProdutoCreateDTO {
    private Integer produtoId;
    private Integer qtdVendida;
}
