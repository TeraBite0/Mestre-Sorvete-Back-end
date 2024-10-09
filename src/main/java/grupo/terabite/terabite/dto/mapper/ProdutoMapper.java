package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.response.MarcaResponseDTO;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.dto.response.TipoResponseDTO;
import grupo.terabite.terabite.dto.update.ProdutoUpdateDTO;
import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Subtipo;

public class ProdutoMapper {

    public static ProdutoResponseDTO toDetalhe(Produto produto){
        if(produto == null) return null;

        Marca marca = produto.getMarca();
        Subtipo subtipo = produto.getSubtipo();

        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .isAtivo(produto.getIsAtivo())
                .marca(MarcaResponseDTO.builder()
                        .id(marca.getId())
                        .nome(marca.getNome())
                        .build())
                .tipo(TipoResponseDTO.builder()
                        .id(subtipo.getId())
                        .nome(subtipo.getNome())
                        .build())
                .build();
    }

    public static Produto toCriarProduto(ProdutoCreateDTO entity){
        if(entity == null) return null;

        return Produto.builder()
                .nome(entity.getNome())
                .preco(entity.getPreco())
                .isAtivo(entity.getIsAtivo())
                .build();
    }

    public static Produto toAtualizar(ProdutoUpdateDTO entity){
        if(entity == null) return null;

        return Produto.builder()
                .nome(entity.getNome())
                .preco(entity.getPreco())
                .isAtivo(entity.getIsAtivo())
                .build();
    }
}
