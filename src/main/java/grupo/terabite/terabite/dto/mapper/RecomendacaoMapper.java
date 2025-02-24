package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.DestaqueResponseDTO;
import grupo.terabite.terabite.dto.response.RecomendacaoResponseDTO;
import grupo.terabite.terabite.dto.update.DestaqueUpdateDTO;
import grupo.terabite.terabite.dto.update.RecomendacaoUpdateDTO;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.service.ProdutoService;

public class RecomendacaoMapper {

    public static DestaqueResponseDTO toDestaqueResponseDTO(Destaque entity){
        if (entity == null) return null;

        return DestaqueResponseDTO.builder()
                .texto(entity.getTexto())
                .produto(ProdutoMapper.toResponseDto(entity.getProduto()))
                .build();
    }

    public static Destaque toDestaque(DestaqueUpdateDTO entity, ProdutoService produtoService) {
        if (entity == null) return null;

        return Destaque.builder()
                .produto(produtoService.buscarPorId(entity.getProdutoId()))
                .texto(entity.getTexto())
                .build();
    }

    public static RecomendacaoResponseDTO toRecomendacaoResponseDto(Recomendacao entity){
        if (entity == null) return null;

        return RecomendacaoResponseDTO.builder()
                .id(entity.getId())
                .produto(ProdutoMapper.toResponseDto(entity.getProduto()))
                .build();
    }

    public static Recomendacao toRecomendacao(RecomendacaoUpdateDTO entity, ProdutoService produtoService){
        if (entity == null) return null;

        return Recomendacao.builder()
                .produto(produtoService.buscarPorId(entity.getProdutoId()))
                .build();
    }
}
