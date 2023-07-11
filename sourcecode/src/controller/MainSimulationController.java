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
import javafx.scene.control.Label;
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
import model.object.MainObject;
import model.surface.Surface;


public class MainSimulationController implements Initializable {
    
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView cube, cylinder, mainObject, surfaceView,surface, cloud1, cloud2, cloud3, cloud4;
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
    private CheckBoxController checkBoxController;
    @FXML
    private StatsController statsController;
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
    TranslateTransition cloudTransition1 = new TranslateTransition();
    TranslateTransition cloudTransition2 = new TranslateTransition();
    TranslateTransition cloudTransition3 = new TranslateTransition();
    TranslateTransition cloudTransition4 = new TranslateTransition();

    RotateTransition rotate = new RotateTransition();

    Label mass = new Label();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // mainPane.getChildren().add(mass);
        loadCheckBox();
        loadStats();
        loadSurfacePanel();
        disableForceController(true);


        mainPane.getChildren().add(mass);
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
    
    private void loadStats() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/Stats.fxml"));

            statisticPane = (Pane) loader.load();
            statisticPane.setScaleX(1.0);
            statisticPane.setScaleY(1.0);

            AnchorPane.setTopAnchor(statisticPane, 20.0);
            AnchorPane.setLeftAnchor(statisticPane, 10.0);

            mainPane.getChildren().add(statisticPane);
            this.statsController = loader.getController();
            

        } catch (IOException e) {
            e.printStackTrace();
        }
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
                animation.setMovement(cloudTransition1, cloud1, mainCube.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition2, cloud2, mainCube.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition3, cloud3, mainCube.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition4, cloud4, mainCube.getVelocity() / 50,mainPane.getWidth());
                try {
                    forceSimulation.getSur().setStaticCoef(surfaceController.getSSlider().getValue());
                    forceSimulation.getSur().setKineticCoef(surfaceController.getKSlider().getValue());
                    forceSimulation.setAppliedForce(forceSlider.getValue());
                    forceSimulation.setFrictionForce();
                    forceSimulation.setNetForce();
                    forceSimulation.applyForceInTime(0.2);
                    showStats();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                

            });
            

        } else { //Cylinder
            mainCylinder = dragDropController.MainCylinder;

            forceSimulation = new ForceSimulation(mainCylinder, mainSurface, appliedForce);

            frame = new KeyFrame(Duration.seconds(0.2), event -> {
                animation.setMovement(surfaceTransition, surface, mainCylinder.getVelocity(), mainPane.getWidth());
                animation.setMovement(cloudTransition1, cloud1, mainCylinder.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition2, cloud2, mainCylinder.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition3, cloud3, mainCylinder.getVelocity() / 50, mainPane.getWidth());
                animation.setMovement(cloudTransition4, cloud4, mainCylinder.getVelocity() / 50,mainPane.getWidth());
                animation.setRotate(rotate, mainObject, mainCylinder.getVelocity());
                try {
                    forceSimulation.getSur().setKineticCoef(surfaceController.getKSlider().getValue());
                    forceSimulation.getSur().setStaticCoef(surfaceController.getSSlider().getValue());
                    forceSimulation.setAppliedForce(forceSlider.getValue());
                    forceSimulation.setFrictionForce();
                    forceSimulation.setNetForce();
                    forceSimulation.applyForceInTime(0.2);
                    showStats();
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
    
    // mainPane.getChildren().add(mass);
    
    private void showStats() {
        if (dragDropController.is_cube){ //cube
            if (checkBoxController.getMassBox().isSelected()){
                //show mass
                mass.setText(Double.toString(mainCube.getMass()));
                mass.setLayoutX(50);
                mass.setLayoutY(50);
                mass.setVisible(true);
            }
            else{
                mass.setVisible(false);
            }
            statsController.getVelocityText().setText(Double.toString(round(dragDropController.MainCube.getVelocity(),3)));
            statsController.getAccelerationText().setText(Double.toString(round(dragDropController.MainCube.getAcceleration(),3)));
            statsController.getPositionText().setText(Double.toString(round(dragDropController.MainCube.getPosition(),3)));
        
        }
        else{ //cylinder
            if (checkBoxController.getMassBox().isSelected()){
                mass.setText(Double.toString(dragDropController.MainCylinder.getMass()));
                mass.setLayoutX(mainObject.getLayoutX() + mainObject.getFitWidth()/2);
                mass.setLayoutY(mainObject.getLayoutY()  - mainObject.getFitHeight()/3);
                mass.setVisible(true);
            }
            else {
                mass.setVisible(false);
            }
            statsController.getVelocityText().setText(Double.toString(round(dragDropController.MainCylinder.getVelocity(),3)));
            // statsController.getAngularVelText().setText(Double.toString(round(dragDropController.MainCylinder.getAngularVel(),3)));

            statsController.getAccelerationText().setText(Double.toString(round(dragDropController.MainCylinder.getAcceleration(),3)));
            // statsController.getAngularAccText().setText(Double.toString(round(dragDropController.MainCylinder.getGamma(),3)));


            statsController.getPositionText().setText(Double.toString(round(dragDropController.MainCylinder.getPosition(),3)));
            // statsController.getAngularPosText().setText(Double.toString(round(dragDropController.MainCylinder.getAngularPos(),3)));

        }
    }

    public void disableForceController(boolean b) {
        forceSlider.setDisable(b);
        increaseForce.setDisable(b);
        decreaseForce.setDisable(b);
        surfaceController.disableFrictionSlider(b);
    }


    public void loadCheckBox() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/CheckBox.fxml"));

            statisticPane = (Pane) loader.load();
            statisticPane.setScaleX(0.9);
            statisticPane.setScaleY(0.9);

            AnchorPane.setTopAnchor(statisticPane, 25.0);
            AnchorPane.setRightAnchor(statisticPane, 35.0);

            mainPane.getChildren().add(statisticPane);
            this.checkBoxController = loader.getController();
            

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
        checkBoxController.reset();
        cube.setVisible(true);
        cylinder.setVisible(true);
        if (timeline!=null) {timeline.pause();}
        surfaceTransition.pause();
        cloudTransition1.pause();
        cloudTransition2.pause();
        cloudTransition3.pause();
        cloudTransition4.pause();
        mass.setVisible(false);
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
        cloudTransition1.play();
        cloudTransition2.play();
        cloudTransition3.play();
        cloudTransition4.play();
        if (dragDropController.is_cylinder) {
            rotate.play();
        }
        if (timeline!=null)
        {timeline.play();}
    }

    public void pause() {
        surfaceTransition.pause();
        cloudTransition1.pause();
        cloudTransition2.pause();
        cloudTransition3.pause();
        cloudTransition4.pause();
        if (dragDropController.is_cylinder) {
            rotate.pause();
        }
        if (timeline!=null)
        {timeline.pause();}
    }

    
}
