package grupo.terabite.terabite.entity;

import grupo.terabite.terabite.entity.enums.LoteStatusEnum;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@Entity
@Table(name = "LOTE")
public class Lote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "ID_LOTE")
    private Integer id;

    @Getter @Setter
    @ManyToOne
    @JoinColumn(name = "FK_ID_FORNECEDOR_LOTE", referencedColumnName = "ID_FORN")
    private Fornecedor fornecedor;

    @Getter @Setter
    @Column(name = "DATA_ENTREGA_LOTE")
    private LocalDate dtEntrega;

    @Getter @Setter
    @Column(name = "DATA_VENCIMENTO_LOTE")
    private LocalDate dtVencimento;

    @Getter @Setter
    @Column(name = "DATA_PEDIDO_LOTE")
    private LocalDate dtPedido;


    @Getter @Setter
    @Column(name = "VALOR_LOTE")
    private Double valorLote;

    private LoteStatusEnum statusEnum = LoteStatusEnum.AGUARDANDO_ENTREGA;

    @Getter @Setter
    @Column(name = "OBSERVACAO_LOTE")
    private String observacao;

    @Getter @Setter
    @OneToMany(mappedBy = "lote", fetch = FetchType.LAZY)
    private List<LoteProduto> loteProdutos;

    //Não criar getter, setter ou colocar no construtor, use o status (ENUM)
    @ManyToOne
    @JoinColumn(name = "FK_ID_STATUS_LOTE", referencedColumnName = "ID_LOTE_STATUS")
    private LoteStatus status;

    public Lote(Integer id, Fornecedor fornecedor, LocalDate dtEntrega, LocalDate dtVencimento, LocalDate dtPedido, Double valorLote, LoteStatusEnum status, String observacao, List<LoteProduto>loteProdutos) {
        this.id = id;
        this.fornecedor = fornecedor;
        this.dtEntrega = dtEntrega;
        this.dtVencimento = dtVencimento;
        this.dtPedido = dtPedido;
        this.valorLote = valorLote;
        setStatus(status);
        this.observacao = observacao;
        this.loteProdutos = loteProdutos;
    }

    public void setStatus(LoteStatusEnum statusEnum) {
        if(statusEnum == null) return;
        this.status = new LoteStatus(statusEnum.getId(), statusEnum.getStatus());
        this.statusEnum = statusEnum;
    }

    public LoteStatusEnum getStatus() {
        return statusEnum;
    }
}
