package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubtipoResponseDTO {

    private Integer id;

    private TipoResponseDTO tipo;

    private String nome;
}