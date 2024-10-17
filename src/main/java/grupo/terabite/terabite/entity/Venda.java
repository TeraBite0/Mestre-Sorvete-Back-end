package grupo.terabite.terabite.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VENDAS")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_VEND")
    private Integer id;

    @Column(name = "DATA_COMPRA_VEND")
    private LocalDateTime dataCompra;

    public Venda(LocalDateTime dataCompra) {
        this.dataCompra = dataCompra;
    }

    @OneToMany(mappedBy = "venda", fetch = FetchType.LAZY)
    private List<VendaProduto> produtos;
}
