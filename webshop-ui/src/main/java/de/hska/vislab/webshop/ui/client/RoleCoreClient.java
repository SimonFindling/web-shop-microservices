package de.hska.vislab.webshop.ui.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.vislab.webshop.ui.model.Role;


@FeignClient(value = "role-core-service")
public interface RoleCoreClient {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<Iterable<Role>> getAllRoles();

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Long> postRole(@RequestBody(required = true) Role role);

	@RequestMapping(value = "/level/{level}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable(name = "level", required = true) int level);

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Role> getRole(@PathVariable(name = "id", required = true) long id);
}
