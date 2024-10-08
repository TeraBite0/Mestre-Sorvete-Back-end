package grupo.terabite.terabite.dto.autenticacao.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioTokenDto{

    private Integer userId;
    private String email;
    private String token;
}
