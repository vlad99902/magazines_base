package sample;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import com.sun.javafx.image.IntPixelGetter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import sample.animations.shake;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class EditMagazineController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editButtonMagazine;

    @FXML
    private TextField MagazineEditNameField;

    @FXML
    private Button closeWindow;

    @FXML
    private ComboBox<Publisher> comboboxEditPublisher;

    @FXML
    private ComboBox<Publisher> comboboxEditTheme;

    @FXML
    private Button deleteButtonMagazine;

    //сведения о журнале, который нужно изменить
    MagazinesTable magazineToEdit;

    @FXML
    void initialize() {
        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
    }

    //получение объекта из предыдущей формы
    public void getMagazineToEdit (MagazinesTable magazineToEdit){
        this.magazineToEdit = magazineToEdit;
        setMagazineInfoToWindow();
    }

    /**
     * установка в в поля значений, которые были выбраны в предыдущей форме
     */
    public void setMagazineInfoToWindow(){
        MagazineEditNameField.setText(magazineToEdit.getMagazinesName());

        //добавление в combobox элементов
        DataBaseHandler dbHandler = new DataBaseHandler();
        ObservableList<Publisher> publishers = dbHandler.returnListFromDb(consts.PUBLISHERS_TABLE, consts.PUBLISHERS_NAME, consts.PUBLISHERS_ID);
        comboboxEditPublisher.setItems(FXCollections.observableList(publishers));
        comboboxEditPublisher.setConverter(converterPublisherOutStringg);

        ObservableList<Publisher> themes = dbHandler.returnListFromDb(consts.THEMES_TABLE, consts.THEMES_NAME, consts.THEMES_ID);
        comboboxEditTheme.setItems(FXCollections.observableList(themes));
        comboboxEditTheme.setConverter(converterPublisherOutStringg);

        //получем id паблишера и темы по id журнала, для установки начальных значений combobox
        Integer magId = magazineToEdit.getMagazinesId();
        MagazinesTable magazineWithId = dbHandler.returnThemeAndPubIdFromDb(magId);

        Integer indexPub = returnIndexByObjectId(publishers, magazineWithId.getMagazinesPublisherId());
        Integer indexMag = returnIndexByObjectId(themes, magazineWithId.getMagazinesThemesId());
        System.out.println(indexMag + "  " + indexMag);

        //устанавливаем выбранный элемент по id
        comboboxEditPublisher.getSelectionModel().select(indexPub);
        comboboxEditTheme.getSelectionModel().select(indexMag);
    }

    /**
     * по нажатию на кнопку "Изменить" изменять значения полей БД
     * @param event
     */

    @FXML
    void onClickEditButtonMagazine(ActionEvent event) {
        DataBaseHandler db = new DataBaseHandler();

        //получаем выбранные пользователем тему и паблишера
        Publisher publisher = comboboxEditPublisher.getSelectionModel().getSelectedItem();
        Publisher theme = comboboxEditTheme.getSelectionModel().getSelectedItem();

        //создание объекта, данные из которого нужно передать в БД
        MagazinesTable resMagazine = new MagazinesTable(magazineToEdit.getMagazinesId(),MagazineEditNameField.getText(),
                publisher.getId(),theme.getId());

        //изменение значений в БД таблца Журналы
        if (!MagazineEditNameField.getText().isEmpty()){
            //вывод окна подтверждения изменения
            MessageWindow information = new MessageWindow();
            Alert result = information.messageOkCancel("Вы уверены?",
                    "Вы хотите изменить журнал: " + magazineToEdit.getMagazinesName() + "?",
                    "Также будут изменены все связанные объекты!\nЭто действие необратимо!");

            //если пользователь выбрал ОК
            if (result.getResult().equals(ButtonType.OK)){
                db.editMagazine(resMagazine);
            }

            //сообщение о изменении записи
            information.messageInfo("Успешно!", "Запись обновлена", "");
        } else {
            if (MagazineEditNameField.getText().isEmpty()){
                shake magazineNameFieldAnim = new shake(MagazineEditNameField);
                magazineNameFieldAnim.playAnim();
            }
        }

    }

    /**
     * По нажатию "удалить" удаляется журнал и все с ним связанные объекты.
     * После нажатия на конпку выводится окно подтверждения.
     * После подтверждения запись удаляется из БД.
     * @param event
     */

    public void onClickDeleteButtonMagazine(ActionEvent event) {

        //вывод окна подтверждения удаления
        MessageWindow information = new MessageWindow();
        Alert result = information.messageOkCancel("Вы уверены?",
                "Вы хотите удалить журнал: " + magazineToEdit.getMagazinesName() + "?",
                "Также будут удалены все связанные объекты!\nЭто действие необратимо!");
        if (result.getResult().equals(ButtonType.OK)){
            //удаление из БД
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.deleteMagazine(magazineToEdit);

            //при успешном удалении сообщение
            information.messageInfo("Успешно!", "Журнал удален", "");
            deleteButtonMagazine.getScene().getWindow().hide();
        }

    }

    /**
     * поиск индекса массива по id журнала
     * @param list
     * @param id
     * @return
     */

    int returnIndexByObjectId(ObservableList<Publisher> list, int id){
        int index = 0;
        for (int i = 0; i < list.size(); ++i){
            if (list.get(i).getId() == id){
                index = list.indexOf(list.get(i));
                break;
            }
        }
        return index;
    }

    //конвертер
    StringConverter<Publisher> converterPublisherOutStringg = new StringConverter<Publisher>() {
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
