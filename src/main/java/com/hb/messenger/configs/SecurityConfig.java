package com.hb.messenger.configs;


import com.hb.messenger.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

  @Value("${role:user}")
  String userRole;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http,
      AuthenticationProvider authenticationProvider, JwtAuthFilter jwtAuthFilter) throws Exception {
    return http
        .csrf(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests(
            auth -> auth.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/login").permitAll()
                .requestMatchers(HttpMethod.POST, "/user").permitAll()
                .anyRequest().authenticated()
        )
        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
        .authenticationProvider(authenticationProvider)
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .build();
  }


  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {
    return config.getAuthenticationManager();
  }
}