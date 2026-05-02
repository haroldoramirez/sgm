package br.com.haroldo.sgm.rest.dtos;

import br.com.haroldo.sgm.validator.cpfcnpj.CpfCnpj;
import jakarta.validation.constraints.NotBlank;

public class ClienteDTO {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public void setCpfCnpj(String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}