package grupo.terabite.terabite.dto.requisition;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    private LocalDate dtPedido;

    @NotNull
    private Double valorLote;

    @NotBlank
    private String status;

    private String observacao;

    @NotEmpty @Size(max = 15, message = "A lista pode ter no máximo 15 itens.")
    private List<LoteProdutoRequisitionDTO> loteProdutos;
}
