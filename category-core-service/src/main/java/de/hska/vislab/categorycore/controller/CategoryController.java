package de.hska.vislab.categorycore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.vislab.categorycore.dao.CategoryRepository;
import de.hska.vislab.categorycore.model.Category;

@RestController
public class CategoryController {

	@Autowired
	private CategoryRepository repo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Category>> getCategories(
			@RequestParam(name = "name", required = false) String name) {
		return validate(name) ? new ResponseEntity<>(repo.getCategoryByName(name), HttpStatus.OK)
				: new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postCategory(@RequestBody(required = true) Category category) {
		if (!validate(category) || !validate(category.id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		category = repo.save(category);

		if (category.id != null) {
			return new ResponseEntity<>(category.id, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable(name = "id", required = true) long id) {
		return new ResponseEntity<>(repo.findOne(id), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id", required = true) long id) {
		repo.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	private boolean validate(Object obj) {
		return obj != null;
	}

	private boolean validate(String str) {
		return str != null && !str.isEmpty();
	}
}
