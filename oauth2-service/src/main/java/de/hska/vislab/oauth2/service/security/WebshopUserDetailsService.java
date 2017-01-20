package de.hska.vislab.oauth2.service.security;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hska.vislab.oauth2.client.UserCompositeClient;
import de.hska.vislab.oauth2.domain.User;

@Service
public class WebshopUserDetailsService implements UserDetailsService {
	
	private static final Logger LOGGER = Logger.getLogger(WebshopUserDetailsService.class.getCanonicalName());

	@Autowired
	private UserCompositeClient client;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ResponseEntity<User> user;
		try {
			user = client.getUser(username);

		}
		catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			user = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (user.getStatusCode() != HttpStatus.OK) {
			throw new UsernameNotFoundException(username);
		}

		return user.getBody();
	}
}