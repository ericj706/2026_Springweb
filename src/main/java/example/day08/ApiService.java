package example.day08;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service 
public class ApiService {
    @Value ("${api.public-data.service-key}")
    private String serviceKey;
    private WebClient webClient = WebClient.builder().build();

    // [대구 중구 맛집]
    public Map<String,Object> test1(){
        // 1. API 주소 (공공데이터 신청한 api 요청 url)
        String url = "https://api.odcloud.kr/api/15052602/v1/uddi:855807e2-fe8a-4e47-8a5a-ce1894e410d7_201909031553";
        url += "?page="+1;
        url += "&perPage=" +10;
        url += "&serviceKey=" +serviceKey;
        
        // 3. WebClient 객체 이용한 api 요청하고 응답받기
        Map<String,Object> response = webClient.get() // .http메소드명  http GET메소드
                 .uri(url) // uri는 http 주소상에 자원(쿼리스트링)까지 포함
                 .retrieve()    // 요청 결과 반환 결과 수신
                 .bodyToMono(Map.class)  // 응답 결과 content-type 직렬화/변환
                 .block();  // 동기화
        return response;
    }

    // [전국 약국 정보]
    public Map<String,Object> test2(){
        String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown";
        url += "?serviceKey=" +serviceKey;
        url += "&pageNo=" +1;
        url += "&numOfRows=" +10;
        // 3.
        Map<String,Object> response = webClient.get()
                        .uri(url)
                        .retrieve()
                        // XML 타입 --> Map 직렬화 / 변환 실패
                        .bodyToMono(Map.class)
                        .block();
        return response;
    }
}
