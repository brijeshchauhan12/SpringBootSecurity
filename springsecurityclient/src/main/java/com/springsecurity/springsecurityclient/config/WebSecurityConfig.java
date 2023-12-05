package com.springsecurity.springsecurityclient.config;

import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    private static  final String [] WHITE_LIST_URLS={
            "/hello",
            "/register",
            "/verifyRegistration",
            "/resetPassword",
            "/savePassword",
            "/changePassword"

    };
    @Bean
    public PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder(11);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
                  .cors()
                  .and()
                  .csrf()
                  .disable()
                  .authorizeHttpRequests()
                  .requestMatchers(WHITE_LIST_URLS).permitAll()
                  .requestMatchers("/api/**").authenticated()

                  .and()
                  .oauth2Login(oauth2Login->
                          oauth2Login.loginPage("/oauth2/authorization/api-client-oidc"))
                  .oauth2Client(Customizer.withDefaults());
          return http.build();
    }
}
