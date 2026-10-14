package com.example.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http.authorizeHttpRequests(authorizeRequests->
                authorizeRequests.requestMatchers("/admin/**").hasRole("Admin")
                                .requestMatchers("/user/**").hasAnyRole("User","Admin")
                        .anyRequest().authenticated());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
        UserDetails user1 = User.withUsername("user1")
                .password("password1")
                .roles("Seller")
                .build();

        UserDetails user2 = User.withUsername("user2")
                .password("password2")
                .roles("User")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("adminPassword")
                .roles("Admin")
                .build();

       // return new InMemoryUserDetailsManager(user1,user2,admin); -- commenting for db connections
        JdbcUserDetailsManager userDetailsManager = new
                JdbcUserDetailsManager(dataSource);

        userDetailsManager.createUser(user1);
        userDetailsManager.createUser(user2);
        userDetailsManager.createUser(admin);

        return userDetailsManager;
    }
}
