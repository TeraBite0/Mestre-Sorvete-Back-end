package grupo.terabite.terabite.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDTO {

    private Integer id;
    @NotBlank
    private String nome;

    @NotBlank
    private Double preco;

    @NotNull
    private Integer qtdEstoque;

    @NotBlank
    private Boolean isAtivo = true;

    @NotBlank
    private SubtipoResponseDTO subtipo;

    @NotBlank
    private MarcaResponseDTO marca;
}
