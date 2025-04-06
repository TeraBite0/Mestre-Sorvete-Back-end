package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.RecomendacaoRequisitionDTO;
import grupo.terabite.terabite.dto.response.RecomendacaoResponseDTO;
import grupo.terabite.terabite.entity.Recomendacao;
import grupo.terabite.terabite.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecomendacaoMapper {
    private final ProdutoMapper produtoMapper;

    public RecomendacaoResponseDTO toRecomendacaoResponseDto(Recomendacao entity){
        if (entity == null) return null;

        return RecomendacaoResponseDTO.builder()
                .id(entity.getId())
                .produto(produtoMapper.toResponseDto(entity.getProduto()))
                .build();
    }

//    public static Recomendacao toRecomendacao(RecomendacaoRequisitionDTO entity){
//        if (entity == null) return null;
//
//        return Recomendacao.builder()
//                .id(entity.getId())
//                .build();
//    }
}
