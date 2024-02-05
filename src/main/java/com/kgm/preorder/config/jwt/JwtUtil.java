package com.kgm.preorder.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "64461f01e1af406da538b9c48d801ce59142452199ff112fb5404c8e7e98e3ff";

    public static String getMemberIdFromToken(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            log.info("claims.getSubject() : {}", claims.getSubject());
            return claims.getSubject();  // Assuming subject contains user ID
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
