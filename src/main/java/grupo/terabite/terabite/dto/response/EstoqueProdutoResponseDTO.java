package grupo.terabite.terabite.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EstoqueProdutoResponseDTO {

    private Integer codigo;
    private String produto;
    private String marca;
    private Integer qtdEstoque;
}
