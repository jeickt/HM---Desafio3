package br.com.southsystem.desafio_3;

import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.southsystem.desafio_3.model.entities.Category;
import br.com.southsystem.desafio_3.repositories.CategoryRepository;
import br.com.southsystem.desafio_3.services.CategoryService;
import br.com.southsystem.desafio_3.services.exceptions.ResourceNotFoundException;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceTests {
	
	CategoryService service;
	
	@Mock
	CategoryRepository repo;
	
	@Before
	public void setup() {
		service = new CategoryService(repo); 
	}

	@Test
	public void getAll_returnListOfCategories_whenSuccessful() {
		// cenário
		Category category = new Category(null, "Computers");
		when(repo.findAll()).thenReturn(List.of(category, category));
		
		//ação
		List<Category> categories = service.findAll();
		
		//verificação
		Assert.assertEquals(2, categories.size());
	}
	
	@Test
	public void findById_returnCategoryWithTheIdSearched_whenSuccessful() {
		// cenário
		Long id = 3L;
		Optional<Category> cat = Optional.of(new Category(null, "Computers"));
		when(repo.findById(id)).thenReturn(cat);

		
		//ação
		Category category = service.findById(id);
		
		//verificação
		Assert.assertEquals("Computers", category.getName());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void findByIdError_dontReturnTheCategoryWhitTheIdSearched_whenIdNotFoundError() {
		// cenário
		Long id = 7L;
		when(repo.findById(id)).thenThrow(ResourceNotFoundException.class);

		// ação
		service.findById(id);
	}
	
	@Test
	public void insert_createNewCategory_whenSuccessful() {
		// cenário
		Category cat = new Category(null, "Furniture");
		when(repo.save(cat)).thenReturn(cat);

		// ação
		Category category = service.insert(cat);

		// verificação
		Assert.assertEquals("Furniture", category.getName());
	}
	
	@Test
	public void update_updateACategory_whenSuccessful() {
		// cenário
		Long id = 2L;
		Category newCategory = new Category(null, "Magazines");
		Optional<Category> cat = Optional.of(new Category(null, "Computers"));
		when(repo.findById(id)).thenReturn(cat);
		when(repo.save(newCategory)).thenReturn(newCategory);

		// ação
		Category category = service.update(id, newCategory);
		
		// verificação
		Assert.assertEquals("Magazines", category.getName());
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void updateError_dontUpdateACategory_whenIdIsNotValidError() {
		// cenário
		Long id = 6L;
		Category newCategory = new Category(null, "Magazines");
		when(repo.findById(id)).thenThrow(ResourceNotFoundException.class);

		// ação
		service.update(id, newCategory);
	}
	
	@Test
	public void delete_deleteACategory_whenSeccessful() {
		// cenário
		Long id = 3L;
		
		// ação
		service.delete(id);		
	}
	
	@Test (expected = ResourceNotFoundException.class)
	public void deleteError_dontDeleteACategory_whenIdIsNotValidError() {
		// cenário
		Long id = 7L;
		when(repo.findById(id)).thenThrow(ResourceNotFoundException.class);
		
		// ação
		service.delete(id);
		
		// verificação
		service.findById(id);			
	}

}