package Handlers;

import DTOs.UserDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RequestSender {

    private String hostUrl;
    private RestTemplate restTemplate;

    public RequestSender(String url) {
        hostUrl = url;
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
    }

    public ResponseEntity<String> RegisterUser(UserDTO userDetails) {
        try {
            String url = hostUrl + "/register" + "?" + "username=" + userDetails.getUsername() + "&password=" + userDetails.getPassword();
            HttpEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            System.out.println(response.getBody());
            return (ResponseEntity<String>) response;
        } catch (Exception e) {
            return null;
        }
    }

    public ResponseEntity<String> LoginUser(UserDTO userDetails) {
        try {
            String url = hostUrl + "/log-in" + "?" + "username=" + userDetails.getUsername() + "&password=" + userDetails.getPassword();
            HttpEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            return (ResponseEntity<String>) response;
        } catch (Exception e) {
            return null;
        }
    }
}
