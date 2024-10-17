package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDTO {

    private Integer id;

    private String nome;

    private Double preco;

    private Boolean isAtivo;

    private Boolean emEstoque;

    private SubtipoResponseDTO subtipo;

    private MarcaResponseDTO marca;
}