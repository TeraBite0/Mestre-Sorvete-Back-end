package grupo.terabite.terabite.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProdutoResponseDTO {

    private Integer id;

    private String nome;

    private Double preco;

    private Integer qtdCaixasEstoque;

    private Integer qtdPorCaixas;

    private Boolean isAtivo;

    private Boolean disponivel;

    private Boolean temLactose;

    private Boolean temGluten;

    private SubtipoResponseDTO subtipo;

    private MarcaResponseDTO marca;

}