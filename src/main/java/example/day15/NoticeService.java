package example.day15;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


/*
    Websocket + Stomp : 양방향(서버 <--> 서버) 통신 채팅구현
    SSE : 단방향(서버 --> 클라이언트) 알림
*/
@Service
public class NoticeService {
    //1. 연결된 클라이언트들의 정보 보관하는 리스트
    // *서버가 클라이언트에게 비동기 요청 보내기 위한 HTTP 응답 (스트리밍)객체, 파이프라인
    // *동기/비동기 : 동기화(하나의 메소드를 순차적으로 실행), 비동기(하나의 메소드를 동시실행)
    // *ArrayList() 동기화 지원X, Vector()는 동기화 지원O
    // *동기화 필요목적 : 하나의 서버가 구독과 메시지 전송 동시 다발적으로 순차처리
    // CopyOnWriteArrayList() : 여러개 요청들을 동시에 접속,종료,메시지전송의 동시화 제공

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    // 2. 클라이언트 구독처리(SseEmitter 생성하여 리스트에 저장)
    public SseEmitter subscribe(){
        SseEmitter emitter = new SseEmitter(); // 2-1: SseEmitter 객체 생성
        emitters.add(emitter);  // 2-2: 리스트에 저장
        return emitter; // 2-3: 생성된 객체를 반환
    }
    // 3. 메세지 전송 (서버가 클라이언트에게 메세지 전송)
    public void onMessage(String message){
        // 3-2 : 현재 리스트에 저장/접속/구독된 emitter들에게 메시지 보내기
        for (SseEmitter emitter : emitters) {
            //emitter.send( SseEmitter.event().name("구독식별").data(내용물)); , 일반예외발생
            try {
                emitter.send( SseEmitter.event().name("notice").data(message));
            } catch (IOException e) {
                emitters.remove(emitter);
            }
            
        }
    }
}
