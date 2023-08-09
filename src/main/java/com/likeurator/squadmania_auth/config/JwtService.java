package com.likeurator.squadmania_auth.config;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {        
    //해당 비밀 키는 클라이언트에서 생성해야 하는 듯.
    //혹은 gradle에 jwt.secret:
    //그리고 securityConfig에 객체 생성 후 @Value("${jwt.secret}")
    private final String SECRET_KEY = "472D4B6150645367566B58703273357638792F423F4528482B4D625165546857";
    private final Long ACCESS_TOKEN_EXPIRATION = 2000 * 60 * 24L;
    private final Long REFRESH_TOKEN_EXPIRATION = 30000 * 60 * 24L;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public<T> T extractClaim(String token, Function<Claims, T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateAccessToken(UserDetails userDetails){
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String generateRefreshToken(UserDetails userDetails){
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken(Map <String, Object> extractClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String generateRefreshToken(Map <String, Object> extractClaims, UserDetails userDetails){
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && (!isTokenExpired(token) && token!=null);  
    }

    public boolean isTokenIssuer(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && (!isTokenReIssuer(token) && token!=null);  
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    
    private boolean isTokenReIssuer(String token){
        Long reIssuer = extractExpiration(token).getTime() - (extractExpiration(token).getTime()/10L);
        return extractIssuedAt(token).after(new Date(reIssuer));
    }

    private Date extractExpiration(String token){
        return extractClaim(token, Claims::getExpiration);
    }

    private Date extractIssuedAt(String token){
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
            .getBody();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}  
