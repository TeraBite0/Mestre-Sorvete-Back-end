package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.LoteProdutoRequisitionDTO;
import grupo.terabite.terabite.dto.requisition.LoteRequisitionDTO;
import grupo.terabite.terabite.dto.requisition.LoteStatusRequisitionDTO;
import grupo.terabite.terabite.dto.response.LoteProdutoResponseDTO;
import grupo.terabite.terabite.dto.response.LoteResponseDTO;
import grupo.terabite.terabite.entity.Fornecedor;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.entity.LoteProduto;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoteMapper {

    private final ProdutoMapper produtoMapper;

    public static Lote toEntity(LoteRequisitionDTO loteRequisitionDTO) {
        if (loteRequisitionDTO == null) return null;

        Lote lote = new Lote(null,
                new Fornecedor(null, loteRequisitionDTO.getNomeFornecedor()),
                loteRequisitionDTO.getDtEntrega(),
                loteRequisitionDTO.getDtVencimento(),
                (loteRequisitionDTO.getDtPedido() != null ? loteRequisitionDTO.getDtPedido() : null), // valida se foi preenchida, caso contrário preenche null
                loteRequisitionDTO.getValorLote(),
                null,
                null,
                null);

        lote.setLoteProdutos(toLoteProdutosList(loteRequisitionDTO.getLoteProdutos(), lote));
        return lote;
    }

    public LoteResponseDTO toResponseDto(Lote lote) {
        if (lote == null) return null;

        return LoteResponseDTO.builder()
                .id(lote.getId())
                .fornecedor(lote.getFornecedor().getNome())
                .dtEntrega(lote.getDtEntrega())
                .dtVencimento(lote.getDtVencimento())
                .dtPedido(lote.getDtPedido())
                .valorLote(lote.getValorLote())
                .status(lote.getStatus().getStatus())
                .observacao(lote.getObservacao())
                .loteProdutos(loteProdutoResponseDTOSList(lote.getLoteProdutos()))
                .build();
    }

    private List<LoteProdutoResponseDTO> loteProdutoResponseDTOSList(List<LoteProduto> loteProdutos) {
        if (loteProdutos == null) return null;

        List<LoteProdutoResponseDTO> loteProdutosResponseDTOS = new ArrayList<>();
        loteProdutos.forEach(lp ->
                loteProdutosResponseDTOS.add(LoteProdutoResponseDTO.builder()
                        .produto(produtoMapper.toResponseDto(lp.getProduto()))
                        .qtdCaixasCompradas(lp.getQtdCaixasCompradas())
                        .build())
        );
        return loteProdutosResponseDTOS;
    }

    private static List<LoteProduto> toLoteProdutosList(List<LoteProdutoRequisitionDTO> loteProdutoRequisitionDTOS, Lote lote) {
        if (loteProdutoRequisitionDTOS == null) return null;

        List<LoteProduto> loteProdutos = new ArrayList<>();
        loteProdutoRequisitionDTOS.forEach(lp ->
                loteProdutos.add(new LoteProduto(null, lote, Produto.builder().id(lp.getProdutoId()).build(), lp.getQtdCaixasCompradas()))
        );
        return loteProdutos;
    }

    public static Lote toRequisitionUpdateStatusDTO(LoteStatusRequisitionDTO loteStatusRequisitionDTO) {
        if (loteStatusRequisitionDTO == null) return null;
        return new Lote(null, null, null, null, null, null, LoteStatusEnum.valueOf(loteStatusRequisitionDTO.getStatus()), loteStatusRequisitionDTO.getObservacao(), null);
    }
}
