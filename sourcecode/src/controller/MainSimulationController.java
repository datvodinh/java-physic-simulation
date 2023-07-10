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
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.force.AppliedForce;
import model.force.ForceSimulation;
import model.force.FrictionForce;
import model.object.Cube;
import model.object.Cylinder;
import model.surface.Surface;


public class MainSimulationController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView cube, cylinder, mainObject, surface, background;
    @FXML
    private GridPane grid;
    @FXML
    private Slider forceSlider;
    @FXML
    private TextField forceLabel;
    @FXML
    private Button increaseForce, decreaseForce;

    @FXML
    private SurfaceController surfaceController;
    @FXML
    private StatisticController statisticController;
    AnimationController animation = new AnimationController();
    DragDropController dragDropController = new DragDropController();

    Pane statisticPane;
    Pane forcePane;

    Cube mainCube;
    Cylinder mainCylinder;
    ForceSimulation forceSimulation;
    Surface mainSurface = new Surface();
    AppliedForce appliedForce = new AppliedForce(0);
    FrictionForce frictionForce;

    KeyFrame frame;
    Timeline timeline;
    TranslateTransition surfaceTransition = new TranslateTransition();
    TranslateTransition backgroundTransition = new TranslateTransition();
    RotateTransition rotate = new RotateTransition();

    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadStatistic();
        loadSurfacePanel();
        disableForceController(true);


        dragDropController.initializeObject(cube, cylinder, mainObject, surface, () -> {
            try {
                onObjectInitialized();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        forceLabel.setText("0 N");
        forceSlider.valueProperty().addListener(new ChangeListener<Number>() {
        
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                forceLabel.setText(round(forceSlider.getValue(), 2) + "N");
                
            }
                        
        });

        forceSlider.setOnMouseReleased(event -> {
            forceSlider.setValue(0);
        });





    }
    
    public void onObjectInitialized() throws Exception {
        if (timeline != null) {
            timeline.pause();
            timeline.getKeyFrames().clear();
        }

        if (rotate != null) {
            rotate.pause();
            mainObject.setRotate(0);
        }
        if (dragDropController.is_cube) {
            mainCube = dragDropController.MainCube;
            forceSimulation = new ForceSimulation(mainCube, mainSurface, appliedForce);
            frame = new KeyFrame(Duration.seconds(0.2), event -> {
                animation.setMovement(surfaceTransition, surface, mainCube.getVelocity(), mainPane.getWidth());
                animation.setMovement(backgroundTransition, background, mainCube.getVelocity() / 20,
                        mainPane.getWidth());
                try {
                    mainSurface.setStaticCoef(surfaceController.getSSlider().getValue());
                    mainSurface.setKineticCoef(surfaceController.getKSlider().getValue());
                    forceSimulation.setAppliedForce(forceSlider.getValue());
                    forceSimulation.setFrictionForce();
                    forceSimulation.setNetForce();
                    forceSimulation.applyForceInTime(0.2);
                    if (statisticController.getMassBox().isSelected()){
                        statisticController.getMassText().setText(Double.toString(mainCube.getMass()));
                    }
                    else{
                        statisticController.getMassText().setText(null);
                    }
                    if (statisticController.getVelocityBox().isSelected()){
                        statisticController.getVelocityText().setText(Double.toString(mainCube.getVelocity()));
                    }
                    else{
                        statisticController.getVelocityText().setText(null);
                    }
                    if (statisticController.getAccelerationBox().isSelected()){
                        statisticController.getAccelerationText().setText(Double.toString(mainCube.getAcceleration()));
                    }
                    else{
                        statisticController.getAccelerationText().setText(null);
                    }
                    if (statisticController.getPositionBox().isSelected()){
                        statisticController.getPositionText().setText((Double.toString(mainCube.getPosition())));
                    }
                    else{
                        statisticController.getPositionText().setText(null);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                

            });
            

        } else {
            mainCylinder = dragDropController.MainCylinder;

            forceSimulation = new ForceSimulation(mainCylinder, mainSurface, appliedForce);

            frame = new KeyFrame(Duration.seconds(0.2), event -> {
                animation.setMovement(surfaceTransition, surface, mainCylinder.getVelocity(), mainPane.getWidth());
                animation.setMovement(backgroundTransition, background, mainCylinder.getVelocity() / 20,mainPane.getWidth());
                animation.setRotate(rotate, mainObject, mainCylinder.getVelocity());
                try {
                    mainSurface.setKineticCoef(surfaceController.getKSlider().getValue());
                    mainSurface.setStaticCoef(surfaceController.getSSlider().getValue());
                    forceSimulation.setAppliedForce(forceSlider.getValue());
                    forceSimulation.setFrictionForce();
                    forceSimulation.setNetForce();
                    forceSimulation.applyForceInTime(0.2);
                    if (statisticController.getMassBox().isSelected()){
                        statisticController.getMassText().setText(Double.toString(mainCylinder.getMass()));
                    }
                    else{
                        statisticController.getMassText().setText(null);
                    }
                    if (statisticController.getVelocityBox().isSelected()){
                        statisticController.getVelocityText().setText(Double.toString(mainCylinder.getVelocity()));
                    }
                    else{
                        statisticController.getVelocityText().setText(null);
                    }
                    if (statisticController.getAccelerationBox().isSelected()){
                        statisticController.getAccelerationText().setText(Double.toString(mainCylinder.getAcceleration()));
                    }
                    else{
                        statisticController.getAccelerationText().setText(null);
                    }
                    if (statisticController.getPositionBox().isSelected()){
                        statisticController.getPositionText().setText(Double.toString(mainCylinder.getPosition()));
                    }
                    else{
                        statisticController.getPositionText().setText(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        


        timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        disableForceController(false);
        // cube.setDisable(true);
        // cylinder.setDisable(true);
    }
    
    public void disableForceController(boolean b) {
        forceSlider.setDisable(b);
        increaseForce.setDisable(b);
        decreaseForce.setDisable(b);
        surfaceController.disableFrictionSlider(b);
    }


    public void loadStatistic() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/StatsPanel.fxml"));

            statisticPane = (Pane) loader.load();
            statisticPane.setScaleX(0.9);
            statisticPane.setScaleY(0.9);

            AnchorPane.setTopAnchor(statisticPane, 25.0);
            AnchorPane.setRightAnchor(statisticPane, 35.0);

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

    public void increaseForce() {
        forceSlider.setValue(forceSlider.getValue() + 50);
    }
    
    public void decreaseForce() {
        forceSlider.setValue(forceSlider.getValue() - 50);
    }

    public void reset() {
        mainObject.setImage(null);
        forceSlider.setValue(0);
        surfaceController.reset();
        statisticController.reset();
        cube.setVisible(true);
        cylinder.setVisible(true);
        if (timeline!=null) {timeline.pause();}
        surfaceTransition.pause();
        backgroundTransition.pause();
        rotate.pause();
        if (dragDropController.is_cylinder) {
            rotate.pause();
        }
        timeline.getKeyFrames().clear();
        cube.setDisable(false);
        cylinder.setDisable(false);

    }
    
    public void play() {
        surfaceTransition.play();
        backgroundTransition.play();
        if (dragDropController.is_cylinder) {
            rotate.play();
        }
        if (timeline!=null)
        {timeline.play();}
    }

    public void pause() {
        surfaceTransition.pause();
        backgroundTransition.pause();
        if (dragDropController.is_cylinder) {
            rotate.pause();
        }
        if (timeline!=null)
        {timeline.pause();}
    }

    
}
