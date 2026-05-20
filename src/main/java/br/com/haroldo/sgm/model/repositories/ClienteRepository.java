package br.com.haroldo.sgm.model.repositories;

import br.com.haroldo.sgm.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c LEFT JOIN FETCH c.enderecos WHERE c.id = :id")
    Optional<Cliente> buscarComEnderecosById(@Param("id") Integer id);

}