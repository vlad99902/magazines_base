package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sample.animations.shake;

public class AddNewThemeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addRecord;

    @FXML
    private TextField addRecordField;

    @FXML
    private Button closeWindow;

    @FXML
    private ComboBox<String> comboboxSetDirectory;

    @FXML
    void initialize() {
        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });

        //создание листа и добавление названий справочников
        ObservableList<String> itemsList = FXCollections.observableArrayList();
        itemsList.addAll("Издатели", "Тематики");
        comboboxSetDirectory.setItems(itemsList);
        comboboxSetDirectory.getSelectionModel().select(0);


        //обработчик дажатия кнопки "Добавить"
        addRecord.setOnAction(event -> {
            String itemName = addRecordField.getText().trim();
            String directoryName = comboboxSetDirectory.getSelectionModel().getSelectedItem();

            String tableRef = null;
            String tableNameRef = null;
            if (directoryName.equals("Издатели")){
                tableRef = consts.PUBLISHERS_TABLE;
                tableNameRef = consts.PUBLISHERS_NAME;
            } else {
                tableRef = consts.THEMES_TABLE;
                tableNameRef = consts.THEMES_NAME;
            }

            if(!itemName.equals("")){
                addNewItem(tableRef,tableNameRef);

                //сообщение о добавлении записи
                MessageWindow information = new MessageWindow();
                information.messageInfo("Успешно!", "Запись добавлена", "");

                //очистка полей
                addRecordField.clear();
            } else {
                shake recordNameAnim = new shake(addRecordField);
                recordNameAnim.playAnim();
            }

        });

    }

    //добавление тематики или паблишера в таблицу
    public void addNewItem (String tableRef, String nameRef){
        DataBaseHandler dbHandler = new DataBaseHandler();
        String itemName = addRecordField.getText();
        Publisher item = new Publisher(itemName);
        dbHandler.addItemToDirectory(item, tableRef, nameRef);
    }
}
