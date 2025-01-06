package helha.trocappbackend.configuration;

import helha.trocappbackend.security.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTFilter jwtFilter;

    @Bean

    public SecurityFilterChain SecurityFilterChain(final HttpSecurity http) throws Exception {
        return http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers(
                            "/api/categories", "/api/categories/{id}", "/users/all","/users/{id}","/users/update-user",
                            "/users/{userId}/roles/{roleId}","/users/roles","/users/getAllRoles","/users/{userId}/roles",
                            "/users/search","/users/{userId}/items","/users/{userId}/block","/ratings","/ratings/add",
                            "ratings/received/{userId}","/ratings/posted/{userId}","/ratings/{ratingId}", "/ratings/average/{userId}",
                            "/api/gdpr/user/{userId}","/api/gdpr/{gdprRequestId}","/api/gdpr/{userId}/deactivate","/api/gdpr/pending",
                            "/api/gdpr/processed","/api/gdpr/{id}/processed", "/items", "/items/available","/items/user/{id}",
                            "/items/{id}", "/exchanges", "/exchanges/user/{id}", "/exchanges/{id}").hasAnyRole("admin", "user");
                    authorizeRequests.requestMatchers("/api/categories/{id}","/exchanges", "/exchanges/user/{id}", "/exchanges/{id}").hasRole("admin");
                    authorizeRequests.requestMatchers("/api/gdpr","/exchanges", "/exchanges/user/{id}", "/exchanges/{id}").hasRole("user");
                    authorizeRequests.requestMatchers("/swagger-ui/**","/v3/api-docs", "users", "/auth/login", "/auth/create_account", "/items/available","/addresses/{id}","api/categories/{id}","/items/{id}").permitAll();
                    authorizeRequests.anyRequest().authenticated();

                }).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuiler = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuiler.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
        return authenticationManagerBuiler.build();
    }
}
