package com.Smart.config;

import java.text.Normalizer.Form;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class myConfig  {

	@Bean
	public UserDetailsService getUserDetailsService() {
		
		return new UserDetailsServiceImp();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	 @Bean
	    public DaoAuthenticationProvider getAuthenticationProvider() {
	        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setUserDetailsService(getUserDetailsService());
	        provider.setPasswordEncoder(passwordEncoder());
	        return provider;
	    }

	
	
	
	// NOT REQCURED 
	/*
	 * @Bean public AuthenticationManager authenticationManager(HttpSecurity http)
	 * throws Exception { AuthenticationManagerBuilder auth =
	 * http.getSharedObject(AuthenticationManagerBuilder.class);
	 * auth.authenticationProvider(getAuthenticationProvider()); return
	 * auth.build(); }
	 */
	/*
	 * @Bean public AuthenticationManager
	 * authenticationManager(AuthenticationConfiguration authenticationConfiguration
	 * ) throws Exception {
	 * 
	 * authenticationConfiguration.get
	 * 
	 * return authenticationConfiguration.getAuthenticationManager(); }
	 */

	/*
	 * @Bean public SecurityFilterChain filterChain(HttpSecurity http) throws
	 * Exception { http .authorizeHttpRequests((authz) -> authz
	 * .anyRequest().authenticated() ) .httpBasic(withDefaults()); return
	 * http.build(); }
	 */
	
	
	
	
	
	
	
	
	
	
	
	
	

	/*
	 * private AuthenticationProvider DaoAuthenticationProvider() { // TODO
	 * Auto-generated method stub return null; }
	 */

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests((authorize) -> authorize
	            .requestMatchers("/user/**").authenticated()
	           
	            .requestMatchers("/**").permitAll())
	        .formLogin((form) -> form 
	        	    .loginPage("/signin")
	        	    .loginProcessingUrl("/signin")
	        	    .defaultSuccessUrl("/user/index"))

	      
	                              .csrf(csrf -> csrf.disable());	   
	    
	    
	    
	       
	        
	      
	    return http.build();
              
	

	}

	

}