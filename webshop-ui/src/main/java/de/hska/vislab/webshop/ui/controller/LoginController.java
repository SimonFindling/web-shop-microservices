package de.hska.vislab.webshop.ui.controller;

import static de.hska.vislab.webshop.ui.util.Constants.*;
import java.util.Set;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.model.User;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;

@Controller
public class LoginController {

	private static final Logger LOGGER = Logger.getLogger(LoginController.class.getCanonicalName());

	@Autowired
	private UserManager userManager;

	@Autowired
	private ValidationService validator;

	@Autowired
	private MessageBundleService bundleService;

	private User loggedIn;

	@GetMapping({ PATH_LOGIN, PATH_BASE })
	public String getLoginPage(Model model) {
		model.addAttribute(ATTRIBUTE_USER, new User());
		HttpSession session = getSession(false);
		if (session != null) {
			User user = (User) session.getAttribute(ATTRIBUTE_USER);
			if (validator.isNotNull(user) && validator.isNotBlank(user.getUsername())
					&& userManager.doesUserAlreadyExist(user.getUsername())) {
				User response = userManager.getUserByUsername(user.getUsername());
				LOGGER.info("response=" + response + ", user=" + user);
				if (validator.isValidPassword(user, response)) {
					session.setAttribute(ATTRIBUTE_USER, response);
					session.setAttribute(ATTRIBUTE_MESSAGES, null);
					return REDIRECT_START;
				}
			}
		}
		return LOGIN;
	}

	@PostMapping(PATH_LOGIN)
	@Scope(SCOPE_SESSION)
	public String login(@ModelAttribute @Valid User user, BindingResult result, Model model) {
		bundleService.resetMessages(model);

		if (result.hasErrors()) {
			String[] stringArray = new String[result.getErrorCount()];
			int counter = 0;
			for (ObjectError error : result.getAllErrors()) {
				stringArray[counter] = error.getDefaultMessage();
				counter++;
			}
			bundleService.addMessage(model, stringArray);
			LOGGER.info("result.hasErrors=" + bundleService.getMessages());
			return LOGIN;
		}

		LOGGER.info("user=" + user.toString());
		User response = userManager.getUserByUsername(user.getUsername());
		LOGGER.info("respone=" + response);
		if (response != null) {
			if (validator.isValidPassword(user, response)) {
				HttpSession session = getSession(true);
				session.setAttribute(ATTRIBUTE_USER, response);
				session.setAttribute(ATTRIBUTE_MESSAGES, null);
				LOGGER.info(response + " logged in");
				return REDIRECT_START;
			} else {
				bundleService.addMessage(model, "error.password.wrong");

				LOGGER.info(user.toString() + "password is wrong");
				return LOGIN;
			}
		}
		LOGGER.info(user.toString() + "username doesn't exist");
		bundleService.addMessage(model, "error.username.wrong");
		return LOGIN;

	}

	@GetMapping(PATH_LOGOUT)
	public String logout() {
		HttpSession session = getSession(false);
		if (session != null) {
			// session.removeAttribute(ATTRIBUTE_USER);
			// session.removeAttribute(ATTRIBUTE_MESSAGES);
			// session.removeAttribute(ATTRIBUTE_PRODUCTS);
			// session.removeAttribute(ATTRIBUTE_PRODUCT);
			session.invalidate();
			session = null;
		}
		return REDIRECT_LOGIN;
	}

	public HttpSession getSession(boolean create) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpSession session = attr.getRequest().getSession(create);
		return session;
	}

	public Set<String> getMessages() {
		return bundleService.getMessages();
	}

	public User getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(User loggedIn) {
		this.loggedIn = loggedIn;
	}
}
