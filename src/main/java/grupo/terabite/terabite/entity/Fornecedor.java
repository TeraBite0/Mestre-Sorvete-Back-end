package grupo.terabite.terabite.entity;

import jakarta.persistence.*;

@Entity
public class Fornecedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FORN")
    private Integer id;

    @Column(name = "NOME_FORN")
    private String nome;
}
