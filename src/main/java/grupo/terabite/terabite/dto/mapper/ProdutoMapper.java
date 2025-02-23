package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.create.ProdutoCreateDTO;
import grupo.terabite.terabite.dto.response.*;
import grupo.terabite.terabite.dto.update.ProdutoUpdateDTO;
import grupo.terabite.terabite.entity.*;
import grupo.terabite.terabite.service.MarcaService;
import grupo.terabite.terabite.service.SubtipoService;

public class ProdutoMapper {

    public static ProdutoResponseDTO toResponseDto(Produto produto) {
        if (produto == null) return null;

        Marca marca = produto.getMarca();
        Subtipo subtipo = produto.getSubtipo();
        Tipo tipo = subtipo.getTipoPai();

        return ProdutoResponseDTO.builder()
                .id(produto.getId())
                .nome(produto.getNome())
                .preco(produto.getPreco())
                .isAtivo(produto.getIsAtivo())
                .emEstoque(produto.getEmEstoque())
                .temGluten(produto.getTemGluten())
                .temLactose(produto.getTemLactose())
                .marca(MarcaResponseDTO.builder()
                        .id(marca.getId())
                        .nome(marca.getNome())
                        .build())
                .subtipo(SubtipoResponseDTO.builder()
                        .id(subtipo.getId())
                        .nome(subtipo.getNome())
                        .tipoPai(TipoResponseDTO.builder()
                                .id(tipo.getId())
                                .nome(tipo.getNome())
                                .build())
                        .build())
                .build();
    }

    public static Produto toCreateProduto(ProdutoCreateDTO entity) {
        if (entity == null) return null;

        return Produto.builder()
                .nome(entity.getNome())
                .preco(entity.getPreco())
                .temGluten(entity.getTemGluten())
                .temLactose(entity.getTemLactose())
                .build();
    }

    public static Produto toAtualizar(ProdutoUpdateDTO entity, SubtipoService subtipoService, MarcaService marcaService) {
        if (entity == null) return null;

        return Produto.builder()
                .nome(entity.getNome())
                .preco(entity.getPreco())
                .isAtivo(entity.getIsAtivo())
                .subtipo(subtipoService.buscarPorNomeSubtipo(entity.getNomeSubtipo()))
                .marca(marcaService.buscarPorNomeMarca(entity.getNomeMarca()))
                .temLactose(entity.getTemLactose())
                .temGluten(entity.getTemGluten())
                .build();
    }
}
