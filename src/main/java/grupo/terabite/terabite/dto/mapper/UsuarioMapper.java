package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.autenticacao.dto.UsuarioTokenDto;
import grupo.terabite.terabite.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioTokenDto of(Usuario usuario, String token) {
        if (usuario == null || token == null) return null;
        return UsuarioTokenDto.builder()
                .userId(usuario.getId())
                .email(usuario.getEmail())
                .token(token)
                .build();
    }
}
