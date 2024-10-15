package grupo.terabite.terabite.dto.create;

import com.fasterxml.jackson.annotation.JsonProperty;
import grupo.terabite.terabite.entity.Produto;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoteCreateDTO {

    @JsonProperty("produtoId")
    @NotNull
    private Integer produtoId;

    @JsonProperty("dtCompra")
    @NotNull
    private LocalDate dtCompra;

    @JsonProperty("dtVencimento")
    @Future
    private LocalDate dtVencimento;

    @JsonProperty("dtEntrega")
    @NotNull
    private LocalDate dtEntrega;

    @NotNull
    @Positive
    private Integer qtdProdutoComprado;

    @JsonProperty("valorLote")
    @NotNull
    @Positive
    private Double valorLote;
}
