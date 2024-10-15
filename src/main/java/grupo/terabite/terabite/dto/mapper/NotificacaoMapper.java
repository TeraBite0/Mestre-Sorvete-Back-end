package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.NotificacaoCreateDTO;
import grupo.terabite.terabite.dto.response.*;
import grupo.terabite.terabite.entity.*;

public class NotificacaoMapper {

    public static NotificacaoResponseDTO toResponseNotificacaoDto(Notificacao notificacao) {
        if (notificacao == null) return null;

        Produto produto = notificacao.getProduto();
        Marca marca = produto.getMarca();
        Subtipo subtipo = produto.getSubtipo();
        Tipo tipo = subtipo.getTipoPai();

        return NotificacaoResponseDTO.builder()
                .id(notificacao.getId())
                .email(notificacao.getEmail())
                .produtoResponseDTO(ProdutoResponseDTO.builder()
                        .id(produto.getId())
                        .nome(produto.getNome())
                        .preco(produto.getPreco())
                        .isAtivo(produto.getIsAtivo())
                        .emEstoque(produto.getEmEstoque())
                        .marca(MarcaResponseDTO.builder()
                                .id(marca.getId())
                                .nome(marca.getNome())
                                .build())
                        .subtipo(SubtipoResponseDTO.builder()
                                .id(subtipo.getId())
                                .nome(subtipo.getNome())
                                .tipoPai(TipoResponseDTO.builder()
                                        .id(tipo.getId())
                                        .nome(tipo.getNome())
                                        .build())
                                .build())
                        .build())
                .build();
    }

    public static Notificacao toCreateNotificacaoDto(NotificacaoCreateDTO notificacaoCreateDTO) {
        if (notificacaoCreateDTO == null) return null;

        return Notificacao.builder()
                .email(notificacaoCreateDTO.getEmail())
                .build();
    }
}
