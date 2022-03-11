package com.shahintraining.customer.config;

import com.shahintraining.customer.enums.AllowedUrls;
import com.shahintraining.customer.filter.CustomerAuthentication;
import com.shahintraining.customer.filter.JwtFilter;
import com.shahintraining.customer.service.CustomerService;
import com.shahintraining.customer.service.JwtUtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collection;
import java.util.List;

/**
 * author - sh.khalajestani
 * created on - 3/9/2022
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomerService customerService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtilityService jwtUtilityService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customerService).passwordEncoder(passwordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter =
                new CustomerAuthentication(authenticationManagerBean(),jwtUtilityService);
        OncePerRequestFilter jwtFilter = new JwtFilter(jwtUtilityService);
        List<String> allAllowedUrls = AllowedUrls.getAllAllowedUrls();
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers(HttpMethod.POST,allAllowedUrls.toArray(new String[0])).permitAll()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(authenticationFilter)
                .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
