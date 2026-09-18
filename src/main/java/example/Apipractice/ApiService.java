package example.Apipractice;

import java.net.URI;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.beans.factory.annotation.Value;

@Service 
public class ApiService {
    @Value ("${api.public-data.service-key}")
    private String serviceKey;
    private WebClient webClient = WebClient.builder().build();

    public Map<String,Object> parking(){
        String url = "https://api.odcloud.kr/api/15003953/v1/uddi:27347831-ca36-4887-b51d-d5e59f56bfea";
        url+="?page="+1;
        url+="&perPage=" +30;
        url+="&serviceKey=" + serviceKey;

        Map<String,Object> response = webClient.get()
        .uri(url).retrieve().bodyToMono(Map.class).block();
        return response;
    }
    public Map<String, Object> get(){
        String url = "https://api.odcloud.kr/api/15062631/v1/uddi:5134c40d-4de8-49ec-ad70-de75d6675ac7";
        url += "?page="+1;
        url += "&perPage="+10;
        url += "&serviceKey="+serviceKey;

        Map<String, Object> response = webClient.get()
                                        .uri(url)
                                        .retrieve()
                                        .bodyToMono( Map.class)
                                        .block();
        return  response;
    }

    public Map<String,Object> findAll(){
        String url = "https://api.odcloud.kr/api/3045179/v1/uddi:8e74c407-37bd-453b-9d16-bef24ca26490";
        url += "?page="+1;
        url += "&perPage="+10;
        url += "&serviceKey="+ serviceKey;
        
        Map<String,Object> re = webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono(Map.class)
            .block();

        return re;
    }

    public String getDustData(){
    // &year=2020&itemCode=PM10
        String url = "https://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo"
                + "?serviceKey=" + serviceKey
                + "&returnType=json"
                + "&numOfRows=15"
                + "&pageNo=1"
                +"&year=2020"
                +"&itemCode=PM10";
        RestTemplate restTemplate = new RestTemplate();
        String response = 
        restTemplate.getForObject(URI.create(url),
        String.class);
        return response;
    }

}
