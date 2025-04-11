package grupo.terabite.terabite.dto.mapper;

import grupo.terabite.terabite.dto.requisition.ProdutoRequisitionDTO;
import grupo.terabite.terabite.dto.response.ProdutoResponseDTO;
import grupo.terabite.terabite.entity.Marca;
import grupo.terabite.terabite.entity.Produto;
import grupo.terabite.terabite.entity.Subtipo;
import grupo.terabite.terabite.entity.Tipo;
import grupo.terabite.terabite.service.api.AwsBucketService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ProdutoMapper {

    private final AwsBucketService awsBucketService;

    public ProdutoResponseDTO toResponseDto(Produto produto) {
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
                .temGluten(produto.getTemGluten())
                .temLactose(produto.getTemLactose())
                .marca(marca.getNome())
                .subtipo(subtipo.getNome())
                .tipo(tipo.getNome())
                .imagemUrl(awsBucketService.imagemProduto(produto.getId(), produto.getTipoImagem()))
                .build();
    }

    public Produto toCreateProduto(ProdutoRequisitionDTO entity) {
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
