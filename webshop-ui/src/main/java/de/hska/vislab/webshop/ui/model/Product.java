package de.hska.vislab.webshop.ui.model;

public class Product {
	public Long id;
	public String name;
	public double price;
	public String details;
	public Category category;

	public Product() {

	}

	public Product(String name, double price, String details, Category category) {
		super();
		this.name = name;
		this.price = price;
		this.details = details;
		this.category = category;
	}

	public Product(String name, double price, String details) {
		super();
		this.name = name;
		this.price = price;
		this.details = details;
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
		return "Product [id=" + id + ", name=" + name + ", price=" + price + ", details=" + details + ", category="
				+ category + "]";
	}

}
