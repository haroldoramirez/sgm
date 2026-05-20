package br.com.haroldo.sgm.model.repositories;

import br.com.haroldo.sgm.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Integer> {
}