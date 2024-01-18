package in.teamelementals.zap.zap_attendance_system.OtherServices;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiServices {

    private final RestTemplate restTemplate;

    public ApiServices(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String fetchDataFromApi(String apiUrl) {
        return restTemplate.getForObject(apiUrl, String.class);
    }
}
