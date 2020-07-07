package sample;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.webkit.dom.NamedNodeMapImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import sample.animations.SceneChanging;
import sample.animations.shake;


public class HomeController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Tab tableTab1;

    @FXML
    private TableView<MagazinesTable> tableMainMagazines;

    @FXML
    private TableColumn<MagazinesTable, Integer> tableColumnID;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnMagazineName;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnPublisher;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnMagazineTheme;

    @FXML
    private Tab tableTab2;

    @FXML
    private Tab tableTab3;

    @FXML
    private Button refresh_button;

    @FXML
    private Text userNameLeftAngle;

    @FXML
    private Button AddButton;

    @FXML
    private TableView<MagazineNumber> tableMagazinesNumbers;

    @FXML
    private TableColumn<MagazineNumber, String> tableColumnID1;

    @FXML
    private TableColumn<MagazineNumber, String> tableNumColumnPages;

    @FXML
    private TableColumn<MagazineNumber, String> tableNumColumnYear;

    @FXML
    private TableColumn<MagazineNumber, String> tableNumColumnRelDate;

    @FXML
    private Button refreshNumTableButton;

    @FXML
    private Button AddNumButton;

    @FXML
    private ComboBox<Publisher> comboBoxMagazine;

    @FXML
    private TableView<NumberArticle> tableArticles;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleName;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleAuthor;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleBeginPage;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleEndPage;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleTheme;

    @FXML
    private Button refreshArticlesButton;

    @FXML
    private Button AddArticle;

    @FXML
    private ComboBox<Publisher> comboBoxNumberName; //на вкладке статей

    @FXML
    private ComboBox<Publisher> comboBoxMagazineName; //на вкладке статей

    //вкладка полного вывода статей

    @FXML
    private TableView<NumberArticle> tableArticlesFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnMagazineArticlesFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnNumbersArticlesFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleNameFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleAuthorFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleBeginPageFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleEndPageFull;

    @FXML
    private TableColumn<NumberArticle, String> tableColumnArticleThemeFull;

    @FXML
    private Button refreshArticlesButtonFull;

    @FXML
    private TextField filterArticlesField;

    @FXML
    private TableView<Publisher> tableViewPublishers;

    @FXML
    private TableColumn<Publisher, String> tableColumnPublishers;

    @FXML
    private TableColumn<Publisher, String> tableColumnPublishersNumOfMagazines;

    @FXML
    private Button refreshPublishersButtonFull;

    //таблица авторов
    @FXML
    private TableView<Authors> tableAuthors;

    @FXML
    private TableColumn<Authors, String> tableAuthorsColumnName;

    @FXML
    private TableColumn<Authors, String> tableColumnAuthorsNumOfArticles;

    @FXML
    private TableColumn<Authors, String> tableColumnAuthorsNumOfMagazines;

    @FXML
    private TableColumn<Authors, String> tableColumnAuthorsThemes;

    @FXML
    private TableColumn<Authors, String> tableColumnAuthorsNumOfPages;

    //таблица datemag
    @FXML
    private TableView<MagazinesTable> tableDateMag;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnDateMagName;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnDateMagArticleNum;

    @FXML
    private TableColumn<MagazinesTable, String> tableColumnDateMagSumPages;

    @FXML
    private Button refreshMagazinesFiltr;

    @FXML
    private ComboBox<Publisher> comboBoxThemesDatesMag;

    @FXML
    private DatePicker datePickerBeginDateDateMag;

    @FXML
    private DatePicker datePickerEndDateDateMag;

    @FXML
    private Button refreshAuthorsTable;

    @FXML
    private Button EditDirectoryes;

    @FXML
    void initialize() {

        //изменение надписи в пустой таблице
        tableMagazinesNumbers.setPlaceholder(new Label("По вашему запросу ничего не найдено"));
        tableArticles.setPlaceholder(new Label("По вашему запросу ничего не найдено"));
        tableMainMagazines.setPlaceholder(new Label("Нажмите Обновить, чтобы вывести таблицу"));
        tableArticlesFull.setPlaceholder(new Label("По вашему запросу ничего не найдено"));
        tableAuthors.setPlaceholder(new Label("Нажмите Обновить, чтобы вывести таблицу"));
        tableViewPublishers.setPlaceholder(new Label("Нажмите Обновить, чтобы вывести таблицу"));
        tableDateMag.setPlaceholder(new Label("По вашему запросу ничего не найдено"));

        AddButton.setOnAction(event -> {
            SceneChanging changeScene = new SceneChanging();
            changeScene.SceneChanging("/sample/AddNewMagazine.fxml", AddButton);
            changeScene.openNewSceneWithoutClosing();
        });

        AddNumButton.setOnAction(event -> {
            SceneChanging changeScene = new SceneChanging();
            changeScene.SceneChanging("/sample/AddNewNumber.fxml", AddButton);
            changeScene.openNewSceneWithoutClosing();
        });

        AddArticle.setOnAction(event -> {
            SceneChanging changeScene = new SceneChanging();
            changeScene.SceneChanging("/sample/AddNewArticle.fxml", AddButton);
            changeScene.openNewSceneWithoutClosing();
        });

        //добавление в combobox на вкладке номеров журнала
        DataBaseHandler dbHandler = new DataBaseHandler();
        ObservableList<Publisher> magazines = dbHandler.returnListFromDb(consts.MAGAZINES_TABLE, consts.MAGAZINES_NAME, consts.MAGAZINES_ID);
        comboBoxMagazine.setItems(FXCollections.observableList(magazines));
        comboBoxMagazine.setConverter(converterPublisherOutStringg);
        comboBoxMagazine.getSelectionModel().select(0);

        //добавление в combobox на вкладке статей
        //добавление в combobox журналов
        comboBoxMagazineName.setItems(FXCollections.observableList(magazines));
        comboBoxMagazineName.setConverter(converterPublisherOutStringg);
        comboBoxMagazineName.getSelectionModel().select(0);

        //добавление в combobox номеров
        Publisher number = comboBoxMagazineName.getSelectionModel().getSelectedItem();
        Integer nextId = number.getId();
        ObservableList<Publisher> numbers = dbHandler.returnListFromDb(consts.NUMBERS_TABLE, consts.NUMBERS_NUMBER,
                consts.NUMBERS_ID, nextId, consts.NUMBERS_MAGAZINEID);

            comboBoxNumberName.setItems(FXCollections.observableList(numbers));
            comboBoxNumberName.setConverter(converterPublisherOutStringg);
            comboBoxNumberName.getSelectionModel().select(0);



        //добавление в combobox на вкладке журналов по дате тем
        ObservableList<Publisher> themes = dbHandler.returnListFromDb(consts.THEMES_TABLE, consts.THEMES_NAME, consts.THEMES_ID);
        comboBoxThemesDatesMag.setItems(FXCollections.observableList(themes));
        comboBoxThemesDatesMag.setConverter(converterPublisherOutStringg);
        comboBoxThemesDatesMag.getSelectionModel().select(0);
    }

    //изменение справчоников
    public void editDirectoryesActionEvent(ActionEvent event) {
        SceneChanging changeScene = new SceneChanging();
        changeScene.SceneChanging("/sample/AddNewTheme.fxml", AddButton);
        changeScene.openNewSceneWithoutClosing();
    }

    //вывод всех журналов в таблицу
    @FXML
    void loadMagazineFromDb(ActionEvent event) {
        cellTableMainMagazines();
        DataBaseHandler db = new DataBaseHandler();
        tableMainMagazines.setItems(db.returnMagazinesTableFromDb());
    }

    //вывод номеров журналов по выбору журнала
    @FXML
    void loadNumMagazineFromDb(ActionEvent event) {
        cellTableMagazinesNumbers();
        DataBaseHandler db = new DataBaseHandler();
        Publisher magazine = comboBoxMagazine.getSelectionModel().getSelectedItem();
        tableMagazinesNumbers.setItems(db.returnMagazinesNumbersTableFromDb(magazine.getId()));
    }

    //вывод в таблицу статей по выборе журнала и номера
    public void refreshArticles(ActionEvent event) {
        cellTableMagazinesNumbersArticle();
        DataBaseHandler db = new DataBaseHandler();
        Publisher number = comboBoxNumberName.getSelectionModel().getSelectedItem();
        tableArticles.setItems(db.returnMagazinesNumberArticleTableFromDb(number.getId()));
    }

    //вывод в полную таблицу статей по подстроке
    public void refreshArticlesFull(ActionEvent event) {
        cellTableMagazinesNumbersArticle(tableColumnMagazineArticlesFull, tableColumnNumbersArticlesFull,
                tableColumnArticleNameFull, tableColumnArticleAuthorFull,
                tableColumnArticleBeginPageFull, tableColumnArticleEndPageFull,
                tableColumnArticleThemeFull);
        DataBaseHandler db = new DataBaseHandler();
        String subString = filterArticlesField.getText().trim();
        tableArticlesFull.setItems(db.returnMagazinesNumberArticleTableFromDb(subString));
    }

    //вывод в таблицу паблишеров паблишеров и сумма их журналов
    public void refreshPublishersFull(ActionEvent event) {
        cellPublishersTable(tableColumnPublishers, tableColumnPublishersNumOfMagazines);
        DataBaseHandler db = new DataBaseHandler();
        tableViewPublishers.setItems(db.returnListFromDb(consts.PUBLISHERS_TABLE));
    }

    //вывод в таблицу авторов авторов и инфы об их статьях
    public void refreshAuthorsTableEvent(ActionEvent event) {
        cellAuthorsTable(tableAuthorsColumnName, tableColumnAuthorsNumOfArticles,
                tableColumnAuthorsNumOfMagazines, tableColumnAuthorsThemes,
                tableColumnAuthorsNumOfPages);
        DataBaseHandler db = new DataBaseHandler();
        tableAuthors.setItems(db.returnAuthorsFromDb());
    }

    //вывод в таблицу журналов по дате
    public void refreshMagazinesFilterEvent(ActionEvent event) {
        cellMagazineTableByAuthors(tableColumnDateMagName,tableColumnDateMagArticleNum,
                tableColumnDateMagSumPages);
        DataBaseHandler db = new DataBaseHandler();

        if (datePickerBeginDateDateMag.getValue() != null &&
                datePickerEndDateDateMag.getValue() != null){
            //формат даты
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            //получение инофрмации из date и перевод ее в string
            LocalDate dateOfBeg = datePickerBeginDateDateMag.getValue();
            Date begDate = convertToDateViaInstant(dateOfBeg);
            String strBegDate = dateFormat.format(begDate);
            strBegDate = "\"" + strBegDate + "\"";

            LocalDate dateOfEnd = datePickerEndDateDateMag.getValue();
            Date endDate = convertToDateViaInstant(dateOfEnd);
            String strEndDate = dateFormat.format(endDate);
            strEndDate = "\"" + strEndDate + "\"";

            //получени из комбобокса тема
            Publisher theme = comboBoxThemesDatesMag.getSelectionModel().getSelectedItem();
            Integer themeId = theme.getId();

            //метод из dbhandler
            tableDateMag.setItems(db.returnMagazinesByDateFromDb(themeId,strBegDate,strEndDate));
        } else {
            if (datePickerBeginDateDateMag.getValue() == null){
                shake dateBeginFieldAnim = new shake(datePickerBeginDateDateMag);
                dateBeginFieldAnim.playAnim();
            }
            if (datePickerEndDateDateMag.getValue() == null){
                shake dateEndFieldAnim = new shake(datePickerEndDateDateMag);
                dateEndFieldAnim.playAnim();
            }
        }

    }

    //установка таблицы журналов по дате
    public void cellMagazineTableByAuthors(TableColumn<MagazinesTable, String> name,
                                           TableColumn<MagazinesTable, String> numOfNumbers,
                                           TableColumn<MagazinesTable, String> numOfPages){
        name.setCellValueFactory(new PropertyValueFactory<>("magazinesName"));
        numOfNumbers.setCellValueFactory(new PropertyValueFactory<>("magazinesPublisherId"));
        numOfPages.setCellValueFactory(new PropertyValueFactory<>("magazinesThemesId"));
    }

    //установка таблицы авторов
    public void cellAuthorsTable(TableColumn<Authors, String> name,
                                 TableColumn<Authors, String> numOfArt,
                                 TableColumn<Authors, String> numOfMag,
                                 TableColumn<Authors, String> numOfThe,
                                 TableColumn<Authors, String> numOfPages){
        name.setCellValueFactory(new PropertyValueFactory<>("authorsName"));
        numOfArt.setCellValueFactory(new PropertyValueFactory<>("authorsNumOfArticles"));
        numOfMag.setCellValueFactory(new PropertyValueFactory<>("authorsNumOfMagazines"));
        numOfThe.setCellValueFactory(new PropertyValueFactory<>("authorsNumOfThemes"));
        numOfPages.setCellValueFactory(new PropertyValueFactory<>("authorsNumOfPages"));
    }

    //установка таблицы паблишеров
    public void cellPublishersTable(TableColumn<Publisher, String> name,
                                    TableColumn<Publisher, String> sumOfMagazines){
        name.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        sumOfMagazines.setCellValueFactory(new PropertyValueFactory<>("sumOfMagazines"));
    }

    //установка таблицы жураналов
    public void cellTableMainMagazines() {
        tableColumnID.setCellValueFactory(new PropertyValueFactory<>("magazinesId"));
        tableColumnMagazineName.setCellValueFactory(new PropertyValueFactory<>("magazinesName"));
        tableColumnPublisher.setCellValueFactory(new PropertyValueFactory<>("magazinesPublisher"));
        tableColumnMagazineTheme.setCellValueFactory(new PropertyValueFactory<>("magazinesTheme"));
    }

    //установка таблицы номеров журналов
    public void cellTableMagazinesNumbers() {
        tableColumnID1.setCellValueFactory(new PropertyValueFactory<>("magazineNumberNumber"));
        tableNumColumnPages.setCellValueFactory(new PropertyValueFactory<>("magazineNumberPages"));
        tableNumColumnYear.setCellValueFactory(new PropertyValueFactory<>("magazineNumberYear"));
        tableNumColumnRelDate.setCellValueFactory(new PropertyValueFactory<>("magazineNumberDate"));
    }

    //установка таблицы статей
    public void cellTableMagazinesNumbersArticle() {
        tableColumnArticleName.setCellValueFactory(new PropertyValueFactory<>("numberArticleName"));
        tableColumnArticleAuthor.setCellValueFactory(new PropertyValueFactory<>("numberArticleAuthor"));
        tableColumnArticleBeginPage.setCellValueFactory(new PropertyValueFactory<>("numberArticleBeginPage"));
        tableColumnArticleEndPage.setCellValueFactory(new PropertyValueFactory<>("numberArticleEndPage"));
        tableColumnArticleTheme.setCellValueFactory(new PropertyValueFactory<>("numberArticleTheme"));
    }

    //установка таблицы статей (перегрузка предыдущего метода, удалить предыдующую)
    public void cellTableMagazinesNumbersArticle(TableColumn<NumberArticle, String> magazineName,
                                                 TableColumn<NumberArticle, String> numberName,
                                                 TableColumn<NumberArticle, String> name,
                                                 TableColumn<NumberArticle, String> author,
                                                 TableColumn<NumberArticle, String> beginPage,
                                                 TableColumn<NumberArticle, String> endPage,
                                                 TableColumn<NumberArticle, String> theme) {
        magazineName.setCellValueFactory(new PropertyValueFactory<>("numberArticleMagazineName"));
        numberName.setCellValueFactory(new PropertyValueFactory<>("numberArticleNumber"));
        name.setCellValueFactory(new PropertyValueFactory<>("numberArticleName"));
        author.setCellValueFactory(new PropertyValueFactory<>("numberArticleAuthor"));
        beginPage.setCellValueFactory(new PropertyValueFactory<>("numberArticleBeginPage"));
        endPage.setCellValueFactory(new PropertyValueFactory<>("numberArticleEndPage"));
        theme.setCellValueFactory(new PropertyValueFactory<>("numberArticleTheme"));
    }

    //можно сохранять чтобы список не сбрасывался при нажатии
    public void RefreshList(javafx.scene.input.MouseEvent mouseEvent) {
       /* DataBaseHandler dbHandler = new DataBaseHandler();
        ObservableList<Publisher> magazines = dbHandler.returnListFromDb(consts.MAGAZINES_TABLE,consts.MAGAZINES_NAME,consts.MAGAZINES_ID);
        comboBoxMagazine.setItems(FXCollections.observableList(magazines));
        comboBoxMagazine.setConverter(converterPublisherOutStringg);
        comboBoxMagazine.getSelectionModel().select(0);*/
    }


    //переделать!
    public void RefreshComboBoxListNumbers(MouseEvent mouseEvent) {
        //добавление в combobox номеров
        Publisher number = comboBoxMagazineName.getSelectionModel().getSelectedItem();
        Integer nextId = number.getId();
        DataBaseHandler dbHandler = new DataBaseHandler();
        ObservableList<Publisher> numbers = dbHandler.returnListFromDb(consts.NUMBERS_TABLE, consts.NUMBERS_NUMBER,
                consts.NUMBERS_ID, nextId, consts.NUMBERS_MAGAZINEID);
        comboBoxNumberName.setItems(FXCollections.observableList(numbers));
        comboBoxNumberName.setConverter(converterPublisherOutStringg);
        comboBoxNumberName.getSelectionModel().select(0);
    }

    public void RefreshListComboBoxMagazineName(MouseEvent mouseEvent) {
    }

    //вытаскиваем из таблицы объект для редактирования
    public void loadMagazineRowFromTable(MouseEvent mouseEvent){
        MagazinesTable magazine = tableMainMagazines.getSelectionModel().getSelectedItem();

        //открываем окно редактирования
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/ChangeMagazineInfo.fxml"));
            Parent root = (Parent) loader.load();
            EditMagazineController con = loader.getController();
            con.getMagazineToEdit(magazine);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Получение объекта для редактирования из таблицы номеров
     * @param mouseEvent
     */

    public void mouseClickedOnNumbersTableEvent(MouseEvent mouseEvent) {
        MagazineNumber numberToEdit = tableMagazinesNumbers.getSelectionModel().getSelectedItem();
        //получение id журнала к которому относится статья
        numberToEdit.setMagazineId(comboBoxMagazine.getSelectionModel().getSelectedItem().getId());
        //открытие окна редактирования
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/EditNumber.fxml"));
            Parent root = (Parent) loader.load();
            EditNumberController con = loader.getController();
            con.getNumberToEdit(numberToEdit);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * получение объекта из таблицы статей
     * @param mouseEvent
     */

    public void articleOnMouseClickedEvent(MouseEvent mouseEvent) {
        NumberArticle articleToEdit = tableArticles.getSelectionModel().getSelectedItem();
        //получение id номера к которому относится статья
        articleToEdit.setNumberArticleIdNumber(comboBoxNumberName.getSelectionModel().getSelectedItem().getId());
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



    public void UserNameShow(String userName) {
        userNameLeftAngle.setText(userName);
    }



}

