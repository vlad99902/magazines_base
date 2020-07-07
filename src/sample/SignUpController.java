package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import sample.animations.SceneChanging;
import sample.animations.shake;
import sample.MessageWindow.*;

import javax.swing.*;


public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField LoginSignUpField;

    @FXML
    private PasswordField PasswordSignUpField;

    @FXML
    private Button SignUnOnButton;

    @FXML
    private TextField LastNameField;

    @FXML
    private TextField NameField;

    @FXML
    void initialize() {
        SignUnOnButton.setOnAction(event -> {
            String NameUser = NameField.getText().trim();
            String LastNameUser = LastNameField.getText().trim();
            String LoginUser = LoginSignUpField.getText().trim();
            String PasswordUser = PasswordSignUpField.getText().trim();

            if (!NameUser.equals("") && !LastNameUser.equals("") &&
                    !LoginUser.equals("") && !PasswordUser.equals("")) {
                signUpNewUser();

                //сообщение об удачной регистрации
                MessageWindow information = new MessageWindow();
                information.messageInfo("Успешно", "Вы успешно зарегистрировались", "");

                //открытие окна приложения
                SceneChanging changeScene = new SceneChanging();
                changeScene.SceneChanging("/sample/app.fxml", SignUnOnButton);
                changeScene.openNewScene();
            } else {
                shake userNameAnim = new shake(NameField);
                shake userLastName = new shake(LastNameField);
                shake userLoginAnim = new shake(LoginSignUpField);
                shake userPasswordAnim = new shake(PasswordSignUpField);
                userLoginAnim.playAnim();
                userPasswordAnim.playAnim();
                userNameAnim.playAnim();
                userLastName.playAnim();
            }
        });

    }

    private void signUpNewUser (){
        DataBaseHandler dbHandler = new DataBaseHandler();

        String firstName = NameField.getText();
        String lastName = LastNameField.getText();
        String userName = LoginSignUpField.getText();
        String password = PasswordSignUpField.getText();

        User user = new User (firstName, lastName, userName, password);

        dbHandler.signUpUser(user);
    }


}

