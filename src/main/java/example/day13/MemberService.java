package example.day13;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;


 	
@Service @RequiredArgsConstructor 
public class MemberService {

    // 세션저장
    public String sessionAdd(String data, HttpSession session){
        if (data == null || data.trim().isEmpty()) {
            return "세션저장실패";
        }
        List<String> list = (List<String>) session.getAttribute("fruits");
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(data);
        session.setAttribute("fruits", list);
        return "세션저장성공";
    }
    // 세션조회
    public MemberDto sessionAll(HttpSession session){
        List<String> list = (List<String>) session.getAttribute("fruits");
        if (list == null) {
            return null;
        }return MemberDto.from(list);
    }
    
}
