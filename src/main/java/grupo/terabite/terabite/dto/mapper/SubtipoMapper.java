package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.SubtipoCreateDTO;
import grupo.terabite.terabite.dto.response.SubtipoResponseDTO;
import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;

public class SubtipoMapper {
    public static Subtipo toCreateDto(SubtipoCreateDTO entity){
        if(entity == null) return null;

        return Subtipo.builder()
                .nome(entity.getNome())
                .tipo(Tipo.builder().nome(entity.getNome()).build())
                .build();
    }

    public static SubtipoResponseDTO toResponseDto(Subtipo entity){
        if(entity == null) return null;

        return SubtipoResponseDTO.builder()
                .tipo(TipoMapper.toResponseDto(entity.getTipo()))
                .nome(entity.getNome())
                .build();
    }
}
