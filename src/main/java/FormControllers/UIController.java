package FormControllers;

import DTOs.DTOMaker;
import DTOs.UserDTO;
import Handlers.RequestSender;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UIController {

    public Button signInButton;
    public Button signUpButton;
    public Label loggedAsLabel;
    public Button signOutButton;
    private RequestSender requestSender;
    private DTOMaker dtoMaker;
    private Stage stage;

    public UIController() {
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

    //UI Manipulation
    public void ShowLoginForm(Event event) {
        CreateNewModalWindow("/loginForm.fxml", "Fiz - Sign in", ((Node) event.getSource()).getScene().getWindow());
    }

    public void ShowRegisterForm(Event event) {
        CreateNewModalWindow("/registerForm.fxml", "Fiz - Sign up", ((Node) event.getSource()).getScene().getWindow());
    }

    //Tasks
    public HttpStatus RegisterUser(String username, String password, String password2) {
        if (ValidatePasswords(password, password2)) {
            UserDTO userData = dtoMaker.CreateUser(username, password);
            ResponseEntity<String> response = requestSender.RegisterUser(userData);
            if (response == null) {
                return HttpStatus.NOT_FOUND;
            } else {
                return response.getStatusCode();
            }
        } else {
            return HttpStatus.I_AM_A_TEAPOT;
        }
    }

    public HttpStatus LoginUser(String username, String password) {
        UserDTO userData = dtoMaker.CreateUser(username, password);
        ResponseEntity<String> response = requestSender.LoginUser(userData);
        if (response == null) {
            return HttpStatus.NOT_FOUND;
        } else {
            return response.getStatusCode();
        }
    }

    private void CreateNewModalWindow(String source, String title, Window parentWindow) {
        try {
            stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(source));
            Parent root = loader.load();
            ((IFormController) loader.getController()).setOurStage(stage);
            ((IFormController) loader.getController()).setParentController(this);
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentWindow);
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.show();
        }
    }

    private boolean ValidatePasswords(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    public void SignOutButtonClick() {
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
