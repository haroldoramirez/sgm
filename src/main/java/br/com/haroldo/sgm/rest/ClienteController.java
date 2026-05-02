package br.com.haroldo.sgm.rest;

import br.com.haroldo.sgm.model.entities.Cliente;
import br.com.haroldo.sgm.model.repositories.ClienteRespository;
import br.com.haroldo.sgm.rest.dtos.ClienteDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteRespository respository;

    @Autowired
    public ClienteController(ClienteRespository respository) {
        this.respository = respository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente salvar(@RequestBody @Valid ClienteDTO clienteDTO) {

        Cliente cliente = Cliente.builder()
            .nome(clienteDTO.getNome())
            .cpfCnpj(clienteDTO.getCpfCnpj())
            .build();

        return respository.save(cliente);

    }

    @GetMapping("{id}")
    public Cliente acharPorId(@PathVariable Integer id) {
        return respository
            .findById(id)
            .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id) {
        respository
            .findById(id)
            .map( cliente -> {
                respository.delete(cliente);
                return Void.TYPE;
            }).orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable Integer id, @RequestBody @Valid ClienteDTO clienteAtualizadoDTO) {
        respository
            .findById(id)
            .map(cliente -> {

                // Atualiza os campos da entidade
                cliente.setNome(clienteAtualizadoDTO.getNome());
                cliente.setCpfCnpj(clienteAtualizadoDTO.getCpfCnpj());

                return respository.save(cliente);
            })
            .orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado")
            );
    }

}