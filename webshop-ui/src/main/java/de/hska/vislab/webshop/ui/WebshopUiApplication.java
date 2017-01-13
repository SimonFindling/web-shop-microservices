package de.hska.vislab.webshop.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.hska.vislab.webshop.ui.client")
public class WebshopUiApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebshopUiApplication.class, args);
	}
}
