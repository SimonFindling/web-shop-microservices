package de.hska.vislab.webshop.ui.manager.impl;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.vislab.webshop.ui.client.ProductCategoryClient;
import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.Product;

public class ProductManagerImpl implements ProductManager {

	private static final Logger LOGGER = Logger.getLogger(ProductManagerImpl.class.getCanonicalName());

	@Autowired
	private ProductCategoryClient client;

	@Override
	public Iterable<Product> getProducts() {
		try {
			ResponseEntity<Iterable<Product>> response = client.getProducts();
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return new ArrayList<Product>();
		} catch (Exception e) {
			LOGGER.info(e.toString());
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	@Override
	public Product getProductById(Long id) {
		try {
			ResponseEntity<Product> response = client.getProduct(id);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Product getProductByName(String name) {
		try {
			ResponseEntity<Iterable<Product>> response = client.getProducts(name);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				for (Product p : response.getBody()) {
					return p;
				}
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.toString());
			return null;
		}
	}

	@Override
	public Long addProduct(Product product) {
		try {
			ResponseEntity<Long> response = client.postProduct(product);
			if (response.getStatusCode().equals(HttpStatus.CREATED)) {
				return response.getBody();
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.toString());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Long addProduct(String name, double price, Long categoryId, String details) {
		Category category;
		try {
			ResponseEntity<Category> response = client.getCategory(categoryId);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				category = response.getBody();
			} else {
				return null;
			}
		} catch (Exception e) {
			LOGGER.info(e.toString());
			e.printStackTrace();
			return null;
		}
		Product product = new Product(name, price, details, category);
		return addProduct(product);
	}

	@Override
	public Iterable<Product> getProductsForSearchValues(String searchValue, Double searchMinPrice,
			Double searchMaxPrice) {
		try {
			ResponseEntity<Iterable<Product>> response = client.getProducts( searchMinPrice != null ? searchMinPrice.doubleValue(): Double.MIN_VALUE,
					searchMaxPrice != null ? searchMaxPrice.doubleValue() : Double.MAX_VALUE, searchValue);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return new ArrayList<Product>();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return new ArrayList<Product>();
		}
	}

	@Override
	public boolean deleteProductsByCategoryId(Long categoryId) {
		try {
			ResponseEntity<Iterable<Product>> response = client.getProductsOfCategory(categoryId);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				for (Product p : response.getBody()) {
					ResponseEntity<Void> delResponse = client.deleteProduct(p.getId());
				}
				return true;
			}
			return false;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProductById(Long id) {
		try {
			ResponseEntity<Void> response = client.deleteProduct(id);
			if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return false;
		}

	}

}
