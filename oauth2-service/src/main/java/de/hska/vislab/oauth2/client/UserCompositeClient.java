package de.hska.vislab.oauth2.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hska.vislab.oauth2.domain.Role;
import de.hska.vislab.oauth2.domain.User;


@FeignClient(value = "user-composite-service", decode404 = true)
public interface UserCompositeClient {
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<Long> registerUser(@RequestBody(required = true) User user);

	@RequestMapping(method = RequestMethod.GET, value = "/user")
	public ResponseEntity<Iterable<User>> getAllUsers();

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}")
	public ResponseEntity<User> getUser(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(method = RequestMethod.GET, value = "/user/{id}/role")
	public ResponseEntity<Role> getRoleOfUser(@PathVariable(name = "id", required = true) long id);

	@RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}")
	public ResponseEntity<User> getUser(@PathVariable(name = "username", required = true) String username);

	@RequestMapping(method = RequestMethod.GET, value = "/user/username/{username}/role")
	public ResponseEntity<Role> getRoleOfUser(@PathVariable(name = "username", required = true) String username);

	@RequestMapping(method = RequestMethod.GET, value = "/login")
	public ResponseEntity<Role> login(@RequestParam(name = "username", required = true) String username,
			@RequestParam(name = "password", required = true) String password);

	@RequestMapping(method = RequestMethod.DELETE, value = "user/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable(required = true, name = "id") long id);
}
