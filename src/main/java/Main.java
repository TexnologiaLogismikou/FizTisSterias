import FormControllers.UIController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableAutoConfiguration
public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainWindow.fxml"));
        Parent root = loader.load();
        UIController primaryController = loader.getController();
        primaryController.setStage(primaryStage);
        primaryStage.setTitle("Fiz");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
