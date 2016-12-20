package de.hska.uilab.composite.user.restclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("user-core-service")
public interface UserCoreRestClient {
	@RequestMapping(method = RequestMethod.GET, value="users/{username}")
	public String getUserByUsername(@PathVariable("username") String username);
}
