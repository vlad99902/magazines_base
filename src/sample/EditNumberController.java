package sample;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import sample.animations.shake;

public class EditNumberController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editNumberInfo;

    @FXML
    private TextField NumberNumFieldEdit;

    @FXML
    private Button closeWindow;

    @FXML
    private DatePicker magazineNumDatePickerEdit;

    @FXML
    private TextField NumbersFieldNumOfPagesEdit;

    @FXML
    private Button deleteNumber;

    @FXML
    private ComboBox<Publisher> magazinesComboBox;

    //объект для редактирования
    private MagazineNumber numberToEdit;



    @FXML
    void initialize() {
        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });
    }

    /**
     * Получение объекта из предыдущего контроллера для редактирования
     * @param numberToEdit
     */

    public void getNumberToEdit (MagazineNumber numberToEdit){
        this.numberToEdit = numberToEdit;
        setNumberInfoToWindow();
    }

    /**
     * устнавка полученных данных в форму
     */

    public void setNumberInfoToWindow(){
        //получение данных из БД
        DataBaseHandler db = new DataBaseHandler();
        MagazineNumber numberData = db.returnMagazineNumberId(numberToEdit);

        //присвоение данных полю класса
        numberToEdit = numberData;

        //установка данных номера на форму
        NumberNumFieldEdit.setText(numberData.getMagazineNumberNumber().toString());
        NumbersFieldNumOfPagesEdit.setText(numberData.getMagazineNumberPages().toString());

        //изменение формата даты в localDate
        LocalDate dateToDatePicker = convertToLocalDateViaInstant(numberData.getMagazineNumberDate());

        //установка в picker
        magazineNumDatePickerEdit.setValue(dateToDatePicker);

        //установка журналов в combobox
        ObservableList<Publisher> magazinesList = db.returnListFromDb(consts.MAGAZINES_TABLE, consts.MAGAZINES_NAME, consts.MAGAZINES_ID);
        magazinesComboBox.setItems(FXCollections.observableList(magazinesList));
        magazinesComboBox.setConverter(converterPublisherOutStringg);

        //поиск индекса массива по id журнала
        int index = returnIndexByObjectId(magazinesList, numberData.getMagazineId());
        magazinesComboBox.getSelectionModel().select(index);
    }

    /**
     * При нажатии на кнопку "Изменить" значения изменяются
     * @param event
     */

    @FXML
    void editNumberInfoEvent(ActionEvent event) {
        String number = NumberNumFieldEdit.getText().trim();
        String numOfPages = NumbersFieldNumOfPagesEdit.getText().trim();
        if (!number.equals("") && !numOfPages.equals("") && magazineNumDatePickerEdit.getValue() != null){

            //вывод окна подтверждения изменения
            MessageWindow information = new MessageWindow();
            Alert result = information.messageOkCancel("Вы уверены?",
                    "Вы хотите изменить номер: " + numberToEdit.getMagazineNumberNumber() + "?",
                    "Также будут изменены все связанные объекты!\nЭто действие необратимо!");

            //если пользователь выбрал ОК
            if (result.getResult().equals(ButtonType.OK)){
                updateNumberInfo();
            }

            //сообщение об изменении записи
            information.messageInfo("Успешно!", "Запись изменена", "");
        } else {
            if (number.equals("")){
                shake numberFieldAnim = new shake(NumberNumFieldEdit);
                numberFieldAnim.playAnim();
            }
            if (numOfPages.equals("")){
                shake pagesFieldAnim = new shake(NumbersFieldNumOfPagesEdit);
                pagesFieldAnim.playAnim();
            }
            if (magazineNumDatePickerEdit.getValue() == null){
                shake dateFieldAnim = new shake(magazineNumDatePickerEdit);
                dateFieldAnim.playAnim();
            }
        }
    }

    /**
     * Обновление информации о журнале
     */

    public void updateNumberInfo(){
        DataBaseHandler db = new DataBaseHandler();

        //получение данных из окна
        //id номера
        Integer numberId = numberToEdit.getMagazineNumberID();

        //id журнала
        Publisher magazine = magazinesComboBox.getSelectionModel().getSelectedItem();
        Integer magazineId = magazine.getId();

        //номер номера и количество страниц
        String numberNum = NumberNumFieldEdit.getText();
        String numOfPages = NumbersFieldNumOfPagesEdit.getText();
        //перевод в integer
        Integer numberNumInt = Integer.parseInt(numberNum);
        Integer numOfPagesInt = Integer.parseInt(numOfPages);

        //дата
        Date date = convertToDateViaInstant(magazineNumDatePickerEdit.getValue());

        //год из даты
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer year = calendar.get(calendar.YEAR);

        //создание объекта для редактирования
        MagazineNumber number = new MagazineNumber(numberId, numberNumInt, year, numOfPagesInt, date, magazineId);

        //вызов функции редактирования
        db.editNumber(number);
    }

    /**
     * удаление номера журнала и всех его статей
     * @param event
     */

    @FXML
    void deleteNumberEvent(ActionEvent event) {
        //вывод окна подтверждения удаления
        MessageWindow information = new MessageWindow();
        Alert result = information.messageOkCancel("Вы уверены?",
                "Вы хотите удалить номер: " + numberToEdit.getMagazineNumberNumber() + "?",
                "Также будут удалены все связанные статьи!\nЭто действие необратимо!");
        if (result.getResult().equals(ButtonType.OK)){
            //удаление из БД
            DataBaseHandler dbHandler = new DataBaseHandler();
            dbHandler.deleteNumber(numberToEdit);

            //при успешном удалении сообщение
            information.messageInfo("Успешно!", "Номер удален", "");
            deleteNumber.getScene().getWindow().hide();
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

    /**
     * конвертер java.sql.Date в LocalDate
     * @param dateToConvert
     * @return
     */
    private static LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    private static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
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
