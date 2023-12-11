package com.juju.cozyformombackend3.global.config;

import com.juju.cozyformombackend3.global.auth.service.UserDetailService;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final UserDetailService userDetailService;

	private final OAuth2UserService oAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeHttpRequests(config -> config.anyRequest().permitAll());
		http.oauth2Login(oauth2Configurer -> oauth2Configurer
						.loginPage("/login")
						.successHandler(successHandler())
						.userInfoEndpoint()
						.userService(oAuth2UserService));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();

			String id = defaultOAuth2User.getAttributes().get("id").toString();
			String body = """
							{"id":"%s"}
							""".formatted(id);

			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());

			PrintWriter writer = response.getWriter();
			writer.println(body);
			writer.flush();
		});
	}

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
