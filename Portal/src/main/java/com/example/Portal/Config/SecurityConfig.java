package com.example.Portal.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

        @Bean
        public UserDetailsManager userDetailsManager(DataSource dataSource) {
            JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
            jdbcUserDetailsManager.setUsersByUsernameQuery(
                    "select user_email,user_password,active from Login where role1_role_id=?");
            jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                    "select role_name,role_id from role where role_id=?");
            return jdbcUserDetailsManager;
        }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/view").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/Employee/{id}/view").hasAnyRole("ADMIN","MANAGER","EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/add").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/Employee/{id}/delete").hasAnyRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/Employee/{id}/update").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/").permitAll()
                        .anyRequest().authenticated()
        );
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
//        }
    }
}

