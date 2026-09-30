package example.day12;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor 
public class RedisTokenService {
    // [1] redis 조작객체 주입
    private final StringRedisTemplate stringRedisTemplate;

    // [2] refresh 토큰 저장함수
    public void setRefreshToken(Long mno, String token){
        stringRedisTemplate.opsForValue().set("RT:"+mno, token, Duration.ofDays(7));
    }

    // [3] refresh 토큰 조회 함수
    public String getRefreshToken(Long mno){
        return stringRedisTemplate.opsForValue().get("RT:"+mno);
    }

    // [4] refresh 토큰 삭제 함수
    public boolean deleteRefrshToken(Long mno){
        return stringRedisTemplate.delete("RT:"+mno);
    }

}
