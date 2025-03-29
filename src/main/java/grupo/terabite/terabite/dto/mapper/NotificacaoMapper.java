package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.NotificacaoRequisitionDTO;
import grupo.terabite.terabite.dto.response.*;
import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.service.ProdutoService;

public class NotificacaoMapper {

    public static NotificacaoResponseDTO toResponseNotificacaoDto(Notificacao notificacao) {
        if (notificacao == null) return null;

        Produto produto = notificacao.getProduto();

        return NotificacaoResponseDTO.builder()
                .id(notificacao.getId())
                .email(notificacao.getEmail())
                .produtoResponseDTO(ProdutoMapper.toResponseDto(produto))
                .build();
    }
}
