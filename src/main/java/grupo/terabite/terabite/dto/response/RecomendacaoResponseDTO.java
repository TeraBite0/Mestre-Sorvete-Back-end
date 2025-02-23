package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RecomendacaoResponseDTO {
    private Integer id;

    private ProdutoResponseDTO produto;
}
