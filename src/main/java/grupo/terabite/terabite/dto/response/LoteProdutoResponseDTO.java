package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoteProdutoResponseDTO {

    private ProdutoResponseDTO produto;

    private Integer qtdCaixasCompradas;
}