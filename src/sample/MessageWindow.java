package sample;

import javafx.application.Application;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

public class MessageWindow  {

    /**
     * окно для вывода информации с кнопкой ОК
     * @param title
     * @param headerText
     * @param contentText
     */
    public void messageInfo (String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
    }

    /**
     * Окно для подверждения выбора или чего-либо еще
     * @param title
     * @param headerText
     * @param contentText
     * @return
     */
    Alert messageOkCancel (String title, String headerText, String contentText){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);

        alert.showAndWait();
        return alert;
    }

}