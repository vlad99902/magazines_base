package sample.animations;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import sample.EditMagazineController;
import sample.MagazinesTable;

import java.io.IOException;

public class SceneChanging {
    private String window;
    private Button button;

    public void SceneChanging(String window, Button button){
        this.window = window;
        this.button = button;
    }

    public void SceneChanging(String window) {
        this.window = window;
    }

    public void openNewScene (){
        button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    public void openNewSceneWithoutClosing (){
        //button.getScene().getWindow().hide();

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));

        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    //новое окно с передачей данных
    /*
    public void openNewSceneWithReturnData (MagazinesTable magazine){
        try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(window));
        Parent root = loader.load();
        EditMagazineController con = new EditMagazineController();
        con.getMagazineToEdit(magazine);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
