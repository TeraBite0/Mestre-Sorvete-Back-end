package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PRODUTO")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PROD")
    private Integer id;

    @Column(name = "NOME_PROD")
    private String nome;

    @ManyToOne
    @JoinColumn(name = "FK_ID_SUBT_PROD", referencedColumnName = "ID_SUBT")
    private Subtipo subtipo;

    @ManyToOne
    @JoinColumn(name = "FK_ID_MARCA_PROD", referencedColumnName = "ID_MARCA")
    private Marca marca;

    @Column(name = "PRECO_PROD")
    private Double preco;

    @Column(name = "IS_ATIVO_PROD")
    private Boolean isAtivo = true;


    @Column(name = "QTD_CAIXA_ESTOQUE_PROD")
    private Integer qtdCaixasEstoque;

    @Column(name = "DISPONIVEL_PROD")
    private Boolean disponivel;

    @Column(name = "TEM_LACTOSE_PROD")
    private Boolean temLactose = false;

    @Column(name = "TEM_GLUTEN_PROD")
    private Boolean temGluten = false;
}