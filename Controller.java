package sample.Calculette;

/*
  --------------------------------
  PORTABLE CALCULATOR V1.0
  Basic functions supported
  @author sofoste: Stephane Sob F.
 * --------------------------------
 */
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller extends Main implements Initializable{
    private static final int add = 1;
    private static final int sub = 2;
    private static final int mul = 3;
    private static final int div = 4;
    private static final int mod = 5;

    private static final String errorMsg = "ERROR! Couldn't proceed.";
    private static final String errorMsg1 = "ERROR! Unexpected value: ";
    private static String msg = "WELCOME";

    private Float aFloat = 0f;
    private int choice = -1;

    @FXML private TextField screen;
    //group 1: numbers
    @FXML private Button num0;@FXML private Button num1;
    @FXML private Button num2;@FXML private Button num3;
    @FXML private Button num4;@FXML private Button num5;
    @FXML private Button num6;@FXML private Button num7;
    @FXML private Button num8;@FXML private Button num9;

    //group operators
    @FXML private Button exitBtn;
    @FXML private Button addBtn;@FXML private Button subBtn;
    @FXML private Button mulBtn;@FXML private Button divBtn;
    @FXML private Button dotBtn;@FXML private Button modBtn;

    //others Btn
    @FXML private Button clearBtn;@FXML private Button resultBtn;
    @FXML private ProgressBar progress;

    //close onBtnClick
    public void closeApp(){
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
        /*Stage primaryStage = new Stage();
        Main newApp = new Main();
        exitBtn.setOnAction((actionEvent -> primaryStage.close()));*/
    }
    //clear input
    public void clearInput(){
        screen.setText("");
    }
    //perform operation
    @FXML
    public void performOperation(ActionEvent event){
        if (event.getSource() == ""){
            screen.setText(msg);
        } else if (event.getSource() == num0) {
            screen.setText(screen.getText() + "0");
        } else if (event.getSource() == num1) {
            screen.setText(screen.getText() + "1");
        } else if (event.getSource() == num2) {
            screen.setText(screen.getText() + "2");
        } else if (event.getSource() == num3) {
            screen.setText(screen.getText() + "3");
        } else if (event.getSource() == num4) {
            screen.setText(screen.getText() + "4");
        } else if (event.getSource() == num5) {
            screen.setText(screen.getText() + "5");
        } else if (event.getSource() == num6) {
            screen.setText(screen.getText() + "6");
        } else if (event.getSource() == num7) {
            screen.setText(screen.getText() + "7");
        } else if (event.getSource() == num8) {
            screen.setText(screen.getText() + "8");
        } else if (event.getSource() == num9) {
            screen.setText(screen.getText() + "9");
        } else if (event.getSource() == clearBtn) {
            screen.setText("");
        } else if(event.getSource() == dotBtn){
            screen.setText(screen.getText() + ".");
        } else if (event.getSource() == addBtn) {
            aFloat = Float.parseFloat(screen.getText());
            choice = add; //Addition
            screen.setText("");
        } else if (event.getSource() == subBtn) {
            aFloat = Float.parseFloat(screen.getText());
            choice = sub; //Substraction
            screen.setText("");
        } else if (event.getSource() == mulBtn) {
            mulBtn.setText("*");
            aFloat = Float.parseFloat(screen.getText());
            choice = mul; //Mul
            screen.setText("");
        } else if (event.getSource() == divBtn) {
            aFloat = Float.parseFloat(screen.getText());
            choice = div; //Division
            screen.setText("");

        }else if (event.getSource() == modBtn){
            aFloat = Float.parseFloat(screen.getText());
            choice = mod;
            screen.setText("");
        } else if (event.getSource() == resultBtn) {
            Float secondOperand = Float.parseFloat(screen.getText());
            //remove unnecessary break;
            progress.setVisible(false);
            switch (choice) {
                case add -> { //Add
                    float result = aFloat + secondOperand;
                    screen.setText(String.valueOf(result));
                }
                case sub -> { //Subtract
                    float result;
                    result = aFloat - secondOperand;
                    screen.setText(String.valueOf(result));
                }
                case mul -> { //Multiply
                    float result;
                    result = aFloat * secondOperand;
                    screen.setText(String.valueOf(result));
                }
                case div -> { //Divide
                    float result;
                    result = 0f;
                    try {
                        result = aFloat / secondOperand;
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        screen.setText(errorMsg);
                    }
                    screen.setText(String.valueOf(result));
                }
                case mod -> { //modulo to obtain the rest of a division
                    float result;
                    result = aFloat % secondOperand;
                    screen.setText(String.valueOf(result));
                }
                default -> {
                    screen.setText(errorMsg1 + choice);
                    progress.setVisible(true);
                }
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // TODO nothing yet :-)
    }
}
