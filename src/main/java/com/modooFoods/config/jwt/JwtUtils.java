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

    private final long EXP_TIME = 1000L * 60 * 3;
    private final String JWT_SECRET = "secret";
    private final SignatureAlgorithm SIGNATURE_ALG = SignatureAlgorithm.HS256;


    public String createToken(Member member) {
        final Claims claims = Jwts.claims();
        claims.put("member_id", member.getId());
        claims.put("name", member.getName());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXP_TIME))
                .signWith(SIGNATURE_ALG, JWT_SECRET)
                .compact();
    }

    public boolean validation(String token) {
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

    public Claims getAllClaims(String token) {
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
