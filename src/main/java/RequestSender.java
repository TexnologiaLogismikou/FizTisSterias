import DTOs.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class RequestSender {

    private String hostUrl;
    private RestTemplate restTemplate;

    public RequestSender(String url) {
        hostUrl = url;
        restTemplate = new RestTemplate();
    }

    //TODO check url
    public ResponseEntity<String> RegisterUser(UserDTO userToRegister){
        try {
            final String uri = hostUrl + ":8080/fiz/register/rd";
            Map<String, String> parameters = new HashMap<>();
            parameters.put("username",userToRegister.getUsername());
            parameters.put("password",userToRegister.getPassword());
            //Gson user = new Gson();
            //user.toJson(userToRegister);
            return restTemplate.postForEntity(uri,null,String.class,parameters);
            //return restTemplate.postForEntity(url, user.toJson(userToRegister), String.class);
            //TODO Do it with Json (must change service side also)
        }catch (Exception e){
            return null;
        }
    }

    //TODO transform to login
    public void GetMessage() {
        final String url = hostUrl + ":8080/Fiz-0.1.0/messages";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }
}
