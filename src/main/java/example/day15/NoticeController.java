package example.day15;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RestController 
@RequiredArgsConstructor @CrossOrigin (origins = "*")
@RequestMapping ("/api/sse")
public class NoticeController {
    private final NoticeService noticeService;
    // 1. 알림 구독 매핑
    // * 응답 Content-type JSON이 아닌 EVENT_STREAM타입으로 변경
    @GetMapping (value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe(){
        return noticeService.subscribe();
    }
    // 2. 알림 메시지 전송 *리스트*
    @GetMapping ("/message")
    public void onMessage(@RequestParam(name = "msg") String msg){
        noticeService.onMessage(msg);
        
    }
}
