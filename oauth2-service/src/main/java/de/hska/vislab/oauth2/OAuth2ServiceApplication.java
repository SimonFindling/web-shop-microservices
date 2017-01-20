package de.hska.vislab.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import de.hska.vislab.oauth2.service.security.WebshopUserDetailsService;

@SpringBootApplication
@EnableResourceServer
@EnableDiscoveryClient
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class OAuth2ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OAuth2ServiceApplication.class, args);
	}
	
	@Configuration
	@EnableAuthorizationServer
	protected static class OAuth2AuthorizationConfiguration extends AuthorizationServerConfigurerAdapter {
		private static final String PASSWORD = "Welcome1";
		
		private TokenStore tokenStore = new InMemoryTokenStore();
		
		@Autowired
		@Qualifier("authenticationManagerBean")
		private AuthenticationManager authenticationManager;
		
		@Autowired
		private WebshopUserDetailsService userDetailsService;

		@Override
		public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
			 // @formatter:off
			 clients.inMemory().withClient("webshop-ui").authorizedGrantTypes("refresh_token",
			 "password").scopes("ui")
			 .and().withClient("user-core-service").secret(PASSWORD)
			 .authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server").and()
			 .withClient("user-composite-service").secret(PASSWORD).authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server").and()
			 .withClient("role-core-service").secret(PASSWORD)
			 .authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server").and()
			 .withClient("product-core-service").secret(PASSWORD)
			 .authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server").and()
			 .withClient("product-category-composite-service").secret(PASSWORD)
			 .authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server").and()
			 .withClient("category-core-service").secret(PASSWORD)
			 .authorizedGrantTypes("client_credentials",
			 "refresh_token").scopes("server");
			 // @formatter:on
		}
		
		@Override
		public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
			endpoints.tokenStore(tokenStore).authenticationManager(authenticationManager)
					.userDetailsService(userDetailsService);
		}
		
		@Override
		public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
			oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		}
	}

	@Configuration
	@EnableWebSecurity
	protected static class webSecurityConfig extends WebSecurityConfigurerAdapter {
		
		@Autowired
		private WebshopUserDetailsService userDetailsService;
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http.authorizeRequests().anyRequest().authenticated().and().csrf().disable();
			// @formatter:on
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);		}

		@Override
		@Bean
		public AuthenticationManager authenticationManagerBean() throws Exception {
			return super.authenticationManagerBean();
		}
	}
}