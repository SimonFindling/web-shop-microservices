package de.hska.vislab.composite.productcategory.restclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.vislab.composite.productcategory.model.Product;

public class ProductCoreFallback implements ProductCoreRestClient {

	private static final String FALLBACK_TEXT = "product-core-service not found";

	@Override
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		ResponseEntity<Iterable<Product>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Iterable<Product>> getProducts(long categoryID) {
		ResponseEntity<Iterable<Product>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Iterable<Product>> getProducts(double min, double max, String details, String name) {
		ResponseEntity<Iterable<Product>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Long> postProduct(Product product) {
		ResponseEntity<Long> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Product> getProduct(long id) {
		ResponseEntity<Product> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteProduct(long id) {
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

}
