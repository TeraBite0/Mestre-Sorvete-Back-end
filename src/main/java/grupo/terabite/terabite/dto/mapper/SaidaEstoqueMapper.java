package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.SaidaEstoqueRequisitionDTO;
import grupo.terabite.terabite.dto.requisition.SaidaEstoqueRequisitionGroupDTO;
import grupo.terabite.terabite.dto.response.SaidaEstoqueResponseDTO;
import grupo.terabite.terabite.dto.response.SaidaEstoqueResponseGroupDTO;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.SaidaEstoque;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SaidaEstoqueMapper {

    private final ProdutoMapper produtoMapper;

    public static List<SaidaEstoque> toEntityList(SaidaEstoqueRequisitionGroupDTO saidaEstoqueRequisitionGroupDTO) {
        if (saidaEstoqueRequisitionGroupDTO == null) return null;

        List<SaidaEstoque> saidaEstoques = new ArrayList<>();
        saidaEstoqueRequisitionGroupDTO.getSaidaEstoques().forEach(se ->
                saidaEstoques.add(
                        new SaidaEstoque(se.getId(),
                                Produto.builder().id(se.getProdutoId()).build(),
                                saidaEstoqueRequisitionGroupDTO.getDtSaida(),
                                se.getQtdCaixasSaida()
                        )
                )
        );

        return saidaEstoques;
    }

    public List<SaidaEstoqueResponseGroupDTO> toSaidaEstoqueResponseGroupDTO(List<SaidaEstoque> saidaEstoques) {
        if (saidaEstoques == null) return null;
        List<SaidaEstoqueResponseGroupDTO> responseGroupDTOList = new ArrayList<>();
        Map<LocalDate, List<SaidaEstoque>> saidaEstoqueMap = saidaEstoques.stream().collect(Collectors.groupingBy(SaidaEstoque::getDtSaida));
        for (LocalDate dtSaida : saidaEstoqueMap.keySet()) {
            responseGroupDTOList.add(SaidaEstoqueResponseGroupDTO.builder()
                    .dtSaida(dtSaida)
                    .saidaEstoques(toSaidaEstoqueResponseDTOS(saidaEstoqueMap.get(dtSaida)))
                    .build());
        }

        return responseGroupDTOList;
    }

    public SaidaEstoqueResponseDTO toSaidaEstoqueResponseDTO(SaidaEstoque saidaEstoque){
        if (saidaEstoque == null) return null;

        return new SaidaEstoqueResponseDTO(saidaEstoque.getId(), produtoMapper.toResponseDto(saidaEstoque.getProduto()), saidaEstoque.getQtdCaixasSaida());
    }

    public static SaidaEstoque toSaidaEstoque(SaidaEstoqueRequisitionDTO saidaEstoqueRequisitionDTO) {
        if (saidaEstoqueRequisitionDTO == null) return null;

        return new SaidaEstoque(null,
                Produto.builder().id(saidaEstoqueRequisitionDTO.getProdutoId()).build(),
                null,
                saidaEstoqueRequisitionDTO.getQtdCaixasSaida());
    }

    private List<SaidaEstoqueResponseDTO> toSaidaEstoqueResponseDTOS(List<SaidaEstoque> saidaEstoques) {
        if (saidaEstoques == null) return null;

        List<SaidaEstoqueResponseDTO> saidaEstoqueResponseDTOS = new ArrayList<>();

        saidaEstoques.forEach(
                se -> saidaEstoqueResponseDTOS.add(
                        new SaidaEstoqueResponseDTO(se.getId() ,produtoMapper.toResponseDto(se.getProduto()), se.getQtdCaixasSaida())
                )
        );

        return saidaEstoqueResponseDTOS;
    }
}
