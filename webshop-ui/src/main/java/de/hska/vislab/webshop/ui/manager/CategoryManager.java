package de.hska.vislab.webshop.ui.manager;

import de.hska.vislab.webshop.ui.model.Category;


public interface CategoryManager {
	public Iterable<Category> getCategories();

	public Category getCategory(Long id);

	public Category getCategoryByName(String name);

	public Long addCategory(String name);
	
	public Long addCategory(Category category);

	public boolean delCategory(Category cat);

	public boolean delCategoryById(Long id);

}
