package de.hska.vislab.webshop.ui.manager;

import de.hska.vislab.webshop.ui.model.Role;
import de.hska.vislab.webshop.ui.model.User;

public interface UserManager {

	public void registerUser(String username, String name, String lastname, String password, Role role);

	public void registerUser(User user);
	
	public User getUserByUsername(String username);

	public boolean deleteUserById(Long id);

	public Role getRoleByLevel(int level);

	public boolean doesUserAlreadyExist(String username);
}
