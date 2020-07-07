package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;
import sample.animations.shake;

import javax.swing.*;
import java.text.NumberFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class AddNewNumberController {

    @FXML
    private Button addNumber;

    @FXML
    private TextField NumberNumField;

    @FXML
    private Button closeWindow;

    @FXML
    private ComboBox<Publisher> comboboxMagazine;

    @FXML
    private DatePicker magazineNumDatePicker;

    @FXML
    private TextField NumbersFieldNumOfPages;

    @FXML
    void initialize (){
        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });

        //установка в combobox названий журналов
        DataBaseHandler db = new DataBaseHandler();
        ObservableList<Publisher> magazines = db.returnListFromDb(consts.MAGAZINES_TABLE,consts.MAGAZINES_NAME,consts.MAGAZINES_ID);
        comboboxMagazine.setItems(FXCollections.observableList(magazines));
        comboboxMagazine.setConverter(converterPublisherOutStringg);
        comboboxMagazine.getSelectionModel().select(0);

        //при нажатии накнопку добавить
        addNumber.setOnAction(event -> {
            String number = NumberNumField.getText().trim();
            String numOfPages = NumbersFieldNumOfPages.getText().trim();
            if (!number.equals("") && !numOfPages.equals("") && magazineNumDatePicker.getValue() != null){
                addNewNumber();

                //сообщение о добавлении записи
                MessageWindow information = new MessageWindow();
                information.messageInfo("Успешно!", "Запись добавлена", "");

                NumberNumField.clear();
                NumbersFieldNumOfPages.clear();
            } else {
                if (number.equals("")){
                    shake numberFieldAnim = new shake(NumberNumField);
                    numberFieldAnim.playAnim();
                }
                if (numOfPages.equals("")){
                    shake pagesFieldAnim = new shake(NumbersFieldNumOfPages);
                    pagesFieldAnim.playAnim();
                }
                if (magazineNumDatePicker.getValue() == null){
                    shake dateFieldAnim = new shake(magazineNumDatePicker);
                    dateFieldAnim.playAnim();
                }
            }

        });

    }

    //добавление номера в таблицу номеров
    public void addNewNumber (){
        DataBaseHandler db = new DataBaseHandler();
        String numNumber = NumberNumField.getText();
        String numOfPages = NumbersFieldNumOfPages.getText();
        Date date = convertToDateViaInstant(magazineNumDatePicker.getValue());
        Publisher magId = comboboxMagazine.getSelectionModel().getSelectedItem();

        //переводим что необходимо в integer
        Integer num = Integer.parseInt(numNumber);
        Integer numberOfPages = Integer.parseInt(numOfPages);

        //год из даты
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Integer year = calendar.get(calendar.YEAR);

        MagazineNumber number = new MagazineNumber(num,year,numberOfPages,date,magId.getId());
        //вызов функции из dbhandler
        db.addNumber(number);
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

    private static Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    //чтобы нельзя было ввести что-то кроме чисел
    public void typeNumMagazineField(KeyEvent keyEvent) {
        //char key = keyEvent.getKeyChar();

    }

    public void typeNumPagesField(KeyEvent keyEvent) {
    }
}
