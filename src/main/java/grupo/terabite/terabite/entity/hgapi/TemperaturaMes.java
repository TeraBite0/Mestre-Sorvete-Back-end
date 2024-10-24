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
@Table(name = "TemperaturaMes")
public class TemperaturaMes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TMES")
    private Integer id;

    @Column(name = "DT_TEMPERATURA_TMES")
    private LocalDate dtMes;

    @Column(name = "TEMPERATURA_MEDIA_TMES")
    private Double temperaturaMedia;
}
