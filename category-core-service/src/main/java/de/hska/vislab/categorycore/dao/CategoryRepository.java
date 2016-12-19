package de.hska.vislab.categorycore.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import de.hska.vislab.categorycore.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
	
	@Query("FROM Category c WHERE LOWER(c.name) LIKE LOWER(?1)")
	public Iterable<Category> getCategoryByName(String name);
}
