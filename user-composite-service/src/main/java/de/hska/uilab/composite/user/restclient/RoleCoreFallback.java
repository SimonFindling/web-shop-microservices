package de.hska.uilab.composite.user.restclient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import de.hska.uilab.composite.user.model.Role;

public class RoleCoreFallback implements RoleCoreRestClient {

	private static final String FALLBACK_TEXT = "role-core-service not found";
	
	@Override
	public ResponseEntity<Iterable<Role>> getAllRoles() {
		ResponseEntity<Iterable<Role>> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Long> postRole(Role role) {
		ResponseEntity<Long> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Role> getRole(int level) {
		ResponseEntity<Role> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

	@Override
	public ResponseEntity<Role> getRole(long id) {
		ResponseEntity<Role> response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		response.getHeaders().add("Fallback", FALLBACK_TEXT);
		return response;
	}

}
