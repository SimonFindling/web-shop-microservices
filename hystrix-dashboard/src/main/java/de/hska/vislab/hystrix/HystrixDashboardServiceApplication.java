package de.hska.vislab.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
public class HystrixDashboardServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(HystrixDashboardServiceApplication.class, args);
	}
	
	@RequestMapping("/")
	public String home() {
		return "forward:/hystrix";
	}
}