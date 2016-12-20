package de.hska.vislab.composite.productcategory.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

public class Category implements Serializable {

	private static final long serialVersionUID = -951317120077253354L;

	public Long id;
	public String name;
	@JsonIgnore
	public Set<Long> productIDs = new HashSet<>();
	@JsonIgnore
	public Set<Product> products = new HashSet<>();

	protected Category() {
		super();
	}

	public Category(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category(Long id, String name, Set<Long> productIDs) {
		super();
		this.id = id;
		this.name = name;
		this.productIDs = productIDs;
	}

	public Category(Long id, String name, Set<Product> products, Set<Long> productIDs) {
		super();
		this.id = id;
		this.name = name;
		this.productIDs = productIDs == null ? new HashSet<>() : productIDs;
		this.products = products == null ? new HashSet<>() : products;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Category other = (Category) obj;
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
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", productIDs=" + productIDs + "]";
	}

}
