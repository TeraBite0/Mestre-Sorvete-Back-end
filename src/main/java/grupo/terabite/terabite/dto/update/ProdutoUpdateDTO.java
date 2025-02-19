package grupo.terabite.terabite.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoUpdateDTO {

    @NotBlank
    private String nome;

    @NotNull
    private Double preco;

    @NotNull
    private Boolean isAtivo;

    @NotNull
    private Integer subtipoId;

    @NotNull
    private Integer marcaId;

    @NotNull
    private Boolean temLactose;

    @NotNull
    private Boolean temGlutem;
}
