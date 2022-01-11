package br.com.southsystem.desafio_3;

import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.southsystem.desafio_3.model.entities.Category;
import br.com.southsystem.desafio_3.model.entities.Product;
import br.com.southsystem.desafio_3.model.entities.dto.ProductDTO;
import br.com.southsystem.desafio_3.repositories.ProductRepository;
import br.com.southsystem.desafio_3.services.CategoryService;
import br.com.southsystem.desafio_3.services.ProductService;
import br.com.southsystem.desafio_3.services.exceptions.NegativeValueException;
import br.com.southsystem.desafio_3.services.exceptions.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTests {

	ProductService service;
	
	@Mock
	ProductRepository repo;
	
	@Mock
	CategoryService categoryService;
	
	@Before
	public void setup() {
		service = new ProductService(repo, categoryService); 
	}

	@Test
	public void getAll_returnListOfProducts_whenSuccessful() {
		// cenário
        Product prod = new Product("c", "a", "a", "PC Gamer", "a", 1200.0, 3.0,  new Date(), new Date(), "n/a", null, null, new Category(null, "Computers"));
		when(repo.findAll()).thenReturn(List.of(prod, prod));
		
		// ação
		List<ProductDTO> products = service.findAll();

		// verificação
		Assert.assertEquals(2, products.size());
	}
	
	@Test
	public void findById_returnProductWithTheIdSearched_whenSuccessful() {
		// cenário
		Optional<Product> prod = Optional.of(new Product("c", "a", "a", "PC Gamer", "a", 1200.0, 3.0,  new Date(), new Date(), "n/a", null, null, new Category(null, "Computers")));
		when(repo.findById("c")).thenReturn(prod);
		String id = "c";

		// ação
		ProductDTO prodDTO = service.findById(id);
		
		//verificação
		Assert.assertEquals("PC Gamer", prodDTO.getName());
	}

	@Test (expected = ResourceNotFoundException.class)
	public void findByIdError_dontReturnTheProductWhitTheIdSearched_whenIdNotFoundError() {
		// cenário
		when(repo.findById("t")).thenThrow(ResourceNotFoundException.class);
		String id = "t";

		// ação
		service.findById(id);
	}
	
	@Test
	public void findByCategoryId_returnListOfProductsOfTheSameCategory_whenSuccessful() {
		// cenário
		Long id = 3L;
		Product prod = new Product("c", "a", "a", "PC Gamer", "a", 1200.0, 3.0,  new Date(), new Date(), "n/a", null, null, new Category(3L, "Computers"));
		when(repo.findByCategoryId(id)).thenReturn(List.of(prod));

		// ação
		List<ProductDTO> products = service.findByCategoryId(id);

		// verificação
		Assert.assertEquals(1, products.size());
	}

	@Test
	public void insert_createNewProduct_whenSuccessful() {
		// cenário
		Product prod = new Product("c", "a", "a", "PC Gamer", "a", 1200.0, 3.0,  new Date(), new Date(), "n/a", null, null, new Category(null, "Computers"));
		when(repo.save(prod)).thenReturn(prod);
		
		// ação
		Product product = service.insert(prod);

		// verificação
		Assert.assertEquals(1200.0, product.getGrossValue(), 0.00);
	}
	
	@Test (expected = NegativeValueException.class)
	public void insertError_dontCreateNewProduct_whenProductNotValidError() {
		// cenário
		Product prod = new Product("c", "a", "a", "PC Gamer", "a", 1200.0, 3.0,  new Date(), new Date(), "n/a", null, -3, new Category(null, "Computers"));
		
		// ação
		service.insert(prod);
	}
	
	@Test
	public void update_updateAProduct_whenSuccessful() {
		// cenário
		String id = "d";
		Product newProduct = new Product("d", "a", "a", "Macbook Pro", "a", 1400.0, 2.0,  new Date(), new Date(), "n/a", "n/a", 1, new Category(null, "Computers"));
		Optional<Product> prod = Optional.of(new Product("d", "a", "a", "Macbook Pro", "a", 700.0, 2.0,  new Date(), new Date(), "n/a", "n/a", 1, new Category(null, "Computers")));
		when(repo.findById(id)).thenReturn(prod);
		when(repo.save(newProduct)).thenReturn(newProduct);
		
		// ação
		Product product = service.update(id, newProduct);
		
		// verificação
		Assert.assertEquals(1400.00, product.getGrossValue(), 0.00);
		Assert.assertEquals(1, (int) product.getQuantity());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void updateError_dontUpdateAProduct_whenIdIsNotValidError() {
		// cenário
		String id = "k";
		Product newProduct = new Product("c", "a", "a", "Macbook Pro", "a", 1250.0, 2.0,  new Date(), new Date(), "n/a", "n/a", 1);
		when(repo.findById(id)).thenThrow(ResourceNotFoundException.class);
		
		// ação
		service.update(id, newProduct);
	}
	
	@Test
	public void delete_deleteAProduct_whenSeccessful() {
		// cenário
		String id = "c";
		
		// ação
		service.delete(id);		
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void deleteError_dontDeleteAProduct_whenIdIsNotValidError() {
		// cenário
		String id = "g";
		when(repo.findById(id)).thenThrow(ResourceNotFoundException.class);
		
		// ação
		service.delete(id);
		
		// verificação
		service.findById(id);			
	}

}
