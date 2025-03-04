package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoCreateDTO {

    @NotBlank
    private String nome;

    @NotBlank
    private String nomeSubtipo;

    @NotBlank
    private String nomeMarca;

    @NotNull
    private Double preco;

    @NotNull
    private Boolean temLactose;

    @NotNull
    private Boolean temGluten;
}
