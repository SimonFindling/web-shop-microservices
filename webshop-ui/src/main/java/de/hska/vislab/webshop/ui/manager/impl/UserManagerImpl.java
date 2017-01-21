package de.hska.vislab.webshop.ui.manager.impl;

import java.util.Arrays;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.client.RestTemplate;

import de.hska.vislab.webshop.ui.client.RoleCoreClient;
import de.hska.vislab.webshop.ui.client.UserCompositeClient;
import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.model.Role;
import de.hska.vislab.webshop.ui.model.User;

public class UserManagerImpl implements UserManager {

	private static final Logger LOGGER = Logger.getLogger(UserManagerImpl.class.getCanonicalName());
	private static final String[] scopes = { "ui" };
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
			RestTemplate template = new RestTemplate();
			ResponseEntity<Long> response = template.postForEntity("http://user-composite-service:10000/register", user,
					Long.class);
			LOGGER.info("REGISTER_RESPONSE=" + response);
			LOGGER.info("REGISTER_ID=" + response.getBody());
			// client.registerUser(user);
			if (response.getStatusCode().equals(HttpStatus.OK)) {
				return true;
			}
			return false;
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

	@Override
	public boolean login(String username, String password) {
		try {
			ResourceOwnerPasswordResourceDetails passwordDetails = new ResourceOwnerPasswordResourceDetails();
			passwordDetails.setAccessTokenUri("http://user-core-service:9876/oauth/token");
			passwordDetails.setAuthenticationScheme(AuthenticationScheme.header);
			passwordDetails.setClientId("browser");
			passwordDetails.setPassword(password);
			passwordDetails.setUsername(username);
			passwordDetails.setScope(Arrays.asList(scopes));
			OAuth2RestTemplate restTemplate = new OAuth2RestTemplate(passwordDetails, new DefaultOAuth2ClientContext());
			OAuth2AccessToken token = restTemplate.getAccessToken();
			LOGGER.info("TOKEN=" + token);
			LOGGER.info("TOKEN_VALUE=" + token.getValue());
			ResponseEntity<Role> response = restTemplate.getForEntity(
					"http://user-composite-service:10000/login?username={username}&password={password}", Role.class,
					username, password);
			LOGGER.info("LOGIN_RESPONSE_ENTITY=" + response);
			LOGGER.info("LOGIN_ROLE=" + response.getBody());
			if (response.getStatusCode().equals(HttpStatus.ACCEPTED)) {
				return true;
			}
			return false;
		} catch (Exception e) {
			LOGGER.info(e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}

}
