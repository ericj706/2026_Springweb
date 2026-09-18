package example.day08;


import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;


@Service 
public class ApiService {
    @Value ("${api.public-data.service-key}")
    private String serviceKey;
    private WebClient webClient = WebClient.builder().build();

    // [대구 중구 맛집].openAPI
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

    // [전국 약국 정보].xml
    public Map<String,Object> test2(){
        String url = "https://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyFullDown";
        url += "?serviceKey=" +serviceKey;
        url += "&pageNo=" +1;
        url += "&numOfRows=" +10;
        // 3.
        String response = webClient.get().uri(url).retrieve()
                        // XML 타입 -->String --> Map 직렬화 / 변환 실패
                        .bodyToMono(String.class)
                        .block();
        // 4. String -> xml 변환, 
        XmlMapper xmlMapper = new XmlMapper(); // xml매퍼 객체 생성
        try { 
            // Map<String,Object> map = xmlMapper.readValue(xml문자열, 타입명.class); // +일반예외
           Map<String,Object> map = xmlMapper.readValue(response, Map.class);
            return map; 
        } catch (Exception e) {
            System.out.println(e);
        }return null;
    }

    // 중소벤처기업 정보.csv
    // 프로젝트내 resources>static>파일명.csv
    public List<Map<String,Object>> test3(){
        List<Map<String,Object>> list = new ArrayList<>();
        // 1. .csv파일 경로, resources 이하 폴더
        String fileName = "static/중소벤처기업부_벤처기업명단_20260521.csv";
        // 2. ClassPathResource 객체 이용하여 해당 경로내 파일 가져오기 [파일객체]
        ClassPathResource resource = new ClassPathResource(fileName);
        
        try {
            // 3. (대용량) 파일들을 바이트로 읽어와서 바이트 배열에 저장 .getInputStream().readAllBytes();
            byte[] bytes = resource.getInputStream().readAllBytes();
            // 4. 한글 인코딩, EUC-KR, CP949, UTF-8
            InputStreamReader reader = new InputStreamReader( new java.io.ByteArrayInputStream(bytes)
            , Charset.forName("UTF-8"));
            // 5. OpenCSV
            CSVReader csvReader = new CSVReaderBuilder(reader).build();
            // 6. 주로 첫행은 제목(행) 가져오기 (key/속성명 사용할 예정)
            String[] headers = csvReader.readNext();
            // 7. 나머지 행들을 반복문 이용하여 가져오기
            String[] values;
            while (true) { // 무한루프
                values = csvReader.readNext(); // 한줄 읽어오기
                if (values == null) break; // 만약에 읽어온 데이터가 없으면 반복문 종료
                // 8. 반복문 이용하여 map 만들기
                Map<String,Object> row = new LinkedHashMap<>();
                for (int i = 0; i < headers.length; i++) {
                   row.put(headers[i], values[i]);
                }
                // 9. List에 생성한 map 추가
                list.add(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }return list;
    }

}
