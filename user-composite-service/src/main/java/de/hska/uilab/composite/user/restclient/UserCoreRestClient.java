package de.hska.uilab.composite.user.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.uilab.composite.user.model.User;

@FeignClient("user-core-service")
public interface UserCoreRestClient {
	@RequestMapping(method = RequestMethod.GET, value="/user/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username);
	
	@RequestMapping(method = RequestMethod.POST, value ="/user")
	public ResponseEntity<Long> postUser(@RequestBody(required = true) User user);
	
	@RequestMapping(method = RequestMethod.GET, value = "user/{id}")
	public ResponseEntity<Long> getUser(@PathVariable("id") long id);
	
	@RequestMapping(method = RequestMethod.DELETE, value = "user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id);

}
