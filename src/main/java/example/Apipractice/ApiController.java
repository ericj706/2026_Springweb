package example.Apipractice;

import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@CrossOrigin (value = "http://localhost:5173")
@RestController @RequiredArgsConstructor 
public class ApiController {
    private final ApiService apiService;

    @GetMapping ("/parking")
    public Map<String,Object> parking(){
        return apiService.parking();
    }
}
