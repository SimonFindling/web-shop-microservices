package de.hska.vislab.webshop.ui.controller;

import java.util.Locale;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import de.hska.vislab.webshop.ui.model.User;

@Controller
@Scope("session")
public class LoginController {

	private static final Logger LOGGER = Logger.getLogger(LoginController.class.getSimpleName());

	@Autowired
	private MessageSource messageSource;

	private Locale locale = Locale.getDefault();

	private HttpSession session;
	private Set<String> messages;
	private User loggedIn;

	@GetMapping({ "/login", "/" })
	public String loginForm(Model model) {
		model.addAttribute("user", new User());
		if (session != null && session.getAttribute("user") != null) {
			return "start";
		}

		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		session = attr.getRequest().getSession();

		return "login";
	}

	@PostMapping("login")
	public String login(@ModelAttribute @Valid User user, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "login";
		}

		LOGGER.fine(user.toString());
		this.setLoggedIn(user);
		// TODO get user from user-composite-service
		if (user != null) {
			// TODO compare with given user from user-composite-service
			if (user.getPassword().equals("test")) {
				session.setAttribute("user", user);
				session.setAttribute("message", "");
				return "start";
			} else {
				messages.add(messageSource.getMessage("error.password.wrong", null, locale));
				model.addAttribute("messages", messages);
				return "login";
			}
		}
		messages.add(messageSource.getMessage("error.username.wrong", null, locale));
		model.addAttribute("messages", messages);
		return "login";

	}

	public Set<String> getMessages() {
		return messages;
	}

	public void setMessages(Set<String> messages) {
		this.messages = messages;
	}

	public User getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(User loggedIn) {
		this.loggedIn = loggedIn;
	}

}
