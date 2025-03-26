package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "DESTAQUE")
public class Destaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEST")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "FK_ID_PROD_DEST",referencedColumnName = "ID_PROD")
    private Produto produto;

    @Column(name = "DATA_DEST")
    private LocalDate dtRecomendacao;

    @Column(name = "TEXTO_DEST", length = 1000)
    private String texto;

    public Destaque(Produto produto, LocalDate dtRecomendacao) {
        this.produto = produto;
        this.dtRecomendacao = dtRecomendacao;
    }
}
