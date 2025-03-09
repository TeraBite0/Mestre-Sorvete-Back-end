package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.LoteRequisitionDTO;
import grupo.terabite.terabite.dto.response.LoteResponseDTO;
import grupo.terabite.terabite.entity.Lote;
import grupo.terabite.terabite.service.ProdutoService;

public class LoteMapper {
    public static Lote toEntity(LoteRequisitionDTO lote, ProdutoService produtoService) {
        if (lote == null || produtoService == null) return null;
        return null;
//        return new Lote(null,
//                lote.getDtCompra(),
//                lote.getDtEntrega(),
//                lote.getDtVencimento(),
//                lote.getQtdProdutoComprado(),
//                lote.getValorLote(),
//                produtoService.buscarPorId(lote.getProdutoId())
//        );
    }

//    public static Lote toEntity(grupo.terabite.terabite.dto.requisition.LoteRequisitionDTO lote, ProdutoService produtoService) {
//        if (lote == null || produtoService == null) return null;
//        return null;
////        return new Lote(null,
////                lote.getDtCompra(),
////                lote.getDtEntrega(),
////                lote.getDtVencimento(),
////                //lote.getQtdProdutoComprado(),
////                lote.getValorLote()
////                //produtoService.buscarPorId(lote.getProdutoId())
////        );
//    }

    public static LoteResponseDTO toResponseDto(Lote lote) {
        if (lote == null) return null;

        return LoteResponseDTO.builder()
                .id(lote.getId())
                .dtPedido(lote.getDtPedido())
                .dtEntrega(lote.getDtEntrega())
                .dtVencimento(lote.getDtVencimento())
                //.qtdProdutoComprado(lote.getQtdCixasCompradas())
                .valorLote(lote.getValorLote())
//                .produto(ProdutoResponseDTO.builder()
//                        .id(lote.getProduto().getId())
//                        .nome(lote.getProduto().getNome())
//                        .marca(MarcaResponseDTO.builder()
//                                .id(lote.getProduto().getMarca().getId())
//                                .nome(lote.getProduto().getMarca().getNome())
//                                .build())
//                        .build())
                .build();
    }
}
