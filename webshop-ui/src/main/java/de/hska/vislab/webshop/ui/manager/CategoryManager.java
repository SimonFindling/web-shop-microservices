package de.hska.vislab.webshop.ui.manager;

import java.util.List;

import de.hska.vislab.webshop.ui.model.Category;


public interface CategoryManager {
	public List<Category> getCategories();

	public Category getCategory(Long id);

	public Category getCategoryByName(String name);

	public void addCategory(String name);

	public void delCategory(Category cat);

	public void delCategoryById(Long id);

}
