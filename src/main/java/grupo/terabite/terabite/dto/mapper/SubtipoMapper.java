package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.SubtipoRequisitionDTO;
import grupo.terabite.terabite.dto.response.SubtipoResponseDTO;
import grupo.terabite.terabite.entity.Subtipo;

public class SubtipoMapper {
    public static Subtipo toCreateDto(SubtipoRequisitionDTO entity){
        if(entity == null) return null;

        return Subtipo.builder()
                .nome(entity.getNome())
                .build();
    }

    public static SubtipoResponseDTO toResponseDto(Subtipo entity){
        if(entity == null) return null;

        return SubtipoResponseDTO.builder()
                .id(entity.getId())
                .tipo(TipoMapper.toResponseDto(entity.getTipo()))
                .nome(entity.getNome())
                .build();
    }
}
