package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTransactionRollbackException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.UserDataHandler;
import sample.animations.shake;
import sample.animations.SceneChanging;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button SignUpButton;

    @FXML
    private TextField LoginField;

    @FXML
    private PasswordField PasswordField;

    @FXML
    private Button SignInButton;


    @FXML
    void initialize() {
        //sign in
        SignInButton.setOnAction(event -> {
            String loginText = LoginField.getText().trim();
            String loginPassword = PasswordField.getText().trim();

            if(!loginText.equals("") && !loginPassword.equals("")){
                loginUser (loginText,loginPassword);
            }
            else {
                shake userLoginAnim = new shake(LoginField);
                shake userPasswordAnim = new shake(PasswordField);
                userLoginAnim.playAnim();
                userPasswordAnim.playAnim();
            }
        });

        //sign up
        SignUpButton.setOnAction(event ->{
            SceneChanging changeScene = new SceneChanging();
            changeScene.SceneChanging("/sample/signup.fxml", SignUpButton);
            changeScene.openNewScene();
        });
    }

    private void loginUser(String loginText, String loginPassword) {
        DataBaseHandler dbHandler = new DataBaseHandler();
        User user = new User();
        user.setUserName(loginText);
        user.setPassword(loginPassword);
        ResultSet result = dbHandler.getUser(user);

        int count = 0;
        //проход по всем пользователям
        while (true){
            try {
                if (!result.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count++;
        }
        if(count >= 1) {
            SceneChanging changeScene = new SceneChanging();
            changeScene.SceneChanging("/sample/app.fxml", SignUpButton);
            changeScene.openNewScene();
        }
        else {
            shake userLoginAnim = new shake(LoginField);
            shake userPasswordAnim = new shake(PasswordField);
            userLoginAnim.playAnim();
            userPasswordAnim.playAnim();
            //добавить выделение пароля
        }

    }



}
