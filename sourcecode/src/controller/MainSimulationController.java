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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
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
    private StatsController statisticController;
    @FXML
    private ImageView frictionForceArrow, appliedForceArrow, netForceArrow, negativeFrictionForceArrow, negativeAppliedForceArrow, negativeNetForceArrow;
    // @FXML
    // private HBox negativeNetForceBox, negativeAppliedForceBox, negativeFrictionForceBox, frictionForceBox, netForceBox, appliedForceBox;
    @FXML
    private Label negativeNetForceLabel, negativeAppliedForceLabel, negativeFrictionForceLabel, frictionForceLabel, netForceLabel, appliedForceLabel;


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

    private double scaleFactor=0.5;
    Label mass = new Label();
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadCheckBox();
        loadStats();
        loadSurfacePanel();
        disableForceController(true);
        frictionForceArrow.setVisible(false);
        appliedForceArrow.setVisible(false);
        negativeFrictionForceArrow.setVisible(false);
        negativeAppliedForceArrow.setVisible(false);
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
            AnchorPane.setLeftAnchor(statisticPane, 20.0);

            mainPane.getChildren().add(statisticPane);
            this.statisticController = loader.getController();
            

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
            frictionForceArrow.setVisible(false);
            appliedForceArrow.setVisible(false);
            negativeFrictionForceArrow.setVisible(false);
            negativeAppliedForceArrow.setVisible(false);
            frame = new KeyFrame(Duration.seconds(0.05), event -> {
                mainKeyFrame(mainCube, false);
            });
        }
        else { //Cylinder
            forceSimulation = new ForceSimulation(dragDropController.MainCylinder, mainSurface, appliedForce);

            frame = new KeyFrame(Duration.seconds(0.05), event -> {
                mainKeyFrame(dragDropController.MainCylinder, true);});
        }

        timeline = new Timeline(frame);
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        disableForceController(false);
        adjustForceArrowbyObjectSize();
    }

    private void mainKeyFrame(MainObject object, boolean is_rotate) {
        animation.setMovement(surfaceTransition, surface, object.getVelocity(), mainPane.getWidth());
        animation.setMovement(cloudTransition1, cloud1, object.getVelocity() / 50, mainPane.getWidth());
        animation.setMovement(cloudTransition2, cloud2, object.getVelocity() / 50, mainPane.getWidth());
        animation.setMovement(cloudTransition3, cloud3, object.getVelocity() / 50, mainPane.getWidth());
        animation.setMovement(cloudTransition4, cloud4, object.getVelocity() / 50, mainPane.getWidth());
        if (is_rotate) {
            animation.setRotate(rotate, mainObject, ((Cylinder) object).getAngularVel());
        }

        try {
            forceSimulation.getSur().setKineticCoef(surfaceController.getKSlider().getValue());
            forceSimulation.getSur().setStaticCoef(surfaceController.getSSlider().getValue());
            forceSimulation.setAppliedForce(forceSlider.getValue());
            forceSimulation.setFrictionForce();
            forceSimulation.setNetForce();
            forceSimulation.applyForceInTime(0.05);
            showStatistic();
            arrowForceTimeFrame(object);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public void adjustForceArrowbyObjectSize(){
        frictionForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);
        appliedForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);
        netForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);
        negativeFrictionForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);
        negativeAppliedForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);
        negativeNetForceArrow.setLayoutX(mainObject.getLayoutX() + mainObject.getFitHeight() / 2);

        frictionForceArrow.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 15);
        appliedForceArrow.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 15);
        netForceArrow.setLayoutY(mainObject.getLayoutY() - mainObject.getFitWidth() / 2);
        negativeFrictionForceArrow.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 15);
        negativeAppliedForceArrow.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 15);
        negativeNetForceArrow.setLayoutY(mainObject.getLayoutY() - mainObject.getFitWidth() / 2);

        negativeNetForceLabel.setLayoutY(mainObject.getLayoutY() - mainObject.getFitWidth() / 2 + 3);
        negativeAppliedForceLabel.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 12);
        negativeFrictionForceLabel.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 12);
        frictionForceLabel.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 12);
        netForceLabel.setLayoutY(mainObject.getLayoutY() - mainObject.getFitWidth() / 2 + 3);
        appliedForceLabel.setLayoutY(mainObject.getLayoutY() + mainObject.getFitWidth() / 2 - 12);



    }
    public void arrowForceTimeFrame(MainObject object) {
        try{
            double frictionForceValue = forceSimulation.getFrictionForce().getMagnitude();
            double appliedForceValue = forceSimulation.getAppliedForce().getMagnitude();
            double netForceValue = forceSimulation.getNetForce().getMagnitude();
            
            double frictionForceArrowWidth = Math.abs(frictionForceValue) * scaleFactor;
            double appliedForceArrowWidth = Math.abs(appliedForceValue) * scaleFactor;
            if (checkBoxController.getForceBox().isSelected() && object.getVelocity()!=0) {	                	
                    {
                if (appliedForceValue>0) {
                    appliedForceArrow.setVisible(true);
                    negativeAppliedForceArrow.setVisible(false);	                	 
                    appliedForceArrow.setFitWidth(appliedForceArrowWidth);
                    appliedForceArrow.setFitHeight(30);	                	 
                    if (frictionForceValue<0) {
                        negativeFrictionForceArrow.setVisible(true);
                        frictionForceArrow.setVisible(false);	                	 	 
                        negativeFrictionForceArrow.setFitWidth(frictionForceArrowWidth);
                        negativeFrictionForceArrow.setFitHeight(30);
                        negativeFrictionForceArrow.setLayoutX(640-frictionForceArrowWidth );	                		 
                    }
                    else if (frictionForceValue>0){
                        negativeFrictionForceArrow.setVisible(false);
                        frictionForceArrow.setVisible(true);
                        
                        frictionForceArrow.setFitWidth(frictionForceArrowWidth);
                        frictionForceArrow.setFitHeight(30);	                			                			                		
                    }
                }
                else if (appliedForceValue<0){	                	
                    // Customize the friction force arrow properties
                    negativeAppliedForceArrow.setVisible(true);
                    appliedForceArrow.setVisible(false);	                	
                    // Customize the applied force arrow properties
                    negativeAppliedForceArrow.setFitWidth(appliedForceArrowWidth);
                    negativeAppliedForceArrow.setFitHeight(30);
                    negativeAppliedForceArrow.setLayoutX(640-appliedForceArrowWidth);	                		                	 
                    if (frictionForceValue<0) {
                        negativeFrictionForceArrow.setVisible(true);
                        frictionForceArrow.setVisible(false);	                	 	 
                        negativeFrictionForceArrow.setFitWidth(frictionForceArrowWidth);
                        negativeFrictionForceArrow.setFitHeight(30);
                        negativeFrictionForceArrow.setLayoutX(640-frictionForceArrowWidth);	                		 	                		
                    }
                    else if (frictionForceValue>0){
                        negativeFrictionForceArrow.setVisible(false);
                        frictionForceArrow.setVisible(true);
            
                        frictionForceArrow.setFitWidth(frictionForceArrowWidth);
                        frictionForceArrow.setFitHeight(30);
                        frictionForceArrow.setLayoutX(640);	                		 	                		
                    }
                    }
                else {
                    appliedForceArrow.setVisible(false);
                    negativeAppliedForceArrow.setVisible(false);	                	 
                    if (frictionForceValue<0) {
                    negativeFrictionForceArrow.setVisible(true);
                    frictionForceArrow.setVisible(false);                		
                    negativeFrictionForceArrow.setFitWidth(frictionForceArrowWidth);
                    negativeFrictionForceArrow.setLayoutX(640-frictionForceArrowWidth);	                		                		
                    }
                    else {
                    negativeFrictionForceArrow.setVisible(false);
                    frictionForceArrow.setVisible(true);	                                		
                    frictionForceArrow.setFitHeight(30);	                		
                    }
                }
                    }
            } else {
            frictionForceArrow.setVisible(false);
            appliedForceArrow.setVisible(false);
            negativeFrictionForceArrow.setVisible(false);
            negativeAppliedForceArrow.setVisible(false);	                
            }
            if (checkBoxController.getSumForceBox().isSelected() && object.getVelocity()!=0) {	                	
                if (netForceValue>0) {
                    netForceArrow.setVisible(true);
                    negativeNetForceArrow.setVisible(false);	                	 
                    double netForceArrowWidth = Math.abs(netForceValue) * scaleFactor;
                    netForceArrow.setFitWidth(netForceArrowWidth);
                    netForceArrow.setFitHeight(30);	                	 
                    }
                else {
                    negativeNetForceArrow.setVisible(true);
                    netForceArrow.setVisible(false);	                	 
                    double netForceArrowWidth = Math.abs(netForceValue) * scaleFactor;
                    negativeNetForceArrow.setFitWidth(netForceArrowWidth);
                    negativeNetForceArrow.setFitHeight(30);
                    negativeNetForceArrow.setLayoutX(640-netForceArrowWidth);	                	
                }
            } else {
            netForceArrow.setVisible(false);
            negativeNetForceArrow.setVisible(false);	                
            }         
            if (checkBoxController.getValueBox().isSelected() && object.getVelocity()!=0) {		         	
                {
                    if (checkBoxController.getSumForceBox().isSelected()) {
                    if (netForceValue>0) {		 		        	 
                        netForceLabel.setVisible(true);
                        negativeNetForceLabel.setVisible(false);		 		        	 		 		     
                        netForceLabel.setText(String.format("%.0f", netForceValue)+" N");
                        }
                    else {		 		        	 
                        negativeNetForceLabel.setVisible(true);
                        netForceLabel.setVisible(false);
                        negativeNetForceLabel.setText(String.format("%.0f", netForceValue)+" N");
                    }
                } 	 
            if (appliedForceValue>0) {
                appliedForceLabel.setVisible(true);
                negativeAppliedForceLabel.setVisible(false);		        	 
                appliedForceLabel.setText(String.format("%.0f", appliedForceValue)+" N");
                if (frictionForceValue<0) {		        	 	 
                    negativeFrictionForceLabel.setVisible(true);
                    frictionForceLabel.setVisible(false);		        	 	 
                    negativeFrictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");
                }
                else if (frictionForceValue>0){		        	 	 
                    negativeFrictionForceLabel.setVisible(false);
                    frictionForceLabel.setVisible(true);		        	 	 		        		
                    frictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");		        		
                }
            }
            else if (appliedForceValue<0){		        			  		        	 
                negativeAppliedForceLabel.setVisible(true);
                appliedForceLabel.setVisible(false);		        	 		        	 		        	
                negativeAppliedForceLabel.setText(String.format("%.0f", appliedForceValue)+" N");
                if (frictionForceValue<0) {		        	 	
                    negativeFrictionForceLabel.setVisible(true);
                    frictionForceLabel.setVisible(false);		        	 	 
                    negativeFrictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");		        		
                }
                else if (frictionForceValue>0){		        	 	 
                    negativeFrictionForceLabel.setVisible(false);
                    frictionForceLabel.setVisible(true);		        	 	 
                    frictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");		        		
                }
                }
            else {		        	 
                appliedForceLabel.setVisible(false);
                negativeAppliedForceLabel.setVisible(false);
                if (frictionForceValue<0) {		        		
                negativeFrictionForceLabel.setVisible(true);
                frictionForceLabel.setVisible(false);		        		
                negativeFrictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");		        		
                }
                else {		        				        		
                negativeFrictionForceLabel.setVisible(false);
                frictionForceLabel.setVisible(true);
                frictionForceLabel.setText(String.format("%.0f", frictionForceValue)+" N");
                }
            }
                }
        } else {		       		        
        frictionForceLabel.setVisible(false);
        appliedForceLabel.setVisible(false);
        negativeFrictionForceLabel.setVisible(false);
        negativeAppliedForceLabel.setVisible(false);
        netForceLabel.setVisible(false);
        negativeNetForceLabel.setVisible(false);
        }		        	                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }                                  

    
    private void showStatistic() {
        mass.setFont(new Font(25));
        if (dragDropController.is_cube){ //cube
            if (checkBoxController.getMassBox().isSelected()){
                mass.setText(Double.toString(round(dragDropController.MainCube.getMass(),3))+" kg");
                mass.setVisible(true);
                mass.setLayoutX(mainObject.getLayoutX() + mainObject.getFitWidth() / 2 - mass.getWidth() / 2);
                // System.out.println(mass.getWidth());
                mass.setLayoutY(mainObject.getLayoutY() - mainObject.getFitHeight());
            }
            else{
                mass.setVisible(false);
            }
            if (checkBoxController.getVelocityBox().isSelected()){
                statisticController.getVelocityText().setText(Double.toString(round(dragDropController.MainCube.getVelocity(),3)));
                statisticController.getVelocityText().setVisible(true);
            }
            else{
                statisticController.getVelocityText().setVisible(false);
            }
            if (checkBoxController.getAccelerationBox().isSelected()){
                statisticController.getAccelerationText().setText(Double.toString(round(dragDropController.MainCube.getAcceleration(),3)));
                statisticController.getAccelerationText().setVisible(true);   
            }
            else{
                statisticController.getAccelerationText().setVisible(false);
            }
            if(checkBoxController.getPositionBox().isSelected()){
                statisticController.getPositionText().setText(Double.toString(round(dragDropController.MainCube.getPosition(),3)));
                statisticController.getPositionText().setVisible(true);
            }
            else{
                statisticController.getPositionText().setVisible(false);
            }

        
        }
        else{ //cylinder
            if (checkBoxController.getMassBox().isSelected()){
            mass.setText(Double.toString(round(dragDropController.MainCylinder.getMass(),3))+" kg");
            mass.setVisible(true);
            mass.setLayoutX(mainObject.getLayoutX() + mainObject.getFitWidth()/2 - mass.getWidth() / 2 );
            mass.setLayoutY(mainObject.getLayoutY() -  mainObject.getFitHeight());
            }
            else{
                mass.setVisible(false);
            }
            if (checkBoxController.getVelocityBox().isSelected()){
                statisticController.getVelocityText().setText(Double.toString(round(dragDropController.MainCylinder.getVelocity(),3)));
                statisticController.getAngularVelText().setText(Double.toString(round(dragDropController.MainCylinder.getAngularVel(),3)));
                statisticController.getVelocityText().setVisible(true);
                statisticController.getAngularVelText().setVisible(true);
            }
            else{
                statisticController.getVelocityText().setVisible(false);
                statisticController.getAngularVelText().setVisible(false);
            }
            if (checkBoxController.getAccelerationBox().isSelected()){
                statisticController.getAccelerationText().setText(Double.toString(round(dragDropController.MainCylinder.getAcceleration(),3)));
                statisticController.getAngularAccText().setText(Double.toString(round(dragDropController.MainCylinder.getGamma(),3)));
                statisticController.getAccelerationText().setVisible(true);
                statisticController.getAngularAccText().setVisible(true);
            }
            else{
                statisticController.getAccelerationText().setVisible(false);
                statisticController.getAngularAccText().setVisible(false);
            }
            if (checkBoxController.getPositionBox().isSelected()){
                statisticController.getPositionText().setText(Double.toString(round(dragDropController.MainCylinder.getPosition(),3)));
                statisticController.getAngularPosText().setText(Double.toString(round(dragDropController.MainCylinder.getAngularPos(),3)));
                statisticController.getPositionText().setVisible(true);
                statisticController.getAngularPosText().setVisible(true);
            }
            else{
                statisticController.getPositionText().setVisible(false);
                statisticController.getAngularPosText().setVisible(false);
            }
        }
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

            AnchorPane.setTopAnchor(statisticPane, 20.0);
            AnchorPane.setRightAnchor(statisticPane, 20.0);

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

    public void loadCheckBox() {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/CheckBox.fxml"));

            statisticPane = (Pane) loader.load();
            statisticPane.setScaleX(0.9);
            statisticPane.setScaleY(0.9);

            AnchorPane.setTopAnchor(statisticPane, 20.0);
            AnchorPane.setRightAnchor(statisticPane, 20.0);

            mainPane.getChildren().add(statisticPane);
            this.checkBoxController = loader.getController();
            

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
        if (timeline != null) {
            timeline.pause();
            timeline.getKeyFrames().clear();
        }
        surfaceTransition.pause();
        cloudTransition1.pause();
        cloudTransition2.pause();
        cloudTransition3.pause();
        cloudTransition4.pause();

                                
        rotate.pause();
        if (dragDropController.is_cylinder) {
            rotate.pause();
        }
        
        cube.setDisable(false);
        cylinder.setDisable(false);
        disableForceController(true);
        statisticController.reset();

        negativeNetForceLabel.setVisible(false);
        negativeAppliedForceLabel.setVisible(false);
        negativeFrictionForceLabel.setVisible(false);
        frictionForceLabel.setVisible(false);
        netForceLabel.setVisible(false);
        appliedForceLabel.setVisible(false);

        frictionForceArrow.setVisible(false);
        appliedForceArrow.setVisible(false);
        netForceArrow.setVisible(false);
        negativeFrictionForceArrow.setVisible(false);
        negativeAppliedForceArrow.setVisible(false);
        negativeNetForceArrow.setVisible(false);

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
