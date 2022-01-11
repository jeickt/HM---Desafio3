package br.com.southsystem.desafio_3.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.southsystem.desafio_3.model.entities.Product;
import br.com.southsystem.desafio_3.model.entities.dto.ProductDTO;
import br.com.southsystem.desafio_3.rabbitmq.RabbitMQConnection;
import br.com.southsystem.desafio_3.services.ProductService;

@RestController
@RequestMapping(value = "/products")
public class ProductResource {

	private final ProductService service;

	public ProductResource(ProductService service) {
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<List<ProductDTO>> findAll(
			@RequestParam(value = "categoryId", defaultValue = "0") Long categoryId) {
		List<ProductDTO> products;
		if (categoryId != 0) {
			products = service.findByCategoryId(categoryId);
		} else {
			products = service.findAll();
		}
		return ResponseEntity.ok().body(products);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable String id) {
		ProductDTO productDTO = service.findById(id);
		return ResponseEntity.ok().body(productDTO);
	}

	@PostMapping
	public ResponseEntity<Product> insert(@Valid @RequestBody Product product) {
		product = service.insert(product);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(product.getId())
				.toUri();
		return ResponseEntity.created(uri).body(product);
	}

	@PostMapping(value = "/file")
	public ResponseEntity<Void> insertFile(@RequestBody byte[] csvFile, @RequestParam("fileName") String fileName) {
		service.insertFile(csvFile);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Product> update(@PathVariable String id, @Valid @RequestBody Product product) {
		product = service.update(id, product);
		return ResponseEntity.ok().body(product);
	}

	@PutMapping(value = "/quantity")
	public ResponseEntity<Product> updateQuantity(@RequestParam(value = "id", defaultValue = "0") String id,
			@RequestParam(value = "quantity", defaultValue = "0") Integer quantity) {
		Product product = service.updateQuantity(id, quantity);
		service.sendMessage(RabbitMQConnection.QUEUE_QUANTITY, product);
		return ResponseEntity.ok().body(product);
	}

	@DeleteMapping(value = "/{id}")
	@Secured({ "ROLE_ADMIN" })
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
