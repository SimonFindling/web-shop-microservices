package de.hska.vislab.webshop.ui.util;

import de.hska.vislab.webshop.ui.model.User;

public class ValidationService {
	
	public boolean isValidPassword(String password, String another) {
		return another != null && !another.isEmpty() && another.equals(password);
	}

	public boolean isValidPassword(User user, User another) {
		return user != null && another != null && isValidPassword(user.getPassword(), another.getPassword());
	}
	
	public boolean isNull(User user) {
		return user == null;
	}
	
	public boolean isNotNull(User user) {
		return !isNull(user);
	}

	public boolean isNull(Object obj) {
		return obj == null;
	}
	
	public boolean isNotNull(Object obj) {
		return !isNull(obj);
	}
	
	public boolean isFirstnameBlank(User user) {
		return isBlank(user.getFirstname());
	}
	
	public boolean isLastnameBlank(User user) {
		return isBlank(user.getLastname());
	}
	
	public boolean isIdValid(User user) {
		return user.getId() == null || user.getId() < 1;
	}
	
	public boolean isRoleNull(User user) {
		return user.getRole() == null;
	}
	
	public boolean isBlank(String str) {
		return str == null || str.isEmpty();
	}
	
	public boolean isNotBlank(String str) {
		return !isBlank(str);
	}
}
