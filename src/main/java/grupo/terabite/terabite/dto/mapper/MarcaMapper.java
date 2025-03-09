package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.MarcaRequisitionDTO;
import grupo.terabite.terabite.dto.response.MarcaResponseDTO;
import grupo.terabite.terabite.entity.Marca;

import java.util.ArrayList;
import java.util.List;

public class MarcaMapper {

    public static MarcaResponseDTO toResponseDto(Marca entity){
        if(entity == null) return null;

        return MarcaResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .build();
    }

    public static List<MarcaResponseDTO> toListResposeDto(List<Marca> marcas){
        if(marcas == null){
            return null;
        }

        List<MarcaResponseDTO> marcasDto = new ArrayList<>();

        for(Marca marca: marcas){
            if(marca != null){
                marcasDto.add(MarcaResponseDTO.builder()
                                .id(marca.getId())
                                .nome(marca.getNome())
                        .build());
            }
        }

        return marcasDto;
    }

    public static Marca toCreateMarca(MarcaRequisitionDTO entity){
        if(entity == null) return null;

        return Marca.builder()
                .nome(entity.getNome())
                .build();
    }
}
