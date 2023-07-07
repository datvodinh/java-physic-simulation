package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import model.Surface.Surface;
import controller.AnimationController;
public class MainSimulationController implements Initializable {
    @FXML
    private Slider kineticSlider;
    @FXML
    private Slider staticSlider;
    private Surface surface = new Surface();
    private AnimationController animation = new AnimationController();
    private SurfaceController surfaceController = new SurfaceController(kineticSlider,staticSlider,surface);
    private StatisticController statisticController = new StatisticController();
    private InputController inputController = new InputController();
    private DragDropController dragDropController = new DragDropController();
    private ForceController forceController = new ForceController();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // AnimationController animation = new AnimationController();
        // TranslateTransition surfaceTransition = new TranslateTransition();
        // TranslateTransition backgroundTransition = new TranslateTransition();

    }
    
}
