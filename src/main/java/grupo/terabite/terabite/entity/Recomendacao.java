package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RECOMENDACAO_DIA")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECO")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "FK_ID_PROD_RECO",referencedColumnName = "ID_PROD")
    private Produto produto;


    public Recomendacao(Produto produto) {
        this.produto = produto;
    }
}
