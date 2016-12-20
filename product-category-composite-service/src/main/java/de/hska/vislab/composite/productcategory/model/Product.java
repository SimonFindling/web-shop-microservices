package de.hska.vislab.composite.productcategory.model;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 3398246529402664188L;

	public Long id;
	public String name;
	public double price;
	public String details;
	public Long categoryID;
	public Category category;

	protected Product() {
		super();
	}

	public Product(Long id, String name, double price, String details) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
	}

	public Product(Long id, String name, double price, String details, Long categoryID) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.categoryID = categoryID;
	}

	public Product(Long id, String name, double price, String details, Category category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((categoryID == null) ? 0 : categoryID.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		if (categoryID == null) {
			if (other.categoryID != null)
				return false;
		} else if (!categoryID.equals(other.categoryID))
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", details=" + details + ", categoryID="
				+ categoryID + ", category=" + category + "]";
	}

}
