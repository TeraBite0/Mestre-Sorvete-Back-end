package grupo.terabite.terabite.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VendaResponseDTO {

    private Integer id;

    private LocalDateTime dataCompra;

    private List<VendaProdutoResponseDTO> produtos;
}