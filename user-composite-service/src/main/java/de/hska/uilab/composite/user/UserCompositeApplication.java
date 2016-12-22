package de.hska.uilab.composite.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import de.hska.uilab.composite.user.restclient.RoleCoreFallback;
import de.hska.uilab.composite.user.restclient.UserCoreFallback;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
public class UserCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCompositeApplication.class, args);
	}
	
}
