package de.hska.vislab.rolecore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getAllRoles() {
		return new ResponseEntity<>(repo.findAll(), HttpStatus.OK);
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postRole(@RequestBody(required = true) Role role) {
		if (!validate(role) || validate(role.id) || repo.getRoleByLevel(role.level) != null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		role = repo.save(role);

		if (validate(role.id)) {
			return new ResponseEntity<>(role.id, HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(value = "/level/{level}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable(name = "level", required = true) int level) {
		Role role = repo.getRoleByLevel(level);

		if (role != null) {
			return new ResponseEntity<>(role, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PreAuthorize("#oauth2.hasScope('server')")
	@RequestMapping(method=RequestMethod.GET,value="/{id}")
	public ResponseEntity<Role> getRole(@PathVariable(name = "id", required = true) long id) {
		Role role = repo.findOne(id);

		if (role != null) {
			return new ResponseEntity<>(role, HttpStatus.OK);
		} else {
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
