package grupo.terabite.terabite.dto.internal;

import grupo.terabite.terabite.entity.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProdutoQuantidadeDTO {

    /* ESTA CLASSE É UTILIZADA PARA SELECT DE PRODUTO COM SOMA VENDIDA*/

    private Produto produto;

    private Long qtdVendida;
}
