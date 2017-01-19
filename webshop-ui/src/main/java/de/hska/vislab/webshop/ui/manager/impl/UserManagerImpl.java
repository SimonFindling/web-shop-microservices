package de.hska.vislab.webshop.ui.manager.impl;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import de.hska.vislab.webshop.ui.client.RoleCoreClient;
import de.hska.vislab.webshop.ui.client.UserCompositeClient;
import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.model.Role;
import de.hska.vislab.webshop.ui.model.User;

public class UserManagerImpl implements UserManager {

	private static final Logger LOGGER = Logger.getLogger(UserManagerImpl.class.getCanonicalName());

	@Autowired
	private UserCompositeClient client;

	@Autowired
	private RoleCoreClient roleClient;

	@Override
	public boolean registerUser(String username, String name, String lastname, String password, Role role) {
		User user = new User(null, username, lastname, name, password, role);
		if (user.getRole() == null) {
			user.setRole(new Role(2l, 1, "user"));
		}
		return registerUser(user);
	}

	@Override
	public boolean registerUser(User user) {
		if (user.getRole() == null) {
			user.setRole(new Role(2l, 1, "user"));
		}
		try {
			client.registerUser(user);
			return true;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return false;
		}
	}

	@Override
	public User getUserByUsername(String username) {
		try {
			return client.getUser(username).getBody();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean deleteUserById(Long id) {
		try {
			client.deleteUser(id);
			return true;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return false;
		}
	}

	@Override
	public Role getRoleByLevel(int level) {
		try {
			return roleClient.getRole(level).getBody();
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return null;
		}
	}

	@Override
	public boolean doesUserAlreadyExist(String username) {
		try {
			return client.getUser(username).getStatusCode() == HttpStatus.OK ? true : false;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			return false;
		}
	}

}
