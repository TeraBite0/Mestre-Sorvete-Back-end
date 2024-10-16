package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificacaoResponseDTO {

    private Integer id;
    private String email;
    private ProdutoResponseDTO produtoResponseDTO;
}