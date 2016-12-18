package de.hska.vislab.productcore.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product implements Serializable {

	private static final long serialVersionUID = -3594098690131855980L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;

	public String name;
	public double price;
	public String details;
	public Long categoryID;

	protected Product() {
		super();
	}

	public Product(long id, String name, double price, String details, long categoryID) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.details = details;
		this.categoryID = categoryID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (categoryID ^ (categoryID >>> 32));
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
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
		if (categoryID != other.categoryID)
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (id != other.id)
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
		return "Product [id=" + id 
				+ ", name=" + name 
				+ ", price=" + price 
				+ ", details=" + details
				+ ", categoryID=" + categoryID 
				+ "]";
	}

}
