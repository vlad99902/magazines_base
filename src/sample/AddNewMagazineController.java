package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import sample.animations.SceneChanging;
import sample.animations.shake;

public class AddNewMagazineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addMagazine;

    @FXML
    private TextField MagazineNameField;

    @FXML
    private Button closeWindow;

    @FXML
    private Button changeData;

    @FXML
    private ComboBox<Publisher> comboboxPublisher;

    @FXML
    private ComboBox<Publisher> comboboxTheme;


    @FXML
    void initialize() {
       closeWindow.setOnAction(event -> {
           closeWindow.getScene().getWindow().hide();
       });
        DataBaseHandler dbHandler = new DataBaseHandler();
       ObservableList<Publisher> pub = dbHandler.returnListFromDb(consts.PUBLISHERS_TABLE,consts.PUBLISHERS_NAME,consts.PUBLISHERS_ID);
       ObservableList<Publisher> them = dbHandler.returnListFromDb(consts.THEMES_TABLE,consts.THEMES_NAME,consts.THEMES_ID);
       //publisher вывод данных в лист
       comboboxPublisher.setItems(FXCollections.observableList(pub));

        comboboxPublisher.setConverter(converterPublisherOutString);
        comboboxPublisher.getSelectionModel().select(0);
        //themes вывод данных в лист
        comboboxTheme.setItems(FXCollections.observableList(them));
        comboboxTheme.setConverter(converterPublisherOutString);
        comboboxTheme.getSelectionModel().select(0);


       changeData.setOnAction(event -> {
           SceneChanging changeScene = new SceneChanging();
           changeScene.SceneChanging("/sample/AddNewTheme.fxml", changeData);
           changeScene.openNewSceneWithoutClosing();
       });

       addMagazine.setOnAction(event -> {
           String name = MagazineNameField.getText().trim();
           if (!name.equals("")) {
               addNewMagazine();

               //сообщение о добавлении записи
               MessageWindow information = new MessageWindow();
               information.messageInfo("Успешно!", "Запись добавлена", "");

               //очистка полей
               MagazineNameField.clear();
           } else {
               shake magNameAnim = new shake(MagazineNameField);
               magNameAnim.playAnim();
           }
       });

    }

    //добавление журнала в таблицу в БД
    public void addNewMagazine (){
        DataBaseHandler dbHandler = new DataBaseHandler();
        String name = MagazineNameField.getText();
        //присвоить индекс
        Publisher publisher = comboboxPublisher.getSelectionModel().getSelectedItem();
        Publisher theme = comboboxTheme.getSelectionModel().getSelectedItem();
        MagazinesTable magazine = new MagazinesTable(name,publisher.getId(),theme.getId());
        dbHandler.addMagazine(magazine);
    }


    //добавить обработчик на нажатиие бо боксу
    //НЕ РАБОТАЕТ ПЕРЕДЕЛАТЬ

    public void onClickPublishers(ActionEvent event) {

    }

    public void onClickThemes(ActionEvent event) {

    }

    //конвертер
    StringConverter<Publisher> converterPublisherOutString = new StringConverter<Publisher>() {
        @Override
        public String toString(Publisher object) {
            return object.getPublisherName();
        }

        @Override
        public Publisher fromString(String string) {
            return null;
        }
    };
}

