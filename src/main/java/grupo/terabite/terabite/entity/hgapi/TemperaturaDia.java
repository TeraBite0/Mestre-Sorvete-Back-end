package grupo.terabite.terabite.entity.hgapi;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "TemperaturaDia")
public class TemperaturaDia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TDIA_TDIA")
    private Integer id;

    @Column(name = "DT_TEMPERATURA_TDIA")
    private LocalDate dtTemperatura;

    @Column(name = "TEMPERATURA_MEDIA_TDIA")
    private Double temperaturaMedia;
}
