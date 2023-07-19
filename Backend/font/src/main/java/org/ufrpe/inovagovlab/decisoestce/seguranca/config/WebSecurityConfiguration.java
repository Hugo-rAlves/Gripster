package org.ufrpe.inovagovlab.decisoestce.seguranca.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.ufrpe.inovagovlab.decisoestce.model.Role;
import org.ufrpe.inovagovlab.decisoestce.seguranca.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        authz -> authz
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/extractor/**").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                        )
                .sessionManagement(sessm -> sessm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

}
