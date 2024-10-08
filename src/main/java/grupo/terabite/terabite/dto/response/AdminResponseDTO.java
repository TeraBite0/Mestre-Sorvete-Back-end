package grupo.terabite.terabite.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdminResponseDTO {

    private Integer id;
    @NotBlank
    @Email
    @Schema(description = "Email do usuário")
    private String email;
    @NotBlank
    @Size(min = 8, max = 16)
    @Schema(description = "Senha do usuário")
    private String senha;
}
