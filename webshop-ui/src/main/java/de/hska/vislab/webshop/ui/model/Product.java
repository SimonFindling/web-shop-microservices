package de.hska.vislab.webshop.ui.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Product {
	private Long id;
	@NotNull(message = "error.product.name.required")
	private String name;
	@NotNull(message = "error.product.price.required")
	@Min(value = 0l, message = "error.product.price.regex")
	private Double price;
	@JsonIgnore
	private Double minPrice;
	@JsonIgnore 
	private Double maxPrice;
	@JsonIgnore
	private String searchValue;
	private String details;
	private Category category;

	public Product() {

	}

	public Product(String name, Double price, String details, Category category) {
		super();
		this.name = name;
		this.price = price;
		this.details = details;
		this.category = category;
	}

	public Product(String name, Double price, String details) {
		super();
		this.name = name;
		this.price = price;
		this.details = details;
	}

	public Product(Long id, String name, Double price, String details, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.category = category;
	}

	public Product(Long id, String name, Double price, Double minPrice, Double maxPrice, String searchValue,
			String details, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.searchValue = searchValue;
		this.details = details;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}

	public Double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(Double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((maxPrice == null) ? 0 : maxPrice.hashCode());
		result = prime * result + ((minPrice == null) ? 0 : minPrice.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((price == null) ? 0 : price.hashCode());
		result = prime * result + ((searchValue == null) ? 0 : searchValue.hashCode());
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
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (maxPrice == null) {
			if (other.maxPrice != null)
				return false;
		} else if (!maxPrice.equals(other.maxPrice))
			return false;
		if (minPrice == null) {
			if (other.minPrice != null)
				return false;
		} else if (!minPrice.equals(other.minPrice))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		if (searchValue == null) {
			if (other.searchValue != null)
				return false;
		} else if (!searchValue.equals(other.searchValue))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", minPrice=" + minPrice + ", maxPrice="
				+ maxPrice + ", searchValue=" + searchValue + ", details=" + details + ", category=" + category + "]";
	}

}
