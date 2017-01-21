package de.hska.vislab.webshop.ui.manager;

import de.hska.vislab.webshop.ui.model.Role;
import de.hska.vislab.webshop.ui.model.User;

public interface UserManager {

	public boolean registerUser(String username, String name, String lastname, String password, Role role);

	public boolean registerUser(User user);
	
	public User getUserByUsername(String username);

	public boolean deleteUserById(Long id);

	public Role getRoleByLevel(int level);

	public boolean doesUserAlreadyExist(String username);
	
	public boolean login(String username, String password);
}
