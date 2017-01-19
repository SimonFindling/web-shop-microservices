package de.hska.uilab.composite.user.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.uilab.composite.user.model.User;

@FeignClient(value = "user-core-service", fallback = UserCoreFallback.class)
public interface UserCoreRestClient {
	@RequestMapping(method = RequestMethod.GET, value="/username/{username}")
	public ResponseEntity<User> getUser(@PathVariable("username") String username);
	
//	@RequestMapping(method = RequestMethod.DELETE, value="/username/{username}")
//	public ResponseEntity<User> deleteUser(@PathVariable("username") String username);
	
	@RequestMapping(method = RequestMethod.GET, value="/")
	public ResponseEntity<Iterable<User>> getAllUsers();
	
	@RequestMapping(method = RequestMethod.POST, value ="/")
	public ResponseEntity<Long> postUser(@RequestBody(required = true) User user);
	
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") long id);
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable("id") long id);

}
