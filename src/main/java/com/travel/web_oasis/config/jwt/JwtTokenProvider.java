package com.travel.web_oasis.config.jwt;

import com.travel.web_oasis.config.oauth.service.PrincipalDetailsService;
import groovy.util.logging.Slf4j;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtTokenProvider {
    private final PrincipalDetailsService principalDetailsService;

    @Value("${jwt.token.key}")
    private String secretKey;

    //토큰 유효시간 설정
    private Long tokenValidTime = 240 * 60 * 1000L;

    //secretkey를 미리 인코딩 해줌.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    //JWT 토큰 생성
    public String createToken(String email, String provider) {

        //payload 설정
        //registered claims
        Date now = new Date();
        Claims claims = Jwts.claims()
                .setSubject("access_token") //토큰제목
                .setIssuedAt(now) //발행시간
                .setExpiration(new Date(now.getTime() + tokenValidTime)); // 토큰 만료기한

        //private claims
        claims.put("email", email); // 정보는 key - value 쌍으로 저장.
        claims.put("provider", provider);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT") //헤더
                .setClaims(claims) // 페이로드
                .signWith(SignatureAlgorithm.HS256, secretKey)  // 서명. 사용할 암호화 알고리즘과 signature 에 들어갈 secretKey 세팅
                .compact();
    }

    //JWT 토큰에서 인증정보 조회
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = principalDetailsService.loadUserByUsername(this.getUserPk(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return (String) Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("email");
    }

    // Request의 Cookie 에서 token 값을 가져옵니다. "Authorization" : "TOKEN값'
    public String resolveToken(HttpServletRequest request) {
        Cookie jwtCookie = WebUtils.getCookie(request, "JWT");

        if (jwtCookie != null) {
            return jwtCookie.getValue();
        }
        return null;
    }

    // 토큰의 유효성 + 만료일자 확인  // -> 토큰이 expire되지 않았는지 True/False로 반환해줌.
    public boolean validateToken(String jwtToken) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken).getBody();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}