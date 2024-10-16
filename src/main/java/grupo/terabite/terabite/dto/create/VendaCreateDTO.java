package grupo.terabite.terabite.dto.create;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class VendaCreateDTO {

    @NotEmpty
    @Valid
    private List<VendaProdutoCreateDTO> produtos;
}
