package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoPopularesReponseDto {

    private String nome;
    private Double preco;
}
