package de.hska.vislab.webshop.ui.manager;

import de.hska.vislab.webshop.ui.model.Product;


public interface ProductManager {
	public Iterable<Product> getProducts();

	public Product getProductById(Long id);

	public Product getProductByName(String name);

	public Long addProduct(String name, double price, Long categoryId, String details);

	public Long addProduct(Product product);
	
	public Iterable<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice);
	
	public boolean deleteProductsByCategoryId(Long categoryId);
	
    public boolean deleteProductById(Long id);
}
