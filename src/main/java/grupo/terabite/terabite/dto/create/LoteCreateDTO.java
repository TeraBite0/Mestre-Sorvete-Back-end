package grupo.terabite.terabite.dto.create;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoteCreateDTO {

    @NotNull
    private LocalDate dtCompra;

    @Future
    private LocalDate dtVencimento;

    @NotNull
    private LocalDate dtEntrega;

    @NotNull
    @Positive
    private Double valorLote;
}
