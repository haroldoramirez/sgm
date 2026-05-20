package br.com.haroldo.sgm.rest.dtos;

import br.com.haroldo.sgm.model.entities.Cliente;
import br.com.haroldo.sgm.model.entities.Endereco;
import br.com.haroldo.sgm.validator.cpfcnpj.CpfCnpj;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    @Valid
    private List<EnderecoDTO> enderecos;

    public Cliente toEntity() {

        Cliente cliente = Cliente.builder()
            .nome(this.nome)
            .cpfCnpj(this.cpfCnpj)
            .build();

        if (enderecos != null) {

            for (EnderecoDTO dto : enderecos) {

                Endereco endereco = Endereco.builder()
                    .rua(dto.getRua())
                    .numero(dto.getNumero())
                    .cidade(dto.getCidade())
                    .uf(dto.getUf())
                    .cep(dto.getCep())
                    .build();

                cliente.adicionarEndereco(endereco);

            }

        }

        return cliente;

    }

}