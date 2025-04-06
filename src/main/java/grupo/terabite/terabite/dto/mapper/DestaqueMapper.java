package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.DestaqueResponseDTO;
import grupo.terabite.terabite.dto.requisition.DestaqueRequisitionDTO;
import grupo.terabite.terabite.entity.Destaque;
import grupo.terabite.terabite.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DestaqueMapper {

    private final ProdutoMapper produtoMapper;

    public DestaqueResponseDTO toDestaqueResponseDTO(Destaque entity){
        if (entity == null) return null;

        return DestaqueResponseDTO.builder()
                .texto(entity.getTexto())
                .produto(produtoMapper.toResponseDto(entity.getProduto()))
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
