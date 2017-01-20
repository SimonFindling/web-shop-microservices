package de.hska.vislab.usercore.security;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import de.hska.vislab.usercore.dao.UserRepository;
import de.hska.vislab.usercore.model.User;

public class UserCoreDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repo.getUserByUsername(username);
		Logger.getLogger(getClass().getName()).info("USER=" + user);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return user;
	}

}
