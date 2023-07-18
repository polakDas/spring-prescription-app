package com.medical.prescriptionapplication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf((csrf) -> csrf.disable())
                                .headers((header) -> header
                                                .frameOptions(Customizer.withDefaults())
                                                .disable())
                                .httpBasic(Customizer.withDefaults())
                                .authorizeHttpRequests((request) -> request
                                                .requestMatchers("/h2-console/**", "/static/**")
                                                .permitAll()
                                                .anyRequest().authenticated())
                                .formLogin(Customizer.withDefaults())
                                .logout((logout) -> logout
                                                .permitAll());
                return http.build();
        }

        // @Bean
        // public InMemoryUserDetailsManager userDetailsService() {
        // UserDetails user1 = User.withUsername("user1")
        // .password(passwordEncoder().encode("user1Pass"))
        // .roles("USER")
        // .build();
        // UserDetails user2 = User.withUsername("user2")
        // .password(passwordEncoder().encode("user2Pass"))
        // .roles("USER")
        // .build();
        // UserDetails admin = User.withUsername("admin")
        // .password(passwordEncoder().encode("adminPass"))
        // .roles("ADMIN")
        // .build();
        // return new InMemoryUserDetailsManager(user1, user2, admin);
        // }

        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
                DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
                daoAuthenticationProvider.setUserDetailsService(userDetailsService);

                return new ProviderManager(daoAuthenticationProvider);
        }
}
