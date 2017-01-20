package de.hska.vislab.oauth2.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import de.hska.vislab.oauth2.client.UserCompositeClient;
import de.hska.vislab.oauth2.domain.User;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getCanonicalName());

	@Autowired
	private UserCompositeClient client;

	@Override
	public void create(User user) {
		try {
			User existing = client.getUser(user.getUsername()).getBody();
			Assert.isNull(existing, "user already exists: " + user.getUsername());

			ResponseEntity<Long> response = client.registerUser(user);
			if (response.getStatusCode().equals(HttpStatus.CREATED)) {
				user = client.getUser(response.getBody()).getBody();
				LOGGER.info("new user has been created: " + user.getUsername());
				return;
			}
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
		}
		LOGGER.info("User creation failed");
	}
}
