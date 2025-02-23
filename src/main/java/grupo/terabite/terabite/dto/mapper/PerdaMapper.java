package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.PerdaCreateDTO;
import grupo.terabite.terabite.dto.response.PerdaResponseDTO;
import grupo.terabite.terabite.dto.update.PerdaUpdateDTO;
import grupo.terabite.terabite.entity.Perda;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.service.ProdutoService;

public class PerdaMapper {

    public static PerdaResponseDTO toResponsePerda(Perda perda) {
        if (perda == null) return null;

        Produto produto = perda.getProduto();

        return PerdaResponseDTO.builder()
                .id(perda.getId())
                .produto(ProdutoMapper.toResponseDto(produto))
                .qtdProduto(perda.getQtdProduto())
                .build();
    }

    public static Perda toEntity(PerdaCreateDTO perda, ProdutoService produtoService) {
        if (perda == null || produtoService == null) return null;

        return Perda.builder()
                .qtdProduto(perda.getQtdPerda())
                .produto(produtoService.buscarPorId(perda.getProdutoId()))
                .build();
    }

    public static Perda toUpdatePerdaDTO(PerdaUpdateDTO perda, ProdutoService produtoService) {
        if (perda == null || produtoService == null) return null;

        return Perda.builder()
                .qtdProduto(perda.getQtdPerda())
                .produto(produtoService.buscarPorId(perda.getProdutoId()))
                .build();
    }
}
