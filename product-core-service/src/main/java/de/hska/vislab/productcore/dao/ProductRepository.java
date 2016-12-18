package de.hska.vislab.productcore.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import de.hska.vislab.productcore.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

	@Query("FROM Product p WHERE LOWER(p.name) LIKE LOWER(?1)")
	Iterable<Product> getProductByName(String name);
	
	@Query("FROM Product p WHERE LOWER(p.details) LIKE LOWER(?1)")
	Iterable<Product> getProductByDetail(String detail);
	
	@Query("FROM Product p WHERE p.price BETWEEN ?1 AND ?2")
	Iterable<Product> getProductByPriceBetween(double min, double max);
	
	@Query("FROM Product p WHERE LOWER(p.name) LIKE LOWER(?1) AND p.price BETWEEN ?2 AND ?3")
	Iterable<Product> getProductByNameAndPriceBetween(String name, double min, double max);
	
	
	@Query("FROM Product p WHERE LOWER(p.details) LIKE LOWER(?1) AND p.price BETWEEN ?2 AND ?3")
	Iterable<Product> getProductByDetailAndPriceBetween(String detail, double min, double max);
	
	
	@Query("FROM Product p WHERE LOWER(p.name) LIKE LOWER(?1) AND LOWER(p.details) LIKE LOWER(?2) AND p.price BETWEEN ?3 AND ?4")
	Iterable<Product> getProductByNameAndDetailAndPriceBetween(String name, String detail, double min, double max);
}
