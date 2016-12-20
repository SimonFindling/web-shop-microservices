package de.hska.vislab.rolecore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.hska.vislab.rolecore.dao.RoleRepository;
import de.hska.vislab.rolecore.model.Role;

@RestController
public class RoleController {

	@Autowired
	private RoleRepository repo;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getAllRoles() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postRole(@RequestBody(required = true) Role role) {
		if (validate(role) && !validate(role.id)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		role = repo.save(role);

		if (validate(role.id)) {
			return new ResponseEntity<>(role.id, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(value = "/{level}", method = RequestMethod.GET) 
	public ResponseEntity<Role> getRole(@PathVariable(name="level", required = true) int level) {
		Role role = repo.getRoleByLevel(level);
		
		if (role != null) {
		return new ResponseEntity<>(role, HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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
