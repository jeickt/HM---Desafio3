package br.com.southsystem.desafio_3.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import br.com.southsystem.desafio_3.model.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

	Optional<Product> findById(String id);

	void deleteById(String id);

}
