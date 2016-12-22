package de.hska.uilab.composite.user.restclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.uilab.composite.user.model.User;

public class UserCoreFallback implements UserCoreRestClient {

	private static final String FALLBACK_TEXT = "user-core-service not available";
	
	@Override
	public ResponseEntity<User> getUser(String username) {
		ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Iterable<User>> getAllUsers() {
		ResponseEntity<Iterable<User>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Long> postUser(User user) {
		ResponseEntity<Long> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<User> getUser(long id) {
		ResponseEntity<User> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Void> deleteUser(long id) {
		ResponseEntity<Void> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

}
