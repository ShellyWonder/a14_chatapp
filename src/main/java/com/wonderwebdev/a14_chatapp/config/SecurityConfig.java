package com.wonderwebdev.a14_chatapp.config;

import com.wonderwebdev.a14_chatapp.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer; 
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
// Enable method-level security
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig  {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(authorizeRequests ->
            authorizeRequests
                            .requestMatchers("/", "/api/auth/register", "/api/auth/login", "/public/**", "/static/**", 
                                            "/css/**", "/js/**", "/img/**", "/img/favicon/**", "img/favicon/favicon.ico" ).permitAll()
                                            // Allow access to channel view endpoint--authentication handled in ChannelViewController
                                            .requestMatchers("/channel/**").permitAll() 
                                            // Secure API endpoints
                                            .requestMatchers("/api/channels/**", "/api/channel/**","/api/channel/{channelId}/message").authenticated() 
                            .anyRequest().authenticated()
            )
            .sessionManagement(sessionManagement ->
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            );

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}