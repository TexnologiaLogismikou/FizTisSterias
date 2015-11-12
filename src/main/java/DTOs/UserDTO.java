package DTOs;

public class UserDTO {
    private String username;
    private String password;

    public UserDTO(String username, String password)
    {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
