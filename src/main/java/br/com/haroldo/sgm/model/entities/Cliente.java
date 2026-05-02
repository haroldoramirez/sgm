package br.com.haroldo.sgm.model.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false, length = 14, unique = true)
    private String cpfCnpj;

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        setDataCadastro(LocalDateTime.now());
        normalizarDocumento();
    }

    @PreUpdate
    public void preUpdate() {
        normalizarDocumento();
    }

    private void normalizarDocumento() {
        if (cpfCnpj != null) {
            cpfCnpj = cpfCnpj.replaceAll("\\D", "");
        }
    }

}