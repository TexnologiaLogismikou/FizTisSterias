import DTOs.DTOMaker;
import DTOs.UserDTO;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UIController {

    public TextField usernameLoginField;
    public PasswordField passwordLoginField;
    public TextField usernameRegisterField;
    public Button signInButton;
    public Button signUpButton;
    public Label loggedAsLabel;
    public Button signOutButton;
    public PasswordField passwordRegisterField;
    public Button submitRegisterButton;
    public PasswordField passwordRepeatRegisterField;
    public Label registerErrorLabel;
    public Button submitLoginButton;
    private RequestSender requestSender;
    private DTOMaker dtoMaker;

    public UIController()
    {
        dtoMaker = new DTOMaker();
        requestSender = new RequestSender("http://shinigami.ddns.net");
    }

    //Event Handlers
    public void SignInButtonClick(Event event) {
        ShowLoginForm(event);
    }

    public void SignUpButtonClick(Event event) {
        ShowRegisterForm(event);
    }

    public void SubmitRegisterButtonClick() {
        boolean response = RegisterUser(usernameRegisterField.getText(),passwordRegisterField.getText(),passwordRepeatRegisterField.getText());
        if (response){
            DialogController.getInstance().close();
        }
    }

    public void SubmitLoginButtonClick() {
        DialogController.getInstance().close();
        //todo login
    }

    //UI Manipulation
    public void ShowLoginForm(Event event){
        DialogController.getInstance().init("loginForm.fxml","Fiz - Sign in",((Node) event.getSource()).getScene().getWindow());
        DialogController.getInstance().show();
    }

    public void ShowRegisterForm(Event event){
        DialogController.getInstance().init("registerForm.fxml","Fiz - Sign up",((Node) event.getSource()).getScene().getWindow());
        DialogController.getInstance().show();
    }

    //Tasks
    public boolean RegisterUser(String username, String password, String password2){
        registerErrorLabel.setVisible(false);
        if (ValidatePasswords(password,password2)) {
            UserDTO userData = dtoMaker.CreateUser(username, password);
            ResponseEntity<String> response = requestSender.RegisterUser(userData);
            if (response==null){
                registerErrorLabel.setText("Cannot connect to the server");
                registerErrorLabel.setVisible(true);
                return false;
            }
            else if (response.getStatusCode()== HttpStatus.FOUND){
                registerErrorLabel.setText("Username already in use");
                registerErrorLabel.setVisible(true);
                return false;
            }
            else if(response.getStatusCode()== HttpStatus.OK){
                //TODO ok registered
                signInButton.setVisible(false);
                signUpButton.setVisible(false);
                signOutButton.setVisible(true);
                loggedAsLabel.setText(loggedAsLabel.getText().concat(username));
                loggedAsLabel.setVisible(true);
                return true;
            }else{
                registerErrorLabel.setText("Unknown error occurred");
                registerErrorLabel.setVisible(true);
                return false;
            }
        }else{
            registerErrorLabel.setText("Passwords do not match");
            registerErrorLabel.setVisible(true);
            return false;
        }
    }

    public void LoginUser(String username, String password){

    }

    private boolean ValidatePasswords(String pass1, String pass2)
    {
        return pass1.equals(pass2);
    }

    public void SignOutButtonClick() {
    }

}
