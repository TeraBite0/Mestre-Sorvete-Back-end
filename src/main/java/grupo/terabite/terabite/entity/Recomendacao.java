package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RECOMENDACAO")
public class Recomendacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_RECO")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_ID_PROD_RECO",referencedColumnName = "ID_PROD")
    private Produto produto;

    @Column(name = "DATA_RECO")
    private LocalDate dtRecomendacao;
}