package grupo.terabite.terabite.entity.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ProdutoVendido {
    //private Integer produtoId;

    private String nome;

    private Integer qtdVendido;
}
