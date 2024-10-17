package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VendaProdutoResponseDTO {

    private ProdutoResponseDTO produto;

    private Integer qtdVendida;
}