package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.EstoqueProdutoResponseDTO;
import grupo.terabite.terabite.entity.EstoqueProduto;

public class EstoqueProdutoMapper {

    public static EstoqueProdutoResponseDTO toResponseDTO(EstoqueProduto estoqueProduto){
        return EstoqueProdutoResponseDTO.builder()
                .codigo(estoqueProduto.getCodigo())
                .produto(estoqueProduto.getProduto())
                .qtdEstoque(estoqueProduto.getQtdEstoque())
                .marca(estoqueProduto.getMarca())
                .build();
    }
}
