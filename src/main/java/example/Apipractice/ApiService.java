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
        url+="&serviceKey=3f12958d4a3d304a9e3f30fef163ec8020c6aec9c35ad05e206caa977355eaa2";

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
        url += "&serviceKey=bbc3964d645b12a3daffebb1a71b22b2764c82c4adc300e06f8d1c9446939d7b";
        
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
                + "?serviceKey=3a0c0c9ebd3517b946f7e40c38ce1f55865fcd01fd05544e48dcce9b7cccf2ab"
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
