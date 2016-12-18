package de.hska.vislab.usercore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
 
@SpringBootApplication
@EnableDiscoveryClient
public class UserCoreApplication {
	
	public static void main(String[] args) {
        SpringApplication.run(UserCoreApplication.class, args);
    }
}
