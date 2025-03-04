package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.FornecedorCreateDTO;
import grupo.terabite.terabite.dto.response.FornecedorResponseDTO;
import grupo.terabite.terabite.entity.Fornecedor;

import java.util.ArrayList;
import java.util.List;

public class FornecedorMapper {

    public static FornecedorResponseDTO toResponseDto(Fornecedor fornecedor) {
        return FornecedorResponseDTO.builder()
            .id(fornecedor.getId())
            .nome(fornecedor.getNome())
            .build();
    }

    public static List<FornecedorResponseDTO> toListResposeDto(List<Fornecedor> fornecedores){
        if(fornecedores == null){
            return null;
        }

        List<FornecedorResponseDTO> fornecedorDto = new ArrayList<>();

        for(Fornecedor fornecedor: fornecedores){
            if(fornecedor != null){
                fornecedorDto.add(FornecedorResponseDTO.builder()
                        .id(fornecedor.getId())
                        .nome(fornecedor.getNome())
                        .build());
            }
        }

        return fornecedorDto;
    }

    public static Fornecedor toCreateDto(FornecedorCreateDTO fornecedorCreateDTO) {
        return Fornecedor.builder()
            .nome(fornecedorCreateDTO.getNome())
            .build();
    }
}
