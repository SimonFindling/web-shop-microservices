package de.hska.vislab.composite.productcategory.restclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.vislab.composite.productcategory.model.Category;

public class CategoryCoreFallback implements CategoryCoreRestClient {

	private static final String FALLBACK_TEXT = "category-core-service not found";

	@Override
	public ResponseEntity<Iterable<Category>> getCategories(String name) {
		ResponseEntity<Iterable<Category>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Long> postCategory(Category category) {
		ResponseEntity<Long> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Category> getCategory(long id) {
		ResponseEntity<Category> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteCategory(long id) {
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

}
