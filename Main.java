package sample.Calculette;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * --------------------------------
 * PORTABLE CALCULATOR V1.0
 * Basic functions supported
 * @author sofoste
 * --------------------------------
 */
public class Main extends Application {

    @FXML
    private Button exitBtn;
//    @FXML private Button exitBtn;


    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("PORTABLE CALCULATOR V1.0 [@sofoste]");

        primaryStage.setScene(new
                        Scene(root,
                        550,
                        600
                )
        );

/*        String decoration = this.getClass().getResource("Decoration.css").toExternalForm();
        root.getStylesheets().add(decoration);
*/



        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
