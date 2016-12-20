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
}
