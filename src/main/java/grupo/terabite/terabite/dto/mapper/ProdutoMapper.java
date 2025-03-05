package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;

public class ProdutoMapper {

    public static ProdutoResponseDTO toResponseDto(Produto produto) {
        if (produto == null) return null;

        Marca marca = produto.getMarca();
        Subtipo subtipo = produto.getSubtipo();
        Tipo tipo = subtipo.getTipo();

        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .isAtivo(produto.getIsAtivo())
                .qtdCaixasEstoque(produto.getQtdCaixasEstoque())
                .qtdPorCaixas(produto.getQtdPorCaixas())
                .disponivel(produto.getDisponivel())
                //.emEstoque(produto.getEmEstoque())
                .temGluten(produto.getTemGluten())
                .temLactose(produto.getTemLactose())
                .marca(MarcaMapper.toResponseDto(produto.getMarca()))
                .subtipo(SubtipoMapper.toResponseDto(produto.getSubtipo()))
                .build();
    }

    public static Produto toCreateProduto(ProdutoCreateDTO entity) {
        if (entity == null) return null;

        return Produto.builder()
                .nome(entity.getNome())
                .preco(entity.getPreco())
                .temGluten(entity.getTemGluten())
                .temLactose(entity.getTemLactose())
                .qtdPorCaixas(entity.getQtdPorCaixas())
                .build();
    }
}
