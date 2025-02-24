package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class DestaqueResponseDTO {

    private Integer id;

    private ProdutoResponseDTO produto;

    private LocalDate dtRecomendacao;

    private String texto;
}