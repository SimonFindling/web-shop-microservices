package de.hska.vislab.productcore.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.vislab.productcore.dao.ProductRepository;
import de.hska.vislab.productcore.model.Product;

@RestController
public class ProductController {

	private final static Logger LOGGER = Logger.getLogger(ProductController.class.getSimpleName());

	@Autowired
	private ProductRepository repo;

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Product>> getAllProducts(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "min", required = false) Double min,
			@RequestParam(name = "max", required = false) Double max,
			@RequestParam(name = "details", required = false) String details,
			@RequestParam(name = "category-id", required = false) Long categoryID) {
		Iterable<Product> products;
		if (validate(min) || validate(max) || validate(details) || validate(name)) {
			min = min == null ? Double.MIN_NORMAL : min;
			max = max == null ? Double.MAX_VALUE : max;
			details = "%" + (details != null ? details.toLowerCase() : "") + "%";
			name = "%" + (name != null ? name.toLowerCase() : "") + "%";

			products = repo.getProductByNameAndDetailAndPriceBetween(name, details, min, max);
		} else if (validate(categoryID)) {
			products = repo.getProductByCategoryID(categoryID);
		} else {
			products = repo.findAll();
		}

		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postProduct(@RequestBody(required = true) Product product) {
		if (!validate(product) || validate(product.id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		LOGGER.info(product.toString());
		product = repo.save(product);

		if (product.id != null) {
			return new ResponseEntity<>(product.id, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Product> getProduct(@PathVariable(required = true, name = "id") long id) {
		Product product = repo.findOne(id);
		if (validate(product)) {
			return new ResponseEntity<>(product, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> deleteProduct(@PathVariable(required = true, name = "id") long id) {
		if (repo.findOne(id) == null) {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
		repo.delete(id);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
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
