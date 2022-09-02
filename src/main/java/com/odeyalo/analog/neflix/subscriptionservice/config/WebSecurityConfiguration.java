package com.odeyalo.analog.neflix.subscriptionservice.config;

import com.odeyalo.analog.neflix.subscriptionservice.config.security.JwtTokenExtractorFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration {
    private final JwtTokenExtractorFilter filter;

    public WebSecurityConfiguration(JwtTokenExtractorFilter filter) {
        this.filter = filter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf().disable().cors().disable().authorizeRequests()
                .antMatchers("/webhooks/**").permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .anyRequest().authenticated()
                .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
