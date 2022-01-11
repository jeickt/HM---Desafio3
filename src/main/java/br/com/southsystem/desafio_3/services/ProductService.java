package br.com.southsystem.desafio_3.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.stereotype.Service;

import br.com.southsystem.desafio_3.model.entities.Category;
import br.com.southsystem.desafio_3.model.entities.Product;
import br.com.southsystem.desafio_3.model.entities.dto.ProductDTO;
import br.com.southsystem.desafio_3.repositories.ProductRepository;
import br.com.southsystem.desafio_3.services.exceptions.FileReaderException;
import br.com.southsystem.desafio_3.services.exceptions.NegativeValueException;
import br.com.southsystem.desafio_3.services.exceptions.ResourceNotFoundException;

@Service
public class ProductService {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	private final ProductRepository repo;

	private final CategoryService categoryService;

	public ProductService(ProductRepository repository, CategoryService categoryService) {
		this.repo = repository;
		this.categoryService = categoryService;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	public List<ProductDTO> findAll() {
		return repo.findAll().stream().map(x -> transformProductToProductDTO(x)).collect(Collectors.toList());
	}

	public ProductDTO findById(String id) {
		Optional<Product> product = repo.findById(id);
		if (product.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Product prod = product.get();
		return transformProductToProductDTO(prod);
	}

	public List<ProductDTO> findByCategoryId(Long categoryId) {
		return repo.findByCategoryId(categoryId).stream().map(x -> transformProductToProductDTO(x))
				.collect(Collectors.toList());
	}

	public Product insert(Product product) {
		return repo.save(product);
	}

	public void insertFile(byte[] csvFile) {
		Scanner sc = null;
		List<Product> products = new ArrayList<>();
		List<Category> categories = new ArrayList<>();
		try {
			File file = File.createTempFile("csvFile", ".tmp");
			FileOutputStream in = new FileOutputStream(file);
			in.write(csvFile);
			in.close();

			sc = new Scanner(file);
			while (sc.hasNextLine()) {
				String line = sc.nextLine();
				if ("codigo".equals(line.split(",")[1].substring(0, 6))) {
					continue;
				}
				List<String> content = getCleanArrayOfFileWithProducts(line);

				try {
					@Valid
					Product newProduct = new Product(content.get(0), content.get(1), content.get(2), content.get(3),
							content.get(4), Double.parseDouble(content.get(6)), Double.parseDouble(content.get(7)),
							sdf.parse(content.get(8)), sdf.parse(content.get(9)), content.get(10), content.get(11), 0);

					products.add(newProduct);
					categories.add(new Category(null, content.get(5)));
				} catch (MethodArgumentNotValidException e) {
					System.out.println("Produto não adicionado:" + e.getMessage());
				}
			}

			for (Product product : products) {
				insert(product);
			}

			for (Category category : categories) {
				categoryService.insert(category);
			}

			for (int i = 0; i < products.size(); i++) {
				products.get(i).setCategory(categories.get(i));
				insert(products.get(i));
			}
		} catch (ParseException | IllegalStateException | IOException e) {
			throw new FileReaderException();
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
	}
	
	private List<String> getCleanArrayOfFileWithProducts(String line) {
		String[] parts = line.split("\"");
		List<String> strings = new ArrayList<>();
		for (int i = 0; i < parts.length; i++) {
			if (i % 2 == 0) {
				String[] cels = parts[i].split(",");
				for (String cel : cels) {
					if (!cel.isEmpty()) {
						strings.add(cel);
					}
				}
			} else {
				strings.add(parts[i]);
			}
		}
		strings.set(6, strings.get(6).replace(',', '.'));
		strings.set(7, strings.get(7).replace(',', '.'));
		strings = strings.stream().map(x -> x.equals("n/a") ? null : x).collect(Collectors.toList());
		
		return strings;
	}

	public Product update(String id, Product product) {
		Optional<Product> p = repo.findById(id);
		if (p.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Product prod = p.get();
		updateData(prod, product);
		return repo.save(prod);
	}

	public Product updateQuantity(String id, Integer quantity) {
		validateQuantity(quantity);
		Optional<Product> p = repo.findById(id);
		if (p.isEmpty()) {
			throw new ResourceNotFoundException(id);
		}
		Product prod = p.get();
		prod.setQuantity(quantity);
		return repo.save(prod);
	}

	private void updateData(Product prod, Product product) {
		prod.setId(product.getId());
		prod.setBarCode(product.getBarCode());
		prod.setSeries(product.getSeries());
		prod.setName(product.getName());
		prod.setDescription(product.getDescription());
		prod.setGrossValue(product.getGrossValue());
		prod.setTaxes(product.getTaxes());
		prod.setManufacturingDate(product.getManufacturingDate());
		prod.setExpirationDate(product.getExpirationDate());
		prod.setColor(product.getColor());
		prod.setMaterial(product.getMaterial());
		prod.setCategory(product.getCategory());
	}

	public void delete(String id) {
		try {
			repo.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private ProductDTO transformProductToProductDTO(Product product) {
		return ProductDTO.create(product);
	}
	
	private void validateQuantity(Integer quantity) {
		if (quantity < 0) {
			throw new NegativeValueException("Quantidade deve conter valor positivo."); 
		}
	}

	public void sendMessage(String queueName, Product product) {
		this.rabbitTemplate.convertAndSend(queueName, product);
	}

}
