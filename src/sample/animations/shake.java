package sample.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;
import sample.HomeController;

public class shake{
    private TranslateTransition tt;

    public shake (Node node){
        tt = new TranslateTransition(Duration.millis(80),node);
        tt.setFromX(-8f);
        tt.setByX(8f);
        tt.setCycleCount(5);
        tt.setAutoReverse(true);


    }

    public void playAnim(){
        tt.playFromStart();
    }
}
