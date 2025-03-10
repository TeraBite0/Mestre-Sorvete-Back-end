package grupo.terabite.terabite.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaidaEstoqueResponseDTO {

    private ProdutoResponseDTO produto;

    private Integer qtdCaixasSaida;
}
