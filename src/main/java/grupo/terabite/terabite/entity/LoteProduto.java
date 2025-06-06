package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@Entity
@Table(name = "LOTE_PRODUTO")
public class LoteProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LP")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_ID_LOTE_LP", referencedColumnName = "ID_LOTE")
    private Lote lote;

    @ManyToOne
    @JoinColumn(name = "FK_ID_PROD_LP", referencedColumnName = "ID_PROD")
    private Produto produto;

    @Column(name = "QTD_CAIXAS_COMPRADAS_LP")
    private Integer qtdCaixasCompradas;
}
