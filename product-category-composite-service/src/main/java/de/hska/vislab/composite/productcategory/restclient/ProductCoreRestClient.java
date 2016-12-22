package de.hska.vislab.composite.productcategory.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hska.vislab.composite.productcategory.model.Product;

@FeignClient(name = "product-core-service", fallback = ProductCoreFallback.class, decode404 = true)
public interface ProductCoreRestClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getAllProducts();

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(
			@RequestParam(name = "category-id", required = true) long categoryID);
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getProducts(
			@RequestParam(name = "min", required = false) double min,
			@RequestParam(name = "max", required = false) double max,
			@RequestParam(name = "details", required = false) String details,
			@RequestParam(name = "name", required = false) String name);
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postProduct(@RequestBody Product product);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable(name = "id", required = true) long id);
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable(name = "id", required = true) long id);
}
