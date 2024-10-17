package grupo.terabite.terabite.entity.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
@AllArgsConstructor
public class ProdutoEstoque {
    private String nome;

    private String marca;

    private Double valorUnitario;

    private Integer qtdEmEstoque;
}
