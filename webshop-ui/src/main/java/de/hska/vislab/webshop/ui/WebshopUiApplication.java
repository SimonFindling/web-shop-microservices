package de.hska.vislab.webshop.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;

import de.hska.vislab.webshop.ui.manager.CategoryManager;
import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.manager.impl.CategoryManagerImpl;
import de.hska.vislab.webshop.ui.manager.impl.ProductManagerImpl;
import de.hska.vislab.webshop.ui.manager.impl.UserManagerImpl;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "de.hska.vislab.webshop.ui.client")
@ComponentScan(basePackages = "de.hska.vislab.webshop.ui")
public class WebshopUiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebshopUiApplication.class, args);
	}

	@Bean
	public ResourceBundleMessageSource messageSource() {
		ResourceBundleMessageSource source = new ResourceBundleMessageSource();
		source.setBasename("static/i18n/messages");
		source.setUseCodeAsDefaultMessage(true);
		return source;
	}
	
	@Bean
	public UserManager getUserManager() {
		return new UserManagerImpl();
	}
	
	@Bean
	public CategoryManager getCategoryManager() {
		return new CategoryManagerImpl();
	}
	
	@Bean
	public ProductManager getProductManager() {
		return new ProductManagerImpl();
	}
	
	@Bean
	public ValidationService getValidationService() {
		return new ValidationService();
	}
	
	@Bean
	public MessageBundleService getMessageBundleService() {
		return new MessageBundleService();
	}
}
