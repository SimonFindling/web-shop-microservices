package de.hska.vislab.webshop.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import de.hska.vislab.webshop.ui.manager.CategoryManager;
import de.hska.vislab.webshop.ui.manager.ProductManager;
import de.hska.vislab.webshop.ui.manager.UserManager;
import de.hska.vislab.webshop.ui.manager.impl.CategoryManagerImpl;
import de.hska.vislab.webshop.ui.manager.impl.ProductManagerImpl;
import de.hska.vislab.webshop.ui.manager.impl.UserManagerImpl;
import de.hska.vislab.webshop.ui.util.MessageBundleService;
import de.hska.vislab.webshop.ui.util.ValidationService;
import feign.RequestInterceptor;

@SpringBootApplication
@EnableDiscoveryClient
@EnableOAuth2Client
@EnableFeignClients(basePackages = "de.hska.vislab.webshop.ui.client")
@ComponentScan(basePackages = "de.hska.vislab.webshop.ui")
public class WebshopUiApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebshopUiApplication.class, args);
	}

	@Bean
	@ConfigurationProperties(prefix = "security.oauth2.client")
	public ClientCredentialsResourceDetails clientCredentialsResourceDetails() {
		return new ClientCredentialsResourceDetails();
	}

	@Bean
	public RequestInterceptor oauth2FeignRequestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), clientCredentialsResourceDetails());
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
