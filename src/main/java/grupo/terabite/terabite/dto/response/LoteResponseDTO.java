package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class LoteResponseDTO {

    private Integer id;

    private String fornecedor;

    private LocalDate dtEntrega;

    private LocalDate dtVencimento;

    private LocalDate dtPedido;

    private Double valorLote;

    private String status;

    private String observacao;

    private List<LoteProdutoResponseDTO> loteProdutos;
}