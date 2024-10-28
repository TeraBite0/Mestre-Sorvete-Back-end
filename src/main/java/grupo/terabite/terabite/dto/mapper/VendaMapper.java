package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.VendaCreateDTO;
import grupo.terabite.terabite.dto.create.VendaProdutoCreateDTO;
import grupo.terabite.terabite.dto.response.VendaProdutoResponseDTO;
import grupo.terabite.terabite.entity.Venda;
import grupo.terabite.terabite.dto.response.VendaResponseDTO;
import grupo.terabite.terabite.entity.VendaProduto;
import grupo.terabite.terabite.service.ProdutoService;
import grupo.terabite.terabite.service.VendaService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class VendaMapper {
    public static VendaResponseDTO toResponseDTO(Venda v, VendaService vendaService) {
        if (v == null || vendaService == null) return null;

        List<VendaProdutoResponseDTO> vendaProdutosDTO = new ArrayList<>();
        List<VendaProduto> produtos = vendaService.buscarProdutosPorVenda(v.getId());

        for (VendaProduto vp : produtos) {
            vendaProdutosDTO.add(VendaProdutoResponseDTO.builder()
                    .produto(ProdutoMapper.toDetalhe(vp.getProduto()))
                    .qtdVendida(vp.getQtdProdutosVendido())
                    .build());
        }

        return VendaResponseDTO.builder()
                .id(v.getId())
                .dataCompra(v.getDataCompra())
                .produtos(vendaProdutosDTO)
                .build();
    }

    public static List<VendaProduto> toEntity(VendaCreateDTO venda, ProdutoService produtoService) {
        if (venda == null || produtoService == null) return null;

        List<VendaProduto> vendaProdutos = new ArrayList<>();

        for(VendaProdutoCreateDTO v :venda.getProdutos()){
            vendaProdutos.add(new VendaProduto(null, null, produtoService.buscarPorId(v.getProdutoId()), v.getQtdVendida()));
        }
        return vendaProdutos;
    }
}
