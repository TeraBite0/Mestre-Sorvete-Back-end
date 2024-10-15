package grupo.terabite.terabite.dto.response;

import grupo.terabite.terabite.entity.Tipo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SubtipoResponseDTO {

    private Integer id;

    private TipoResponseDTO tipoPai;

    private String nome;
}