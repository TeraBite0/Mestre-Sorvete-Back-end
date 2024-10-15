package grupo.terabite.terabite.dto.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MarcaResponseDTO {

    private Integer id;

    private String nome;
}