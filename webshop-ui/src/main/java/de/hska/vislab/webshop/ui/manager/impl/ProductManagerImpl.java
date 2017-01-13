package de.hska.vislab.webshop.ui.manager.impl;

import java.util.List;

import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.model.Product;

public class ProductManagerImpl implements ProductManager {

	@Override
	public List<Product> getProducts() {
		return null;
	}

	@Override
	public Product getProductById(Long id) {
		return null;
	}

	@Override
	public Product getProductByName(String name) {
		return null;
	}

	@Override
	public int addProduct(String name, double price, Long categoryId, String details) {
		return 0;
	}

	@Override
	public List<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice, Double searchMaxPrice) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteProductsByCategoryId(Long categoryId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteProductById(Long id) {
		// TODO Auto-generated method stub

	}

}
