package de.hska.vislab.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableTurbine
public class APIGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(APIGatewayServiceApplication.class, args);
	}
	
	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}
}
