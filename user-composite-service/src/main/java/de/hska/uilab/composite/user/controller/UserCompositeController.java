package de.hska.uilab.composite.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hska.uilab.composite.user.model.Role;
import de.hska.uilab.composite.user.model.User;
import de.hska.uilab.composite.user.restclient.RoleCoreFallback;
import de.hska.uilab.composite.user.restclient.RoleCoreRestClient;
import de.hska.uilab.composite.user.restclient.UserCoreFallback;
import de.hska.uilab.composite.user.restclient.UserCoreRestClient;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserCompositeController {

	@Autowired
	private UserCoreRestClient userClient;

	@Autowired
	private RoleCoreRestClient roleClient;

	@Bean
	public RoleCoreFallback roleCoreFallback() {
		return new RoleCoreFallback();
	}
	
	@Bean
	public UserCoreFallback userCoreFallback() {
		return new UserCoreFallback();
	}
	
//	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<Long> registerUser(@RequestBody(required = true) User user) {
		if (!validate(user) || !validate(user.username) || !validate(user.password) || validate(user.id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		user.roleID = 2l;
		ResponseEntity<Long> userResponse = userClient.postUser(user);
		if (userResponse.getStatusCode() == HttpStatus.OK) {
			return new ResponseEntity<>(userResponse.getBody(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		}
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/user")
	public ResponseEntity<Iterable<User>> getAllUsers() {
		ResponseEntity<Iterable<User>> userResponse = userClient.getAllUsers();
		Iterable<User> users = userResponse.getBody();
		for (User u : users) {
			ResponseEntity<Role> roleResponse = roleClient.getRole(u.roleID);
			if (roleResponse.getStatusCode() == HttpStatus.OK) {
				u.role = roleResponse.getBody();
			}
		}

		return new ResponseEntity<>(users, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable(name = "id", required = true) long id) {
		if (!validate(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<User> userResponse = userClient.getUser(id);

		if (userResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(userResponse.getStatusCode());
		}
		User user = userResponse.getBody();

		ResponseEntity<Role> roleResponse = roleClient.getRole(user.roleID);

		if (roleResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(roleResponse.getStatusCode());
		}

		user.role = roleResponse.getBody();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}/role")
	public ResponseEntity<Role> getRoleOfUser(@PathVariable(name = "id", required = true) long id) {
		ResponseEntity<User> response = getUser(id);

		if (response.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(response.getStatusCode());
		}

		return new ResponseEntity<>(response.getBody().role, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}")
	public ResponseEntity<User> getUser(@PathVariable(name = "username", required = true) String username) {
		if (!validate(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<User> userResponse = userClient.getUser(username);

		if (userResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(userResponse.getStatusCode());
		}
		User user = userResponse.getBody();

		ResponseEntity<Role> roleResponse = roleClient.getRole(user.roleID);

		if (roleResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(roleResponse.getStatusCode());
		}

		user.role = roleResponse.getBody();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}/role")
	public ResponseEntity<Role> getRoleOfUser(@PathVariable(name = "username", required = true) String username) {
		ResponseEntity<User> response = getUser(username);

		if (response.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(response.getStatusCode());
		}

		return new ResponseEntity<>(response.getBody().role, HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<Role> login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {

		if (!validate(username) && !validate(password)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		ResponseEntity<User> userResponse = userClient.getUser(username);

		if (userResponse.getStatusCode() != HttpStatus.OK) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		User user = userResponse.getBody();

		if (user.password.equals(password)) {
			ResponseEntity<Role> roleResponse = roleClient.getRole(user.roleID);
			Role role = roleResponse.getBody();
			return new ResponseEntity<>(role, HttpStatus.ACCEPTED);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}
	
	@PreAuthorize("#oauth2.hasScope('server') or #oauth2.hasScope('ui')")
	@RequestMapping(method = RequestMethod.DELETE, value = "user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(required = true, name = "id") long id) {
		ResponseEntity<Void> response = userClient.deleteUser(id);
		return new ResponseEntity<>(response.getStatusCode());
	}

	private boolean validate(Object obj) {
		return obj != null;
	}

	private boolean validate(Long id) {
		return id != null && id > 0;
	}

	private boolean validate(String str) {
		return str != null && !str.isEmpty();
	}
}
