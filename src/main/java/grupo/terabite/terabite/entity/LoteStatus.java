package grupo.terabite.terabite.entity;

import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

// ENTIDADE PARA CONSTRUÇÃO DO BD, NÃO USAR

@AllArgsConstructor
@Entity
@Table(name = "LOTE_STATUS")
public class LoteStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LOTE_STATUS")
    private Integer id;

    @Column(name = "NOME_STATUS", unique = true, nullable = false)
    private String status;
}
