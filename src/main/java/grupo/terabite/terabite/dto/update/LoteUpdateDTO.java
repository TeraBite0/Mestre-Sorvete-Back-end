package grupo.terabite.terabite.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoteUpdateDTO {

    @NotNull
    private Integer produtoId;

    @NotBlank
    private LocalDate dtCompra;

    @NotBlank
    private LocalDate dtVencimento;

    @NotBlank
    private LocalDate dtEntrega;

    @NotNull
    private Integer qtdProdutoComprado;

    @NotNull
    private Double valorLote;
}
