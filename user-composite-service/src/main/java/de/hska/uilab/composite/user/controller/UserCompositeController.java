package de.hska.uilab.composite.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.hska.uilab.composite.user.restclient.CoreUserRestClient;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
public class UserCompositeController {
	
	@Autowired
	private CoreUserRestClient userClient;
	
	@RequestMapping(value="", method=RequestMethod.GET)
	public ResponseEntity<String> info() {
		String res = userClient.getUserByUsername("penner");
		return new ResponseEntity<String>(res, HttpStatus.OK);
	}
}
