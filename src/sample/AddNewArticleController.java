package sample;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import sample.animations.shake;

public class AddNewArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addArticle;

    @FXML
    private TextField ArticleNameField;

    @FXML
    private Button closeWindow;

    @FXML
    private ComboBox<Publisher> comboBoxMagazine;

    @FXML
    private ComboBox<Publisher> comboBoxNumber;

    @FXML
    private TextField AuthorNameField;

    @FXML
    private ComboBox<Publisher> comboBoxArticleTheme;

    @FXML
    private TextField EndPageField;

    @FXML
    private TextField BeginPageFiled;

    @FXML
    void initialize() {
        closeWindow.setOnAction(event -> {
            closeWindow.getScene().getWindow().hide();
        });

        //установка в выпадающие поля значений
        //журанлы
        DataBaseHandler db = new DataBaseHandler();
        ObservableList<Publisher> magazines = db.returnListFromDb(consts.MAGAZINES_TABLE,consts.MAGAZINES_NAME,consts.MAGAZINES_ID);
        comboBoxMagazine.setItems(FXCollections.observableList(magazines));
        comboBoxMagazine.setConverter(converterPublisherOutStringg);
        comboBoxMagazine.getSelectionModel().select(0);

        //номера
        Publisher number = comboBoxMagazine.getSelectionModel().getSelectedItem();
        Integer nextId = number.getId();
        ObservableList<Publisher> numbers = db.returnListFromDb(consts.NUMBERS_TABLE, consts.NUMBERS_NUMBER,
                consts.NUMBERS_ID, nextId, consts.NUMBERS_MAGAZINEID);
        comboBoxNumber.setItems(FXCollections.observableList(numbers));
        comboBoxNumber.setConverter(converterPublisherOutStringg);
        comboBoxNumber.getSelectionModel().select(0);

        //тематики
        ObservableList<Publisher> themes = db.returnListFromDb(consts.THEMES_TABLE,consts.THEMES_NAME,consts.THEMES_ID);
        comboBoxArticleTheme.setItems(FXCollections.observableList(themes));
        comboBoxArticleTheme.setConverter(converterPublisherOutStringg);
        comboBoxArticleTheme.getSelectionModel().select(0);

        addArticle.setOnAction(event -> {
            String articleName = ArticleNameField.getText().trim();
            String authorName = AuthorNameField.getText().trim();
            String begPage = BeginPageFiled.getText().trim();
            String endPage = EndPageField.getText().trim();

            if (!articleName.equals("") && !authorName.equals("") &&
            !begPage.equals("") && !endPage.equals("")){
                addNewArticle();

                //сообщение о добавлении записи
                MessageWindow information = new MessageWindow();
                information.messageInfo("Успешно!", "Запись добавлена", "");

                ArticleNameField.clear();
                AuthorNameField.clear();
                BeginPageFiled.clear();
                EndPageField.clear();
            }  else {
                if (articleName.equals("")) {
                    shake articleNameAnim = new shake(ArticleNameField);
                    articleNameAnim.playAnim();
                }
                if (authorName.equals("")) {
                    shake authorNameAnim = new shake(AuthorNameField);
                    authorNameAnim.playAnim();
                }
                if (begPage.equals("")) {
                    shake begPageAnim = new shake(BeginPageFiled);
                    begPageAnim.playAnim();
                }
                if (endPage.equals("")) {
                    shake endPageAnim = new shake(EndPageField);
                    endPageAnim.playAnim();
                }
            }

        });

    }

    //добавление статьи

    public void addNewArticle(){
        DataBaseHandler db = new DataBaseHandler();
        String name = ArticleNameField.getText();
        String author = AuthorNameField.getText();
        String begPage = BeginPageFiled.getText();
        String endPage = EndPageField.getText();
        Publisher theme = comboBoxArticleTheme.getSelectionModel().getSelectedItem();
        Publisher number = comboBoxNumber.getSelectionModel().getSelectedItem();

        //конвертирование значений
        Integer begPageInt = Integer.parseInt(begPage);
        Integer endPageInt = Integer.parseInt(endPage);

        NumberArticle article = new NumberArticle(name,author,begPageInt,endPageInt,number.getId(),theme.getId());

        //вызов функции из db handler
        db.addArticle(article);
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

    public void refreshNumbers(MouseEvent mouseEvent) {
        DataBaseHandler db = new DataBaseHandler();
        Publisher number = comboBoxMagazine.getSelectionModel().getSelectedItem();
        Integer nextId = number.getId();
        ObservableList<Publisher> numbers = db.returnListFromDb(consts.NUMBERS_TABLE, consts.NUMBERS_NUMBER,
                consts.NUMBERS_ID, nextId, consts.NUMBERS_MAGAZINEID);
        comboBoxNumber.setItems(FXCollections.observableList(numbers));
        comboBoxNumber.setConverter(converterPublisherOutStringg);
        comboBoxNumber.getSelectionModel().select(0);
    }
}
