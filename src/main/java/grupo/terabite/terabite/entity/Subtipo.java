package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "SUBTIPO")
public class Subtipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SUBT")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_ID_TIPO_PAI", referencedColumnName = "ID_TIPO")
    private Tipo tipoPai;

    @Column(name = "NOME_SUBT")
    private String nome;
}
