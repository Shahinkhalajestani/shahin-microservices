package com.shahintraining.customer.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.customer.domain.UsernamePasswordRequest;
import com.shahintraining.customer.service.JwtUtilityService;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

public class CustomerAuthentication extends UsernamePasswordAuthenticationFilter {

    private final JwtUtilityService jwtUtilityService;
    private final AuthenticationManager authenticationManager;
    private final ObjectMapper mapper = new ObjectMapper();

    public CustomerAuthentication(AuthenticationManager authenticationManager,
                                  JwtUtilityService jwtUtilityService) {
        super(authenticationManager);
        this.jwtUtilityService = jwtUtilityService;
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        UsernamePasswordRequest usernamePasswordRequest =
                mapper.readValue(request.getInputStream(), UsernamePasswordRequest.class);
       return authenticationManager
               .authenticate(new UsernamePasswordAuthenticationToken(usernamePasswordRequest.username()
                , usernamePasswordRequest.password()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        jwtUtilityService.generateTokens(userDetails,request,response);
    }
}
