package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TipoResponseDTO {

    private Integer id;
    private String nome;
}