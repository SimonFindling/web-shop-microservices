package de.hska.vislab.webshop.ui.manager.impl;

import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.model.Role;
import de.hska.vislab.webshop.ui.model.User;

public class UserManagerImpl implements UserManager {

	@Override
	public void registerUser(String username, String name, String lastname, String password, Role role) {
		// TODO Auto-generated method stub

	}

	@Override
	public void registerUser(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteUserById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Role getRoleByLevel(int level) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean doesUserAlreadyExist(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}
