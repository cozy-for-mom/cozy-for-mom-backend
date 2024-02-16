package com.juju.cozyformombackend3.global.config;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.juju.cozyformombackend3.global.auth.service.UserDetailService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final UserDetailService userDetailService;

	private final OAuth2UserService oAuth2UserService;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(config -> config.disable());
		http.authorizeHttpRequests(config -> config.anyRequest().permitAll());
		// http.oauth2Login(oauth2Configurer -> oauth2Configurer
		// 				.loginPage("/login")
		// 				.successHandler(successHandler())
		// 				.userInfoEndpoint()
		// 				.userService(oAuth2UserService));

		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler successHandler() {
		return ((request, response, authentication) -> {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User)authentication.getPrincipal();

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
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
