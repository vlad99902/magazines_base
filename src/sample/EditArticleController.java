package sample;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class EditArticleController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button editArticleButton;

    @FXML
    private TextField ArticleNameField;

    @FXML
    private Button closeWindow;

    @FXML
    private ComboBox<?> comboBoxMagazine;

    @FXML
    private ComboBox<?> comboBoxNumber;

    @FXML
    private TextField AuthorNameField;

    @FXML
    private TextField EndPageField;

    @FXML
    private TextField BeginPageFiled;

    @FXML
    private ComboBox<?> comboBoxArticleTheme;

    @FXML
    private Button deleteAticleButton;

    @FXML
    void deleteArticleEvent(ActionEvent event) {

    }

    @FXML
    void editAticle(ActionEvent event) {

    }

    @FXML
    void refreshNumbers(MouseEvent event) {

    }

    @FXML
    void initialize() {

    }
}
