package de.hska.vislab.webshop.ui.controller;

import static de.hska.vislab.webshop.ui.util.Constants.*;

import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import de.hska.vislab.webshop.ui.model.Category;
import de.hska.vislab.webshop.ui.model.User;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;

@Controller
public class CategoryController {
	private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getCanonicalName());

	@Autowired
	private ValidationService validator;
	@Autowired
	private MessageBundleService bundleService;
	@Autowired
	private CategoryManager categoryManager;

	private User loggedIn;

	@GetMapping(PATH_EDIT)
	public String getCategoryPage(Model model) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
		model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
		model.addAttribute(ATTRIBUTE_CATEGORY, new Category());
		return EDIT;
	}

	@PostMapping(PATH_CATEGORY)
	public String addCategory(Model model, @ModelAttribute @Valid Category category, BindingResult result) {
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
			model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
			model.addAttribute(ATTRIBUTE_CATEGORY, category);
			return EDIT;
		}
		if (categoryManager.getCategoryByName(category.getName()) != null) {
			bundleService.addMessage(model, "error.catname.exists");
			model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
			model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
			model.addAttribute(ATTRIBUTE_CATEGORY, category);
			return EDIT;
		}
		category.setId(null);
		Long id = categoryManager.addCategory(category);
		if (id == null) {
			bundleService.addMessage(model, "error.category.failed");
			model.addAttribute(ATTRIBUTE_USER, getSession(false).getAttribute(ATTRIBUTE_USER));
			model.addAttribute(ATTRIBUTE_CATEGORIES, categoryManager.getCategories());
			model.addAttribute(ATTRIBUTE_CATEGORY, category);
		}
		return REDIRECT_EDIT;
	}

	@GetMapping(PATH_DELETE_CAT)
	public String deleteCategory(Model model, @RequestParam(required = true, value = "id") long id) {
		if (!isLoggedIn()) {
			return REDIRECT_LOGIN;
		}
		if (id < 1) {
			bundleService.addMessage(model, "error.category.failed");
			getCategoryPage(model);
		}
		categoryManager.delCategoryById(id);
		return REDIRECT_EDIT;
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
