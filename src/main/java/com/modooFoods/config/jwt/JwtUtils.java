package com.modooFoods.config.jwt;

import com.modooFoods.exception.CustomException;
import com.modooFoods.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final UserDetailsService userDetailsService;

    private final long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 3;
    private final long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 60 * 24 * 7; // 7 days
    private final String JWT_SECRET = "secret";
    private final SignatureAlgorithm SIGNATURE_ALG = SignatureAlgorithm.HS256;

    public String createAccessToken(Member member) {
        return createToken(member, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(Member member) {
        return createToken(member, REFRESH_TOKEN_EXPIRATION_TIME);
    }

    private String createToken(Member member, long expirationTime) {
        final Claims claims = Jwts.claims();
        claims.put("member_id", member.getId());
        claims.put("name", member.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SIGNATURE_ALG, JWT_SECRET)
                .compact();
    }

    public boolean validateAccessToken(String token) {
        return validateToken(token);
    }

    public boolean validateRefreshToken(String token) {
        return validateToken(token);
    }

    private boolean validateToken(String token) {
        final Claims claims = getAllClaims(token);
        final Date exp = claims.getExpiration();
        return exp.after(new Date());
    }

    public String parseUserId(String token) {
        return getAllClaims(token)
                .get("member_id")
                .toString();
    }

    public Authentication getAuthentication(String memberId) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    private Claims getAllClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(JWT_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "토큰 정보가 올바르지 않습니다.");
        }
    }
}
