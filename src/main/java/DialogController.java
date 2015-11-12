import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.dialog.ExceptionDialog;

import java.io.IOException;

public class DialogController {

    public static DialogController RD = null;
    private Stage stage;

    private DialogController(){

    }

    public static DialogController getInstance(){
        if (RD==null){
            RD = new DialogController();
        }
        return RD;
    }

    public void init(String source,String title, Window parentWindow){
        try {
            stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource(source));
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentWindow);
            stage.setResizable(false);
        }catch (IOException e)
        {
            //TODO Create exception dialog form /w sound
            ExceptionDialog dialog = new ExceptionDialog(e);
            dialog.show();
        }
    }

    public void show(){
        //this.stage = stage;
        stage.show();
    }

    public void close(){
        stage.close();
    }
}
