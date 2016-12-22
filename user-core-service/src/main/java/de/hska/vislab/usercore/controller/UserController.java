package de.hska.vislab.usercore.controller;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.hska.vislab.usercore.dao.UserRepository;
import de.hska.vislab.usercore.model.User;

@RestController
public class UserController {

	private final static Logger LOGGER = Logger.getLogger(UserController.class.getSimpleName());

	@Autowired
	private UserRepository repo;

	@RequestMapping(method = RequestMethod.POST, value = "/")
	public ResponseEntity<Long> postUser(@RequestBody(required = true) User user) {
		if (validate(user.id) || !validate(user.roleID) || repo.getUserByUsername(user.username) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		user = repo.save(user);
		
		if (validate(user.id)) {
			return ResponseEntity.ok(user.id);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}

	@RequestMapping(method = RequestMethod.GET, value = "/")
	public ResponseEntity<Iterable<User>> getAllUsers() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/username/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username) {
		if (!validate(username)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User user = repo.getUserByUsername(username);
		
		if (validate(user) && validate(user.id)) {
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		if (!validate(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User user = repo.findOne(id);
		
		if (validate(user) && validate(user.id)) {
			return new ResponseEntity<>(user,HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) {
		if (!validate(id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User user = repo.findOne(id);
		
		if (validate(user) && validate(user.id)) {
			repo.delete(user);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	private boolean validate(Object obj) {
		return obj != null;
	}
	
	private boolean validate(String str) {
		return str != null && !str.isEmpty();
	}
	
	private boolean validate(Long id) {
		return id != null && id > 0;
	}
}
