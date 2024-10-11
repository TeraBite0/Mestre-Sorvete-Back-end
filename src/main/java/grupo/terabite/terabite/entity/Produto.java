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
    @JoinColumn(name = "FK_ID_TIPO", referencedColumnName = "ID_TIPO")
    private Subtipo subtipo;

    @ManyToOne
    @JoinColumn(name = "FK_ID_MARCA_PROD", referencedColumnName = "ID_MARCA")
    private Marca marca;

    @Column(name = "PRECO_PROD")
    private Double preco;

    @Column(name = "IS_ATIVO_PROD")
    private Boolean isAtivo = true;

    @Column(name = "EM_ESTOQUE_PROD")
    private Boolean emEstoque;
}