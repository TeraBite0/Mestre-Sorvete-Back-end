package grupo.terabite.terabite.dto.response;

import grupo.terabite.terabite.entity.Produto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class RecomendacaoResponseDTO {

    private Integer id;

    private ProdutoResponseDTO produto;

    private LocalDate dtRecomendacao;

    private String texto;
}