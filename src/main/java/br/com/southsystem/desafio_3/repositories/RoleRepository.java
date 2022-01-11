package br.com.southsystem.desafio_3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.desafio_3.model.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
