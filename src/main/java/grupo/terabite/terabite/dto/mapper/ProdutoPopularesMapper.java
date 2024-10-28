package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.response.ProdutoPopularesReponseDto;
import grupo.terabite.terabite.entity.Produto;

public class ProdutoPopularesMapper {

    public static ProdutoPopularesReponseDto toDetalheProdutoPopularDto(Produto produto) {
        if (produto == null) return null;

        return ProdutoPopularesReponseDto.builder()
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .build();
    }
}
