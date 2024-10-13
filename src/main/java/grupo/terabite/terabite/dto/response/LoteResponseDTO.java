package grupo.terabite.terabite.dto.response;

import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoteResponseDTO {

    private Integer id;
    private LocalDate dtPedido;
    private LocalDate dtEntrega;
    private LocalDate dtVencimento;
    private Integer qtdProdutoComprado;
    private Double valorLote;
    private Produto produto;
}
