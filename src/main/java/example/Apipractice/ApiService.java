package example.Apipractice;

import java.util.Map;

import org.springframework.stereotype.Service;
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
}
