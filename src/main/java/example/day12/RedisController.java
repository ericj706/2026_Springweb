package example.day12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor 
@RequestMapping ("/api/redis")
public class RedisController {
    // [1] 레디스 조작 객체
    private final StringRedisTemplate stringRedisTemplate;

    // 1.
    @GetMapping ("/test1")
    public Map<String,Object> test1(){
        // [2] redis에 자료 삽입, .opsForValue().set(key , value), 문자열타입
        // key 중복X, value 중복O
        stringRedisTemplate.opsForValue().set("유재석", "90");
        stringRedisTemplate.opsForValue().set("강호동", "100");
        stringRedisTemplate.opsForValue().set("신동엽", "70");

        // [3] redis에 자료조회, .keys("*"), 모든 자료들의 키 조회, Set<String> 컬렉션으로 반환
        // 참고 : 컬렉션프레임워크 ( List, Map, Set )
        Set<String> keys = stringRedisTemplate.keys("*");
        Map<String , Object> map = new HashMap<>();
        for (String key : keys) {
            String data = stringRedisTemplate.opsForValue().get(key);
            map.put(key, data);
        }
        return map;
    }

    // *******redis CRUD********
    private final ObjectMapper objectMapper = new ObjectMapper(); // 직렬화
    
    // 저장
    @PostMapping ("/member")
    public boolean save(@RequestBody MemberDto memberDto) throws JsonProcessingException, JsonProcessingException{
        // 1. 중복 없는 key구성( 예: 도메인명:식별키)
        String key = "member:" + memberDto.getMno();  // 예) member:3
        // 2. 문자열 템플릿에 DTO/자바객체 대입 , DTO -> 문자열(직렬화), 문자열->DTO
        String str = objectMapper.writeValueAsString(memberDto);
        // 3. redis에 저장
        stringRedisTemplate.opsForValue().set(key, str);
        return true;
    }
    // 전체조회
    @GetMapping ("/member")
    public List<MemberDto> findAll() throws JsonProcessingException, JsonProcessingException{
        // 1. 특정 패턴의 key조회
        Set<String> keys = stringRedisTemplate.keys("member:*");
        // 2. 모든 키 반복하여 하나씩 키에 대응하는 dto(값) 호출
        List<MemberDto> list = new ArrayList<>();
        for (String key : keys) {
            String value = stringRedisTemplate.opsForValue().get(key);
            // 3. 역직렬화
            // objectMapper.readValue(값, 타입명.class), 예외발생
            MemberDto memberDto = objectMapper.readValue(value, MemberDto.class);
            list.add(memberDto);
        }
        return list;
    }
    // 개별조회
    @GetMapping ("/member/find")
    public MemberDto find(@RequestParam  (name = "mno") Long mno) throws JsonProcessingException, JsonProcessingException {
        // 1. 조회할 mno매개변수로 받는다
        // 2. 레디스에서 특정 mno의 키 조회
        String findKey = "member:" +mno;
        String value = stringRedisTemplate.opsForValue().get(findKey);
        if (value==null) return null;
        // 3. 역직렬화: string-> 자바객체(dto/map/list)
        MemberDto memberDto = objectMapper.readValue(value, MemberDto.class);
        return memberDto;
    }
    // 삭제
    @DeleteMapping ("/member")
    public boolean delete(@RequestParam (name = "mno")Long mno){
        String deleteKey = "member:" + mno;
        boolean result = stringRedisTemplate.delete(deleteKey);
        return result;
    }
    // 수정
    @PutMapping ("/member")
    public boolean update(@RequestBody MemberDto memberDto) throws JsonProcessingException{
        // 1. 수정할 자료들을 dto받는다. // 2. 수정할 key 조합하여 수정
        String updateKey = "member:" + memberDto.getMno();
        if (updateKey == null) return false;
        String value = objectMapper.writeValueAsString(memberDto);
        stringRedisTemplate.opsForValue().set(updateKey, value);
        return true;
    }
    
}
