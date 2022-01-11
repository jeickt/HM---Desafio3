package br.com.southsystem.desafio_3.model.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	@NotBlank(message="O valor não pode ser nulo ou vazio.")
	private String barCode;
	@NotBlank(message="O valor não pode ser nulo ou vazio.")
	private String series;
	@NotBlank(message="O valor não pode ser nulo ou vazio.")
	@Length(min=3, message="O tamanho mínimo de caracteres é 3.")
	private String name;
	@NotBlank(message="O valor não pode ser nulo ou vazio.")
	private String description;
	@NotNull(message="O valor não pode ser nulo ou vazio.")
	@Min(0)
	private Double grossValue;
	@NotNull(message="O valor não pode ser nulo ou vazio.")
	@Min(0)
	private Double taxes;
	@Past
	private Date manufacturingDate;
	private Date expirationDate;
	private String color;
	private String material;
	@NotNull(message="O valor não pode ser nulo ou vazio.")
	@Min(0)
	private Integer quantity;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	public Product() {
	}

	public Product(String id, String barCode, String series, String name, String description, Double grossValue,
			Double taxes, Date manufacturingDate, Date expirationDate, String color, String material,
			Integer quantity) {
		super();
		this.id = id;
		this.barCode = barCode;
		this.series = series;
		this.name = name;
		this.description = description;
		this.grossValue = grossValue;
		this.taxes = taxes;
		this.manufacturingDate = manufacturingDate;
		this.expirationDate = expirationDate;
		this.color = color;
		this.material = material;
		this.quantity = quantity;
	}
	
	public Product(String id, String barCode, String series, String name, String description, Double grossValue,
			Double taxes, Date manufacturingDate, Date expirationDate, String color, String material,
			Integer quantity, Category category) {
		super();
		this.id = id;
		this.barCode = barCode;
		this.series = series;
		this.name = name;
		this.description = description;
		this.grossValue = grossValue;
		this.taxes = taxes;
		this.manufacturingDate = manufacturingDate;
		this.expirationDate = expirationDate;
		this.color = color;
		this.material = material;
		this.quantity = quantity;
		this.category = category;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getGrossValue() {
		return grossValue;
	}

	public void setGrossValue(Double grossValue) {
		this.grossValue = grossValue;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Date getManufacturingDate() {
		return manufacturingDate;
	}

	public void setManufacturingDate(Date manufacturingDate) {
		this.manufacturingDate = manufacturingDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMaterial() {
		return material;
	}

	public void setMaterial(String material) {
		this.material = material;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", barCode=" + barCode + ", series=" + series + ", name=" + name + ", description="
				+ description + ", grossValue=" + grossValue + ", taxes=" + taxes + ", manufacturingDate="
				+ manufacturingDate + ", expirationDate=" + expirationDate + ", color=" + color + ", material="
				+ material + ", quantity=" + quantity + ", category=" + category + "]";
	}

}