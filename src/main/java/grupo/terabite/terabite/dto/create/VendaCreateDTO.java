package grupo.terabite.terabite.dto.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
