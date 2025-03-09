package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.DestaqueResponseDTO;
import grupo.terabite.terabite.dto.requisition.DestaqueRequisitionDTO;
import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.service.ProdutoService;

public class DestaqueMapper {

    public static DestaqueResponseDTO toDestaqueResponseDTO(Destaque entity){
        if (entity == null) return null;

        return DestaqueResponseDTO.builder()
                .texto(entity.getTexto())
                .produto(ProdutoMapper.toResponseDto(entity.getProduto()))
                .build();
    }

    public static Destaque toDestaque(DestaqueRequisitionDTO entity, ProdutoService produtoService) {
        if (entity == null) return null;

        return Destaque.builder()
                .produto(produtoService.buscarPorId(entity.getProdutoId()))
                .texto(entity.getTexto())
                .build();
    }
}
