package grupo.terabite.terabite.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestaqueUpdateDTO {

    @NotNull
    private Integer produtoId;

    @NotBlank
    private String texto;
}
