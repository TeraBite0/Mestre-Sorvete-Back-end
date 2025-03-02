package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Entity
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
