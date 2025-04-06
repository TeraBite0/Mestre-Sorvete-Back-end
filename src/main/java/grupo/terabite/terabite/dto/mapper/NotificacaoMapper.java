package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.*;
import grupo.terabite.terabite.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificacaoMapper {

    private final ProdutoMapper produtoMapper;

    public NotificacaoResponseDTO toResponseNotificacaoDto(Notificacao notificacao) {
        if (notificacao == null) return null;

        Produto produto = notificacao.getProduto();

        return NotificacaoResponseDTO.builder()
                .id(notificacao.getId())
                .email(notificacao.getEmail())
                .produtoResponseDTO(produtoMapper.toResponseDto(produto))
                .build();
    }
}
