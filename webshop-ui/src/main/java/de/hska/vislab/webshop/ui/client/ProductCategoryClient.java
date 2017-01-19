package de.hska.vislab.webshop.ui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.Product;

@FeignClient(value = "product-category-composite-service")
public interface ProductCategoryClient {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getInfo();

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "min", required = false) Double min,
			@RequestParam(name = "max", required = false) Double max,
			@RequestParam(name = "details", required = false) String details);

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(name = "min", required = false) Double min,
			@RequestParam(name = "max", required = false) Double max,
			@RequestParam(name = "details", required = false) String details);

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts();

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(name = "name", required = false) String name);

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ResponseEntity<Long> postProduct(@RequestBody(required = true) Product product);

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(value = "/product/{id}/category", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryForProduct(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable(required = true, name = "id") long id);

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Category>> getCategories();

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public ResponseEntity<Long> postCategory(@RequestBody(required = true) Category category);

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Category>> getCategories(
			@RequestParam(defaultValue = "", required = false, name = "name") String name);

	@RequestMapping(value = "/category/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProductsOfCategory(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id", required = true) long id);
}
