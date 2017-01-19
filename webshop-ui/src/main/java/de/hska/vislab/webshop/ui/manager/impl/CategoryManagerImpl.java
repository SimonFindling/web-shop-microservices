package de.hska.vislab.webshop.ui.manager.impl;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.vislab.webshop.ui.client.ProductCategoryClient;
import de.hska.vislab.webshop.ui.model.Category;

public class CategoryManagerImpl implements de.hska.vislab.webshop.ui.manager.CategoryManager {

	private static final Logger LOGGER = Logger.getLogger(CategoryManagerImpl.class.getCanonicalName());

	@Autowired
	private ProductCategoryClient client;

	@Override
	public Iterable<Category> getCategories() {
		try {
			ResponseEntity<Iterable<Category>> response = client.getCategories();
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return new ArrayList<Category>();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return new ArrayList<Category>();
		}
	}

	@Override
	public Category getCategory(Long id) {
		try {
			ResponseEntity<Category> response = client.getCategory(id);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Category getCategoryByName(String name) {
		try {
			ResponseEntity<Iterable<Category>> response = client.getCategories(name);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				for (Category c : response.getBody()) {
					return c;
				}
				return null;
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	@Override
	public Long addCategory(String name) {
		Category category = getCategoryByName(name);
		return addCategory(category);
	}

	@Override
	public Long addCategory(Category category) {
		try {
			ResponseEntity<Long> response = client.postCategory(category);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return response.getBody();
			}
			return null;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean delCategory(Category cat) {
		return delCategoryById(cat.getId());
	}

	@Override
	public boolean delCategoryById(Long id) {
		try {
			ResponseEntity<Void> response = client.deleteCategory(id);
			if (response.getStatusCode().equals(HttpStatus.NO_CONTENT)) {
				return true;
			}
			return false;
		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
			return false;
		}
	}

}
