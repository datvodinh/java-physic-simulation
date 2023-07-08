package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;

import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.object.Cube;
import model.object.Cylinder;


public class MainSimulationController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Slider KSlider;
    @FXML
    private Slider SSlider;
    @FXML
    private ImageView cube;
    @FXML
    private ImageView cylinder;
    @FXML
    private GridPane grid;
    @FXML
    private ImageView mainObject;
    @FXML
    private ImageView surface;
    @FXML
    private Slider forceSlider;
    @FXML
    private TextField forceLabel;

    AnimationController animation = new AnimationController();
    SurfaceController surfaceController = new SurfaceController();
    StatisticController statisticController = new StatisticController();
    DragDropController dragDropController = new DragDropController();
    ForceController forceController = new ForceController();
    StackPane statisticPane;
    Pane forcePane;
    Cube mainCube;
    Cylinder mainCylinder;

    KeyFrame frame;
    Timeline timeline;
    TranslateTransition surfaceTransition = new TranslateTransition();
    TranslateTransition backgroundTransition = new TranslateTransition();
    RotateTransition rotate = new RotateTransition();

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadStatistic();
        loadSurfacePanel();
        forceSlider.setDisable(true);
        dragDropController.initializeObject(cube, cylinder, mainObject, surface, this::onObjectInitialized);
        forceLabel.setText("0 N");
        forceSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                forceLabel.setText(round(forceSlider.getValue(),2) + "N");
                timeline.play();

                
                
            }
                        
        });

        forceSlider.setOnMouseReleased(event -> {
            forceSlider.setValue(0);
        });

    }
    
    public void onObjectInitialized() {
        if (dragDropController.is_cube) {
            mainCube = dragDropController.MainCube;
            mainCube.setVelocity(5);
            frame = new KeyFrame(Duration.seconds(0.2), event -> {
            animation.setMovement(surfaceTransition, surface, mainCube.getVelocity(), mainPane.getWidth());
            animation.setMovement(backgroundTransition, surface, mainCube.getVelocity() / 20, mainPane.getWidth());        
            
        });
    

        } else {
            mainCylinder = dragDropController.MainCylinder;
            frame = new KeyFrame(Duration.seconds(0.2), event -> {
            animation.setMovement(surfaceTransition, surface, mainCylinder.getVelocity(), mainPane.getWidth());
            animation.setMovement(backgroundTransition, surface, mainCylinder.getVelocity() / 20, mainPane.getWidth());        
            animation.setRotate(rotate, cylinder, mainCylinder.getVelocity());
        });
        }

        
        timeline = new Timeline(frame);
        forceSlider.setDisable(false);
    }


    


    public void loadStatistic() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Statistic.fxml"));

            statisticPane = (StackPane) loader.load();

            AnchorPane.setTopAnchor(statisticPane, 50.0);
            AnchorPane.setRightAnchor(statisticPane, 50.0);

            mainPane.getChildren().add(statisticPane);
            this.statisticController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadSurfacePanel() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/SurfacePanel.fxml"));

            forcePane = (Pane) loader.load();

            AnchorPane.setBottomAnchor(forcePane, 25.0);
            AnchorPane.setRightAnchor(forcePane, 100.0);

            mainPane.getChildren().add(forcePane);
            this.surfaceController = loader.getController();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    
}
