package grupo.terabite.terabite.dto.update;

import com.fasterxml.jackson.annotation.JsonProperty;
import grupo.terabite.terabite.entity.Produto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LoteUpdateDTO {

    @JsonProperty("produtoId")
    private Integer produtoId;

    @JsonProperty("dtCompra")
    private LocalDate dtCompra;

    @JsonProperty("dtVencimento")
    private LocalDate dtVencimento;

    @JsonProperty("dtEntrega")
    private LocalDate dtEntrega;

    private Integer qtdProdutoComprado;

    @JsonProperty("valorLote")
    private Double valorLote;
}
