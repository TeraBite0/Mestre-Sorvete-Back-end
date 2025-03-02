package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class LoteRequisitionDTO {

    @NotBlank
    private String nomeFornecedor;

    @NotBlank
    private LocalDate dtEntrega;

    @NotBlank
    private LocalDate dtVencimento;

    @NotBlank
    private LocalDate dtCompra;

    @NotNull
    private Double valorLote;

    @NotBlank
    private String status;

    @NotEmpty
    private List<LoteProdutoRequisitionDTO> loteProdutos;
}
