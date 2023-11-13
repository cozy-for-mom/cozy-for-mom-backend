package com.juju.cozyformombackend3.global.config;

import com.juju.cozyformombackend3.global.auth.service.UserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final UserDetailService userDetailService;


	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http
						.authorizeRequests()
						//						.requestMatchers("/login", "/signup", "/user").permitAll()
						.requestMatchers("/**").permitAll()
						.anyRequest().authenticated()
						.and()
						.formLogin()
						.loginPage("/login")
						.defaultSuccessUrl("/")
						.and()
						.logout()
						.logoutSuccessUrl("/login")
						.invalidateHttpSession(true)
						.and()
						.csrf().disable()
						.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder)
					throws Exception {
		return http.getSharedObject(AuthenticationManagerBuilder.class)
						.userDetailsService(userDetailService)
						.passwordEncoder(bCryptPasswordEncoder)
						.and().build();
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
