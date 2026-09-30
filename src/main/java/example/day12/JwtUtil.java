package example.day12;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component  // Spring MVC 패턴이 아닌 일반 객체(빈) 생성
public class JwtUtil {
    @Value("${jwt.secret}")
    private String key;
    // sha알고리즘 + 비밀키(임의로)조합 ->hmacSha
    private SecretKey secretKey;
    @PostConstruct // 객체 생성시 의존성(@Value)가 완료된 후에 아래 메소드가 1번 호출 되도록 하는 어노테이션
    public void init(){
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    // [1] JWT Access 토큰 생성 메소드
    public String createAccessToken(Long mno){
        String jwt = Jwts.builder()   // 토큰 생성 시작
                    .claim("type", "ACCESS")
                    .subject(mno+"")  // 토큰에 들어갈 내용들(주로 식별번호, 권한)
                    .issuedAt(new Date() )   // 토큰 생성 시간
                    .expiration(new Date(new Date().getTime()+1000L *60 *30)) // 토큰 만료 시간
                        // new Date()현재시간, new Date().getTime() 현재시간초, *60(1분), *60(1시간)
                    .signWith(secretKey)
                    .compact(); // 토큰 생성 끝, 토큰 정보 문자열로 반환
        System.out.println(jwt);
        return jwt;
    }
    // [2] JWT 토큰 검증 메소드
    public Long getMnoFromToken(String token){
        try {
            Claims claims = Jwts.parser() // 파싱
                            .verifyWith(secretKey)  // 전자서명 이용한 검증
                            .build()
                            .parseSignedClaims(token)   // 파싱할 토큰
                            .getPayload();   // JWT안에 payload값 반환
            Long mno = Long.parseLong(claims.getSubject());
            System.out.println(mno);
            return mno;
        } catch (Exception e) {
            return null;    // 만약에 토큰이 없거나 문제가 있다면.
        }

    }
    // [3] JWT Refresh 토큰
    public String createRefreshToken(Long mno){
        return Jwts.builder()
                .claim("type", "REFRESH")
                .subject(mno+"")
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime()+1000L *60 *60 *24 *7)) // 액세스 토큰보다 만료기간 길게
                .signWith(secretKey)
                .compact(); // 생성된 토큰 문자열 반환
    }

}
