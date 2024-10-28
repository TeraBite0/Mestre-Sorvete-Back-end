package grupo.terabite.terabite.dto.response;

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

    private ProdutoResponseDTO produto;
}