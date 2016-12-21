package de.hska.uilab.composite.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hska.uilab.composite.user.model.Role;
import de.hska.uilab.composite.user.model.User;
import de.hska.uilab.composite.user.restclient.RoleCoreRestClient;
import de.hska.uilab.composite.user.restclient.UserCoreRestClient;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserCompositeController {

	@Autowired
	private UserCoreRestClient userClient;

	@Autowired
	private RoleCoreRestClient roleClient;

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<Long> registerUser(@RequestBody(required = true) User user) {
		if (validate(user) && validate(user.username) && validate(user.password) && !validate(user.id)) {
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

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<Role> login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password) {

		if (validate(username) && validate(password)) {
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
