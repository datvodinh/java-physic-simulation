package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.Initializable;
import controller.AnimationController;
public class MainSimulationController implements Initializable {
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        AnimationController animation = new AnimationController();
        TranslateTransition surfaceTransition = new TranslateTransition();
        TranslateTransition backgroundTransition = new TranslateTransition(); 
        

    }
    
}
