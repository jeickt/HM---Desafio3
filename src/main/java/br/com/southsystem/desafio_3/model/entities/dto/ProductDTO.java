package br.com.southsystem.desafio_3.model.entities.dto;

import java.text.DecimalFormat;

import org.modelmapper.ModelMapper;

import br.com.southsystem.desafio_3.model.entities.Category;
import br.com.southsystem.desafio_3.model.entities.Product;
import lombok.Data;

@Data
public class ProductDTO {
	
	private String id;
	private String name;
	private Double price;
	private Integer quantity;
	
	private Category category;

    public static ProductDTO create(Product product) {
        ModelMapper modelMapper = new ModelMapper();
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        Double price = product.getGrossValue() * (product.getTaxes() / 100 + 1) * 1.45;
        DecimalFormat df = new DecimalFormat("#.##");
        dto.price = Double.valueOf(df.format(price).replace(",", "."));
        return dto;
    }

	@Override
	public String toString() {
		return "Id: " + id + ", "
				+ "produto: " + name + ", "
				+ "preço: R$ " + String.format("%.2f", price) + ", "
				+ "quantidade: " + quantity + ", "
				+ "categoria: " + category.getName() + ".";
	}

}