package de.hska.vislab.categorycore.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category implements Serializable {

	private static final long serialVersionUID = 3112775006335009399L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long id;
	
	public String name;
	
	@JsonIgnore
	@ElementCollection
	public Set<Long> productIDs = new HashSet<>();

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((productIDs == null) ? 0 : productIDs.hashCode());
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
		if (productIDs == null) {
			if (other.productIDs != null)
				return false;
		} else if (!productIDs.equals(other.productIDs))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Category [id=" + id 
				+ ", name=" + name
				+ ", productIDs=" + productIDs 
				+ "]";
	}
	
	
}
