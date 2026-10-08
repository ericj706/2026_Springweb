package example.day15;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor 
@Data @Builder 
public class MessageDto {
    private String type; // 메세지의 형식, Talk/Enter 구분
    private String roomId; // 방번호
    private String sender; // 보낸사람
    private String content; // 보낸내용
    private String date;    // 보낸시간
    
}
