package DTOs;

public class DTOMaker {

    public UserDTO CreateUser(String username, String password){
        return new UserDTO(username,password);
    }
}
