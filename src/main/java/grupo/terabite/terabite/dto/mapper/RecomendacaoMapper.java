package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.RecomendacaoDiaResponseDTO;
import grupo.terabite.terabite.dto.response.RecomendacaoResponseDTO;
import grupo.terabite.terabite.dto.update.RecomendacaoDiaUpdateDTO;
import grupo.terabite.terabite.dto.update.RecomendacaoUpdateDTO;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.entity.RecomendacaoDia;
import grupo.terabite.terabite.service.ProdutoService;

public class RecomendacaoMapper {

    public static RecomendacaoDiaResponseDTO toRecomendacaoDiaResponseDTO(RecomendacaoDia entity){
        if (entity == null) return null;

        return RecomendacaoDiaResponseDTO.builder()
                .texto(entity.getTexto())
                .produto(ProdutoMapper.toResponseDto(entity.getProduto()))
                .build();
    }

    public static RecomendacaoDia toRecomendacaoDia(RecomendacaoDiaUpdateDTO entity, ProdutoService produtoService) {
        if (entity == null) return null;

        return RecomendacaoDia.builder()
                .produto(produtoService.buscarPorId(entity.getProdutoId()))
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
