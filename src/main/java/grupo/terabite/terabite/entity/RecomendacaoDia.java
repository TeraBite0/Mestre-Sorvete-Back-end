package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RECOMENDACAO_DIA")
public class RecomendacaoDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECO_DIA")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "FK_ID_PROD_RECO_DIA",referencedColumnName = "ID_PROD")
    private Produto produto;

    @Column(name = "DATA_RECO_DIA")
    private LocalDate dtRecomendacao;

    @Column(name = "TEXTO_RECO_DIA")
    private String texto;

    public RecomendacaoDia(Produto produto, LocalDate dtRecomendacao) {
        this.produto = produto;
        this.dtRecomendacao = dtRecomendacao;
    }
}
