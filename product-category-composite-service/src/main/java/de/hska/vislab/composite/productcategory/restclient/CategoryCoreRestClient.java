package de.hska.vislab.composite.productcategory.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hska.vislab.composite.productcategory.model.Category;

@FeignClient("category-core-service")
public interface CategoryCoreRestClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Category>> getCategories(@RequestParam(name = "name", required = false) String name);

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postCategory(@RequestBody(required = true) Category category);

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id", required = true) long id);
}
