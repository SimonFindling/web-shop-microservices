package de.hska.vislab.webshop.ui.util;

import static de.hska.vislab.webshop.ui.util.Constants.*;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.ui.Model;

public class MessageBundleService {
	@Autowired
	private ResourceBundleMessageSource messageSource;

	private Set<String> messages = new HashSet<>();

	// TODO change to Locale.getDefault();
	private Locale locale = new Locale("de");

	public void addMessage(Model model, String... message) {
		for (String s : message) {
			messages.add(messageSource.getMessage(s, null, locale));
		}
		model.addAttribute(ATTRIBUTE_MESSAGES, messages);
	}

	public void resetMessages(Model model) {
		messages = new HashSet<>();
		model.addAttribute(ATTRIBUTE_MESSAGES, messages);
	}

	public Set<String> getMessages() {
		return messages;
	}
}
