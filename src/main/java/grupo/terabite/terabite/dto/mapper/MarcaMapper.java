package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.MarcaResponseDTO;
import grupo.terabite.terabite.entity.Marca;

import java.util.ArrayList;
import java.util.List;

public class MarcaMapper {

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
}
