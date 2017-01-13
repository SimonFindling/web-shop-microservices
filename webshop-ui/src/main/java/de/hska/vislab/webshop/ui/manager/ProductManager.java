package de.hska.vislab.webshop.ui.manager;

import java.util.List;

import de.hska.vislab.webshop.ui.model.Product;


public interface ProductManager {
	public List<Product> getProducts();

	public Product getProductById(Long id);

	public Product getProductByName(String name);

	public int addProduct(String name, double price, Long categoryId, String details);

	public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);
	
	public boolean deleteProductsByCategoryId(Long categoryId);
	
    public void deleteProductById(Long id);
}
