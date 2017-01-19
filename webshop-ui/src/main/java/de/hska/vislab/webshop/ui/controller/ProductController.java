package de.hska.vislab.webshop.ui.controller;

import static de.hska.vislab.webshop.ui.util.Constants.*;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.hska.vislab.webshop.ui.manager.CategoryManager;
import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.Product;
import de.hska.vislab.webshop.ui.model.User;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;

@Controller
@Scope(SCOPE_SESSION)
public class ProductController {

	private static final Logger LOGGER = Logger.getLogger(ProductController.class.getCanonicalName());

	@Autowired
	private ProductManager manager;
	@Autowired
	private ValidationService validator;
	@Autowired
	private MessageBundleService bundleService;
	@Autowired
	private CategoryManager categoryManager;

	private User loggedIn;

	@GetMapping(PATH_START)
	public String getStartPage(Model model,
			@RequestParam(defaultValue = "", value = REQUEST_PARAM_SEARCH_VALUE) String searchValue,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MIN_PRICE) String minPrice,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MAX_PRICE) String maxPrice) {
		HttpSession session = getSession(false);
		if (session != null) {
			loggedIn = (User) session.getAttribute(ATTRIBUTE_USER);
			LOGGER.info("loggedIn=" + loggedIn);
			if (isLoggedIn()) {
				model.addAttribute(ATTRIBUTE_USER, loggedIn);
				Product search = new Product();
				search.setSearchValue(searchValue);
				search.setMinPrice(minPrice.equals("") ? null : Double.valueOf(minPrice));
				search.setMaxPrice(maxPrice.equals("") ? null : Double.valueOf(maxPrice));
				model.addAttribute(ATTRIBUTE_SEARCH, search);
				Iterable<Product> products = manager.getProductsForSearchValues(searchValue,
						validator.isNotNull(search.getMinPrice()) ? search.getMinPrice() : Double.MIN_VALUE,
						validator.isNotNull(search.getMaxPrice()) ? search.getMaxPrice() : Double.MAX_VALUE);
				model.addAttribute(ATTRIBUTE_PRODUCTS, products);
				return START;
			}
		}
		return REDIRECT_LOGIN;
	}

	@PostMapping(PATH_SEARCH)
	public String searchProducts(Model model, @ModelAttribute Product product) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		if (validator.isNull(product)) {
			return START;
		}
		HttpSession session = getSession(false);
		if (session == null) {
			return REDIRECT_LOGIN;
		}
		LOGGER.info("product=" + product);
		Iterable<Product> products = manager.getProductsForSearchValues(product.getSearchValue(), product.getMinPrice(),
				product.getMaxPrice());
		model.addAttribute(ATTRIBUTE_PRODUCTS, products);
		model.addAttribute(ATTRIBUTE_SEARCH, product);
		model.addAttribute(getSession(false).getAttribute(ATTRIBUTE_USER));
		return START;
	}

	@GetMapping(PATH_DETAILS)
	public String showProductDetails(Model model, @RequestParam(required = true, value = REQUEST_PARAM_ID) long id,
			@RequestParam(defaultValue = "", value = REQUEST_PARAM_SEARCH_VALUE) String searchValue,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MIN_PRICE) String minPrice,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MAX_PRICE) String maxPrice) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		if (id < 1) {
			return REDIRECT_START + "?" + REQUEST_PARAM_SEARCH_VALUE + "=" + searchValue + "&" + REQUEST_PARAM_MIN_PRICE
					+ "=" + minPrice + "&" + REQUEST_PARAM_MAX_PRICE + "=" + maxPrice;
		}
		Product product = manager.getProductById(id);
		LOGGER.info("detailproduct=" + product);
		if (product == null) {
			return REDIRECT_START + "?" + REQUEST_PARAM_SEARCH_VALUE + "=" + searchValue + "&" + REQUEST_PARAM_MIN_PRICE
					+ "=" + minPrice + "&" + REQUEST_PARAM_MAX_PRICE + "=" + maxPrice;
		}
		Product search = new Product();
		search.setSearchValue(searchValue);
		search.setMinPrice(minPrice.equals("") ? null : Double.valueOf(minPrice));
		search.setMaxPrice(maxPrice.equals("") ? null : Double.valueOf(maxPrice));

		model.addAttribute(ATTRIBUTE_PRODUCT, product);
		model.addAttribute(ATTRIBUTE_SEARCH, search);
		model.addAttribute(getSession(false).getAttribute(ATTRIBUTE_USER));
		return DETAILS;
	}

	@GetMapping(PATH_DELETE)
	public String deleteProduct(Model model, @RequestParam(required = true, value = REQUEST_PARAM_ID) long id,
			@RequestParam(defaultValue = "", value = REQUEST_PARAM_SEARCH_VALUE) String searchValue,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MIN_PRICE) String minPrice,
			@RequestParam(defaultValue = "", required = false, value = REQUEST_PARAM_MAX_PRICE) String maxPrice) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		if (id < 1) {
			return REDIRECT_START + "?" + REQUEST_PARAM_SEARCH_VALUE + "=" + searchValue + "&" + REQUEST_PARAM_MIN_PRICE
					+ "=" + minPrice + "&" + REQUEST_PARAM_MAX_PRICE + "=" + maxPrice;
		}
		manager.deleteProductById(id);

		return REDIRECT_START + "?" + REQUEST_PARAM_SEARCH_VALUE + "=" + searchValue + "&" + REQUEST_PARAM_MIN_PRICE
				+ "=" + minPrice + "&" + REQUEST_PARAM_MAX_PRICE + "=" + maxPrice;
	}

	@GetMapping(PATH_ADD)
	public String getAddProductPage(Model model) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
		model.addAttribute(ATTRIBUTE_PRODUCT, new Product());
		model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());

		return ADD;
	}

	@PostMapping(PATH_ADD)
	public String addProduct(Model model, @ModelAttribute @Valid Product product, BindingResult result) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		if (result.hasErrors()) {
			String[] stringArray = new String[result.getErrorCount()];
			int counter = 0;
			for (ObjectError error : result.getAllErrors()) {
				stringArray[counter] = error.getDefaultMessage();
				counter++;
			}
			bundleService.addMessage(model, stringArray);
			LOGGER.info("result.hasErrors=" + bundleService.getMessages());
			model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
			model.addAttribute(ATTRIBUTE_PRODUCT, product);
			model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
			return ADD;
		}
		LOGGER.info("addproduct=" + product);
		product.setId(null);
		Category category = categoryManager.getCategory(product.getCategory().getId());
		product.setCategory(category);
		Long id = manager.addProduct(product);
		if (id == null) {
			bundleService.addMessage(model, "error.product.failed");
			model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
			model.addAttribute(ATTRIBUTE_PRODUCT, product);
			model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
			return ADD;
		}

		return REDIRECT_START;

	}

	private HttpSession getSession(boolean create) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(create);
		return session;
	}

	private boolean isLoggedIn() {
		User user = getSession(false) != null ? (User) getSession(false).getAttribute(ATTRIBUTE_USER) : null;
		return validator.isNotNull(user) && validator.isNotBlank(user.getUsername())
				&& validator.isNotNull(user.getId());
	}

	public User getLoggedIn() {
		return loggedIn;
	}
}
