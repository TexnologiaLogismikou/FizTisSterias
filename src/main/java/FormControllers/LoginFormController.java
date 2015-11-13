package FormControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;

public class LoginFormController implements IFormController {

    @FXML
    private TextField usernameLoginField;
    @FXML
    private PasswordField passwordLoginField;
    @FXML
    private Button submitLoginButton;
    @FXML
    private Label registerErrorLabel;
    private UIController parentController;
    private HttpStatus httpResponseCode;
    private Stage ourStage;

    public void SubmitLoginButtonClick() {
        registerErrorLabel.setVisible(false);
        httpResponseCode = parentController.LoginUser(usernameLoginField.getText(), passwordLoginField.getText());
        AttemptToLoginResults();
    }

    @Override
    public void setParentController(UIController parentController) {
        this.parentController = parentController;
    }

    private void AttemptToLoginResults() {
        switch (httpResponseCode) {
            case OK:
                ourStage.close();
                break;
            case NOT_FOUND:
                ErrorHandler("Username does not exist");
                break;
            case UNAUTHORIZED:
                ErrorHandler("Incorrect password");
                break;
            default:
                ErrorHandler("An unexpected error occurred");
                break;
        }
    }

    @Override
    public void setOurStage(Stage ourStage) {
        this.ourStage = ourStage;
    }

    private void ErrorHandler(String errorText) {
        registerErrorLabel.setText(errorText);
        registerErrorLabel.setVisible(true);
    }
}
