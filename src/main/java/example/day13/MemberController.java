package example.day13;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor 
@RequestMapping ("/api")
public class MemberController {
    private final MemberService memberService;
    
    // 세션 저장
    @GetMapping  ("/session/add")
    public String sessionAdd(@RequestParam (value = "data", required = false)
        String data, HttpSession session){
            return memberService.sessionAdd(data, session);
    }
    
    // 세션 전체 조회
    @GetMapping ("/session/all")
    public MemberDto sessionAll(HttpSession session){
        return memberService.sessionAll(session);
    }

    // 쿠키에 저장
    private final ObjectMapper objectMapper = new ObjectMapper(); // 직렬화 객체
    @GetMapping("/cookie/add")
    public String cookieAdd(@RequestParam("data") String data,
                                @CookieValue(name = "COOKIE_DATA", required = false) String cookieData,
                                HttpServletResponse response) throws Exception {
        List<String> list = (cookieData == null) ? new ArrayList<>() : objectMapper.readValue(cookieData, List.class );
        list.add(data);
        String json = objectMapper.writeValueAsString(list);
        ResponseCookie cookie = ResponseCookie.from("COOKIE_DATA", URLEncoder.encode(json, StandardCharsets.UTF_8) )
                .path("/")
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return "쿠키저장성공";
    }
    // 쿠키조회
    @GetMapping("/cookie/all")
    public List<String> cookieAll(@CookieValue(name = "COOKIE_DATA", required = false) String cookieData) throws Exception {
        if (cookieData == null) {
            return Collections.emptyList();
        }
        return objectMapper.readValue(cookieData, List.class );
    }
    // 레디스 저장
    private final StringRedisTemplate stringRedisTemplate;
    @GetMapping ("/redis/add")
    public String redisAdd(@RequestParam ("data") String data){
        stringRedisTemplate.opsForValue().set(data, data);
        return "레디스저장성공";
    }
    // 레디스 조회
    @GetMapping ("/redis/all")
    public List<String> redisAll(){
        Set<String> keys = stringRedisTemplate.keys("*");
        List<String> list = new ArrayList<>();
        for (String key : keys) {
            String data = stringRedisTemplate.opsForValue().get(key);
            list.add(data);
        }
        return list;
    }
}


