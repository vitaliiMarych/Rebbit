package com.Rebbit.Configurations;

import com.Rebbit.Repo.UserDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class OauthConfig {
    @Autowired
    private UserDetailsRepo userDetailsRepo;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/","/js/**","/error/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .oauth2Login(oauth2Login ->
                        oauth2Login.successHandler(new CustomAuthenticationSuccessHandler(userDetailsRepo)))
                .csrf().disable();
        return http.build();
    }

}
