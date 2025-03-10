package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SAIDA_ESTOQUE")
public class SaidaEstoque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SAID")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_ID_PROD_SAID",referencedColumnName = "ID_PROD")
    private Produto produto;

    @Column(name = "DATA_SAID")
    private LocalDate dtSaida;

    @Column(name = "QTD_CAIXAS_SAID")
    private Integer qtdCaixasSaida;
}
