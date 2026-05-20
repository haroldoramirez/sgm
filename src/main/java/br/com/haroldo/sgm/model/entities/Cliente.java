package br.com.haroldo.sgm.model.entities;

import br.com.haroldo.sgm.rest.dtos.ClienteDTO;
import br.com.haroldo.sgm.rest.dtos.EnderecoDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default //Contornar problemas de objeto nulo por causa do Builder
    @JsonManagedReference
    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Endereco> enderecos = new ArrayList<>();

    @Column(name = "data_cadastro", updatable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataCadastro;

    @PrePersist
    public void prePersist() {
        setDataCadastro(LocalDateTime.now());
    }

    public void adicionarEndereco(Endereco endereco) {

        if (this.enderecos == null) {
            this.enderecos = new ArrayList<>();
        }

        endereco.setCliente(this);

        this.enderecos.add(endereco);

    }

    public ClienteDTO toDTO(Cliente cliente) {

        List<EnderecoDTO> enderecos = cliente.getEnderecos()
            .stream()
            .map(e -> new EnderecoDTO(
                e.getId(),
                e.getRua(),
                e.getNumero(),
                e.getCidade(),
                e.getUf(),
                e.getCep()
            ))
            .toList();

        ClienteDTO clienteDTO = new ClienteDTO();

        clienteDTO.setId(cliente.getId());
        clienteDTO.setNome(cliente.getNome());
        clienteDTO.setCpfCnpj(cliente.getCpfCnpj());
        clienteDTO.setEnderecos(enderecos);

        return clienteDTO;

    }

}