package de.hska.vislab.webshop.ui.controller;

import static de.hska.vislab.webshop.ui.util.Constants.*;

import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.model.User;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;

@Controller
public class RegistrationController {
	

	private static final Logger LOGGER = Logger.getLogger(RegistrationController.class.getCanonicalName());

	private User user;

	@Autowired
	private MessageBundleService bundleService;

	@Autowired
	private ValidationService validator;

	@Autowired
	private UserManager manager;

	@GetMapping(PATH_REGISTER)
	public String getRegistrationPage(Model model) {
		model.addAttribute(ATTRIBUTE_USER, new User());
		return REGISTER;
	}

	@PostMapping(PATH_REGISTER)
	public String register(Model model, @ModelAttribute @Valid User user, BindingResult result) {
		bundleService.resetMessages(model);
		this.user = user;
		LOGGER.info("user=" + user);
		if (!isValid(model, user, result)) {
			return REGISTER;
		}

		boolean success = manager.registerUser(user);

		if (success) {
			bundleService.addMessage(model, "success.registration");
			User template = new User();
			template.setUsername(user.getUsername());
			model.addAttribute(ATTRIBUTE_USER, template);
			return LOGIN;
		}
		bundleService.addMessage(model, "error.registration.failed");

		return REGISTER;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private boolean isValid(Model model, User user, BindingResult result) {
		if (validator.isFirstnameBlank(user)) {
			bundleService.addMessage(model, "error.firstname.required");
		}
		if (validator.isLastnameBlank(user)) {
			bundleService.addMessage(model, "error.lastname.required");
		}
		if (validator.isBlank(user.getPasswordWdh())) {
			bundleService.addMessage(model, "error.password.required");
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
		}
		if (manager.doesUserAlreadyExist(user.getUsername())) {
			bundleService.addMessage(model, "error.username.alreadyInUse");
		}
		if (!validator.isValidPassword(user.getPassword(), user.getPasswordWdh())) {
			bundleService.addMessage(model, "error.password.notEqual");
		}
		if (bundleService.getMessages().size() > 0) {
			return false;
		}
		return true;
	}
}
