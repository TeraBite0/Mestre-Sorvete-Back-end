package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendaProdutoCreateDTO {

    @NotNull
    @Positive
    private Integer produtoId;

    @NotNull
    @Positive
    private Integer qtdVendida;
}
