package helha.trocappbackend.configuration;

import helha.trocappbackend.security.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class SpringSecurityConfig {
    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    JWTFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> {
                    authorizeRequests.requestMatchers("/sports/all").hasRole("ADMIN");
                    authorizeRequests.requestMatchers("/sports").hasRole("USER");
                    authorizeRequests.requestMatchers("/swagger-ui/**","/v3/api-docs","/user","/auth/login").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                }).addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user1 = User.builder()
//                        .username("user")
//                        .password(passwordEncoder().encode("user"))
//                        .roles("USER").build();
//        UserDetails user2 = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("USER","ADMIN").build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }

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
