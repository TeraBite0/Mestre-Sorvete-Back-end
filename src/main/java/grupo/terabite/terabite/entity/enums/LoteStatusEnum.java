package grupo.terabite.terabite.entity.enums;

import lombok.Getter;

@Getter
public enum LoteStatusEnum {
    AGUARDANDO_ENTREGA(1, "Aguardando entrega"),
    ENTREGUE(2, "Entregue"),
    CANCELADO(3, "Cancelado"),
    ENTREGUE_COM_PENDENCIA(4, "Entrege com pendência"),
    CONCLUIDO_COM_PENDENCIA(5, "Concluído com pendência");

    private int id;
    private String status;

    LoteStatusEnum(int id, String status) {
        this.id = id;
        this.status = status;
    }
}
