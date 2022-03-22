package com.shahintraining.customer.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shahintraining.customer.config.JwtConfigureProperties;
import com.shahintraining.customer.exception.InvalidTokenPrefixException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author - sh.khalajestani
 * created on - 3/9/2022
 */

@RequiredArgsConstructor
@Service
@Slf4j
public class JwtUtilityService {

    private final JwtConfigureProperties properties;

    private final ObjectMapper mapper = new ObjectMapper();

    private final Calendar cal = Calendar.getInstance();

    public String generateAccessToken(UserDetails userDetails, String requestUri) {
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, properties.getAccessTokenExpireTimeInMinutes());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withClaim("roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).toList())
                .withIssuer(requestUri)
                .withIssuedAt(new Date())
                .withExpiresAt(cal.getTime())
                .sign(getAlgorithm());
    }


    public void generateTokens(UserDetails userDetails,
            HttpServletRequest request , HttpServletResponse response) throws IOException {
        String requestURI = request.getRequestURI();
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("access_token", generateAccessToken(userDetails,requestURI));
        responseMap.put("refresh_token", generateRefreshToken(userDetails,requestURI));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        mapper.writeValue(response.getOutputStream(),responseMap);
    }

    public String generateRefreshToken(UserDetails userDetails, String requestUri) {
        cal.setTime(new Date());
        cal.add(Calendar.DATE, properties.getRefreshTokenExpireTimeInDays());
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(requestUri)
                .withIssuedAt(new Date())
                .withExpiresAt(cal.getTime())
                .sign(getAlgorithm());
    }

    public List<? extends GrantedAuthority> extractAuthorities(String token) {
        DecodedJWT decodedJWT = verifyToken(token);
        return decodedJWT.getClaim("roles").asList(String.class).stream().map(SimpleGrantedAuthority::new)
                .toList();
    }

    public String extractUsernameFromToken(String token) {
        return verifyToken(token).getSubject();
    }


    private DecodedJWT verifyToken(String token) {
        if (!checkAuthorizationHeader(token)) {
            log.error("token prefix is not present or invalid");
            throw new InvalidTokenPrefixException("token prefix is not present or invalid");
        }
        String tokenWithoutPrefix = token.substring(properties.getTokenPrefix().length());
        return JWT.require(getAlgorithm()).build()
                .verify(tokenWithoutPrefix);
    }


    private Algorithm getAlgorithm() {
        return Algorithm.HMAC512(properties.getSecretKey());
    }

    public boolean checkAuthorizationHeader(String authorizationHeader) {
        return StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(properties.getTokenPrefix());
    }

}
