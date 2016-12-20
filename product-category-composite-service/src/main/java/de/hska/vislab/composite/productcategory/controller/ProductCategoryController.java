package de.hska.vislab.composite.productcategory.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.vislab.composite.productcategory.model.Category;
import de.hska.vislab.composite.productcategory.model.Product;
import de.hska.vislab.composite.productcategory.restclient.CategoryCoreRestClient;
import de.hska.vislab.composite.productcategory.restclient.ProductCoreRestClient;

@RestController
public class ProductCategoryController {

	private static final Logger LOGGER = Logger.getLogger(ProductCategoryController.class.getSimpleName());

	@Autowired
	private ProductCoreRestClient productClient;

	@Autowired
	private CategoryCoreRestClient categoryClient;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> getInfo() {
		return new ResponseEntity<>("This is product-category-composite-service", HttpStatus.OK);
	}

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "min", required = false) Double min,
			@RequestParam(name = "max", required = false) Double max,
			@RequestParam(name = "details", required = false) String details) {
		Iterable<Product> products;
		ResponseEntity<Iterable<Product>> response;
		if (validate(min) || validate(max) || validate(details) || validate(name)) {
			response = productClient.getProducts(min, max, details, name);
		} else {
			response = productClient.getAllProducts();
		}

		if (response.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		products = response.getBody();

		for (Product p : products) {
			if (validate(p.getCategoryID())) {
				ResponseEntity<Category> resp = categoryClient.getCategory(p.getCategoryID());
				if (resp.getStatusCode() == HttpStatus.OK) {
					p.category = resp.getBody();
				}
			}
		}

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ResponseEntity<Long> postProduct(@RequestBody(required = true) Product product) {
		if (!validate(product) || validate(product.id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (categoryClient.getCategory(product.category.id).getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		product.setCategoryID(product.category.id);
		ResponseEntity<Long> response = productClient.postProduct(product);
		if (response.getStatusCode() != HttpStatus.CREATED) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return response;
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id", required = true) long id) {
		ResponseEntity<Product> productResponse = productClient.getProduct(id);
		if (productResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(productResponse.getStatusCode());
		}
		Product product = productResponse.getBody();
		ResponseEntity<Category> categoryResponse = categoryClient.getCategory(product.getCategoryID());
		if (categoryResponse.getStatusCode() == HttpStatus.OK) {
			product.category = categoryResponse.getBody();
		}

		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	@RequestMapping(value = "/product/{id}/category", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategoryForProduct(@PathVariable(name = "id", required = true) long id) {
		ResponseEntity<Product> productResponse = productClient.getProduct(id);
		if (productResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(productResponse.getStatusCode());
		}
		Product product = productResponse.getBody();
		ResponseEntity<Category> categoryResponse = categoryClient.getCategory(product.getCategoryID());
		return categoryResponse.getStatusCode() == HttpStatus.OK
				? new ResponseEntity<>(categoryResponse.getBody(), categoryResponse.getStatusCode())
				: new ResponseEntity<>(categoryResponse.getStatusCode());
	}

	@RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable(required = true, name = "id") long id) {
		return productClient.deleteProduct(id);
	}

	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Category>> getCategories() {
		return categoryClient.getCategories("");
	}

	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public ResponseEntity<Long> postCategory(@RequestBody(required = true) Category category) {
		return categoryClient.postCategory(category);
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public ResponseEntity<Category> getCategory(@PathVariable(name = "id", required = true) long id) {
		return categoryClient.getCategory(id);
	}

	@RequestMapping(value = "/category/{id}/products", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProductsOfCategory(
			@PathVariable(name = "id", required = true) long id) {
		ResponseEntity<Category> response = categoryClient.getCategory(id);
		if (response.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(response.getStatusCode());
		}

		Category category = response.getBody();
		ResponseEntity<Iterable<Product>> productsResponse = productClient.getProducts(category.id);
		LOGGER.info("productsResponse=" + productsResponse);
		Iterable<Product> products = productsResponse.getBody();
		LOGGER.info("products=" + products);
		for (Product p : products) {
			p.category = category;
		}

		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@RequestMapping(value = "/category/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id", required = true) long id) {
		ResponseEntity<Category> response = getCategory(id);
		if (response.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		Category category = response.getBody();

		ResponseEntity<Iterable<Product>> productResponse = productClient.getProducts(category.id);
		for (Product p : productResponse.getBody()) {
			deleteProduct(p.id);
		}

		return categoryClient.deleteCategory(category.id);
	}

	private boolean validate(String str) {
		return validate((Object) str) && !str.isEmpty();
	}

	private boolean validate(Object val) {
		return val != null;
	}

	private boolean validate(Long id) {
		return id != null && id > 0;
	}
}
