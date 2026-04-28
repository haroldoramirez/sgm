package br.com.haroldo.sgm.model.repository;

import br.com.haroldo.sgm.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Integer> {
}