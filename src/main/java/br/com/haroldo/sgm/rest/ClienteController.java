package br.com.haroldo.sgm.rest;

import br.com.haroldo.sgm.model.entities.Cliente;
import br.com.haroldo.sgm.model.entities.Endereco;
import br.com.haroldo.sgm.model.repositories.ClienteRepository;
import br.com.haroldo.sgm.rest.dtos.ClienteDTO;
import br.com.haroldo.sgm.rest.dtos.EnderecoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository respository) {
        this.repository = respository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid ClienteDTO clienteDTO) {

        //

        Cliente cliente = clienteDTO.toEntity();
        return repository.save(cliente);

    }

    @GetMapping("{id}")
    public ClienteDTO acharPorId(@PathVariable Integer id) {

        Cliente cliente = repository.buscarComEnderecosById(id)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return cliente.toDTO(cliente);

    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {

        repository
            .findById(id)
            .map( cliente -> {
                repository.delete(cliente);
                return Void.TYPE;
            }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid ClienteDTO dto) {

        repository.buscarComEnderecosById(id)
            .map(cliente -> {

                // Atualiza dados básicos
                cliente.setNome(dto.getNome());
                cliente.setCpfCnpj(dto.getCpfCnpj());

                // Merge de enderecos
                Map<Long, Endereco> existentes = cliente.getEnderecos()
                    .stream()
                    .collect(Collectors.toMap(Endereco::getId, e -> e));

                List<Endereco> atualizados = new ArrayList<>();

                if (dto.getEnderecos() != null) {

                    for (EnderecoDTO endDTO : dto.getEnderecos()) {

                        if (endDTO.getId() != null && existentes.containsKey(endDTO.getId())) {

                            // UPDATE
                            Endereco enderecoExistente = existentes.get(endDTO.getId());

                            enderecoExistente.setRua(endDTO.getRua());
                            enderecoExistente.setNumero(endDTO.getNumero());
                            enderecoExistente.setCidade(endDTO.getCidade());
                            enderecoExistente.setUf(endDTO.getUf());
                            enderecoExistente.setCep(endDTO.getCep());

                            atualizados.add(enderecoExistente);

                        } else {

                            // INSERT
                            Endereco novo = Endereco.builder()
                                .rua(endDTO.getRua())
                                .numero(endDTO.getNumero())
                                .cidade(endDTO.getCidade())
                                .uf(endDTO.getUf())
                                .cep(endDTO.getCep())
                                .build();

                            novo.setCliente(cliente);

                            atualizados.add(novo);

                        }
                    }
                }

                // REMOVE os que não vieram no DTO (orphanRemoval faz DELETE)
                cliente.getEnderecos().clear();
                cliente.getEnderecos().addAll(atualizados);

                return repository.save(cliente);

            })
            .orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
            );
    }

}