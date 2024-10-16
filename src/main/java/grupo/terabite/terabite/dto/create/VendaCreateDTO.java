package grupo.terabite.terabite.dto.create;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaCreateDTO {

    @NotEmpty
    @Valid
    private List<VendaProdutoCreateDTO> produtos;
}
