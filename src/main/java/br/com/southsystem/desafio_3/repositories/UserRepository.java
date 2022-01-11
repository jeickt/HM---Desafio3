package br.com.southsystem.desafio_3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.southsystem.desafio_3.model.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByLogin(String login);
}
