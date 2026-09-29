package example.day11;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@CrossOrigin (origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequiredArgsConstructor 
@RequestMapping ("/api/member")
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;
    
    // [1] 회원가입
    @PostMapping ("/signup")
    public boolean signup(@RequestBody MemberDto memberDto){
        return memberService.signup(memberDto);
    }

    // [2] 로그인 + 세션(인증 성공시 성공한 회원정보 저장)
    @PostMapping ("/login")
    public MemberDto login(@RequestBody MemberDto memberDto, HttpServletResponse response){
        // 1. 서비스에게 인증확인 
        MemberDto result = memberService.login(memberDto);
        if(result == null) return null;
        // 2. 로그인 성공시 쿠키 생성/발급
        // 쿠키는 세션과다르게 클라이언트 저장되므로 회원번호만 저장
        // ResponseCookie cookie = ResponseCookie.from("쿠키명", "쿠키값").build();
        // **참고** 정수 -> 문자 타입변환 방법 1)정수+"" 2) String.valueOf(정수), 쿠키값은 String타입만. 
            // 4. 토큰 발급 요청
        String token = jwtUtil.createToken(result.getMno());
        ResponseCookie cookie = ResponseCookie.from("login_member", token)
                                .path("/") // 쿠키 사용할 경로, "/" 도메인내 전체
                                .maxAge(Duration.ofDays(1)) // Duration.ofXXX : 쿠키유효기간 설정
                                .httpOnly(true) // JS이용한 탈취 방지, XSS공격 방지
                                .secure(false) // HTTPS에서만 사용, 개발단계 : fals, 배포단계 : true
                                .sameSite("Lax") // CSRF 공격 방지
                                .build(); // 쿠키 생성 끝
        // 3. 응답 헤더에 쿠키 등록 ( .setHeader() )
        response.setHeader(HttpHeaders.SET_COOKIE , cookie.toString());
        return result;
    }

    // [3] 내정보조회 + 쿠키
    @GetMapping ("/me")        // @CookieValue (value = "쿠키명")
    public MemberDto getMyInfo(@CookieValue (value = "login_member",required = false) String token){
        // 1. 만약에 loginMno가 없다면 비로그인중
        if (token == null) {
            return null;
        }
        // *** 쿠키에 저장된 token이용하여 회원번호 찾기
        Long loginMno = jwtUtil.getMnoFromToken(token);
        // 2. 로그인 중이면 서비스에게 회원정보 요청
        // **참고** 문자 -> 정수 변환방법 1) 래퍼클래스명.parse타입(문자)
        return memberService.getMyInfo((loginMno));
    }
    
    // [4] 로그아웃 + 세션초기화
    @PostMapping ("/logout")
    public boolean logout(HttpServletResponse response){
        // 1. 삭제할 쿠키명과 동일한 이름으로 maxAge(0)으로 하여 재발급
        ResponseCookie cookie = ResponseCookie.from("login_member", "")
                                .path("/")  // 모든곳에서 로그아웃 가능, 전체에서
                                .httpOnly(true).secure(false)
                                .maxAge(0)  // 바로삭제
                                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return true;
    }
    


    // @GetMapping ("")
    // public String test(HttpServletRequest request){
    //     // 1) HttpServletRequest : HTTP 요청이 들어오면 요청 정보를 담고 있는 객체
    //     System.out.println(request.getRemoteAddr()); // 요청한 클라이언트의 IP(로그/위치추적/조회수)
    //     System.out.println(request.getHeader("User-Agent")); // 요청한 클라이언트 브라우저 정보
    //     System.out.println(request.getSession()); // 요청한 클라이언트의 세션객체 정보
    //     // 2) 세션객체란? 톰캣 서버내 브라우저마다 독립적인 저장소
    //     // 주로: *로그인성공정보*, 인증번호, 비회원제 장바구니 등등 일시적인 휘발성 메모리
    //     HttpSession session = request.getSession();
    //     System.out.println(session.getId()); // 세션 식별번호
    //     System.out.println(session.getCreationTime()); // 세션 생성시간 (은행)
    //     System.out.println(session.getLastAccessedTime()); // 세션 마지막접근 시간
    //     System.out.println(session.getMaxInactiveInterval()); // 세션 생명주기(자동로그아웃, 기본값이 30분)
    //     // 3) 세션 정보 저장 = 로그인 / 호출 = 마이페이지 / 삭제 = 로그아웃
    //     session.setAttribute("data", "사과"); // map(key:value)쌍 구조로
    //     // data란 이름으로 사과를 저장, 주의점: value 타입은 Object라서 타입변환 필요
    //     System.out.println(session.getAttribute("data")); // key이용한 value 호출
    //     session.invalidate(); // 세션 초기화
    //     return session.getId();

    // }
}   

