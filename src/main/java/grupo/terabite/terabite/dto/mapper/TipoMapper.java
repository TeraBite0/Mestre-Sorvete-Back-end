package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.TipoRequisitionDTO;
import grupo.terabite.terabite.dto.response.TipoResponseDTO;
import grupo.terabite.terabite.entity.Tipo;

public class TipoMapper {
    public static Tipo toCreateDto(TipoRequisitionDTO entity){
        if(entity == null) return null;

        return Tipo.builder()
                .nome(entity.getNome())
                .build();
    }

    public static TipoResponseDTO toResponseDto(Tipo entity){
        if(entity == null) return null;

        return TipoResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }
}
