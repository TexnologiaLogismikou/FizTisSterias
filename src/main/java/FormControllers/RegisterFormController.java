package FormControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.http.HttpStatus;

public class RegisterFormController implements IFormController {

    @FXML
    private Label registerErrorLabel;
    @FXML
    private PasswordField passwordRegisterField;
    @FXML
    private PasswordField passwordRepeatRegisterField;
    @FXML
    private TextField usernameRegisterField;
    private UIController parentController;
    private HttpStatus httpResponseCode;
    private Stage ourStage;

    public void SubmitRegisterButtonClick() {
        registerErrorLabel.setVisible(false);
        httpResponseCode = parentController.RegisterUser(usernameRegisterField.getText(), passwordRegisterField.getText(), passwordRepeatRegisterField.getText());
        AttemptToRegisterResults();
    }

    @Override
    public void setParentController(UIController parentController) {
        this.parentController = parentController;
    }

    private void AttemptToRegisterResults() {
        switch (httpResponseCode) {
            case OK:
                ourStage.close();
                break;
            case NOT_FOUND:
                ErrorHandler("No response from the server");
                break;
            case I_AM_A_TEAPOT:
                ErrorHandler("Passwords must match");
                break;
            case FOUND:
                ErrorHandler("Username already in use");
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
