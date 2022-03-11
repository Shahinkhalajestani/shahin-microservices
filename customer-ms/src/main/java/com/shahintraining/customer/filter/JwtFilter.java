package com.shahintraining.customer.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.customer.enums.AllowedUrls;
import com.shahintraining.customer.service.JwtUtilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtilityService jwtUtilityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (AllowedUrls.getAllAllowedUrls().contains(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
            if (jwtUtilityService.checkAuthorizationHeader(authorizationHeader)) {
                try {
                    String username = jwtUtilityService.extractUsernameFromToken(authorizationHeader);
                    List<? extends GrantedAuthority> authorities = jwtUtilityService
                            .extractAuthorities(authorizationHeader);
                    SecurityContextHolder.getContext()
                            .setAuthentication(new UsernamePasswordAuthenticationToken(username, null, authorities));
                    filterChain.doFilter(request, response);
                } catch (Exception ex) {
                    Map<String, String> errors = new HashMap<>();
                    response.setHeader("error", ex.getMessage());
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    errors.put("error_message", ex.getMessage());
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), errors);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
