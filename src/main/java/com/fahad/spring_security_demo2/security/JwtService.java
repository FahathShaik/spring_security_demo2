package com.fahad.spring_security_demo2.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "tBLszIjRFjENnOj5R7GsJuh54Nnz+Ebm7O8WKDWjLHDIAsAf1h0l14DNCk73acMZOKXf0gFrIZokLt2FPFhxFpqdtPDjB3373hGIGzqYgMLYDzBwsb7npxdMupXKvgcMbzqXiyCxDsDZqCek9t9VAgR4jkM/jK9EfIohl2YgxZFZHYHLHf/ylQxaQSsh7xHxjQFUWVAR2tKQQ4IZ207pkIRu78NWGXtdxJeO3huMI/W2gFD2J6yrGrEVI7v/2Q1xssfQiGxUOJqMfA9/QVKR4LM2zZMv7iFoikO/gK80CE9Wxv//VlPmceUdWWeMFtXiIz6hhOgwa2YCztmnCn/nyt9ge9RuLqVPBsXXS3rRB/k=\n";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

        Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyData = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyData);
    }

    public String generateToken(UserDetails user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(getSignInKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails user) {
        return extractUsername(token).equals(user.getUsername()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return tokenExpiryDate(token).before(new Date());
    }

    public Date tokenExpiryDate(String token) {

        return extractClaim(token, Claims::getExpiration);
    }
}
