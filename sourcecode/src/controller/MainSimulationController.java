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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import model.Force.AppliedForce;
import model.Force.ForceSimulation;
import model.Force.FrictionForce;
import model.object.Cube;
import model.object.Cylinder;
import model.Surface.Surface;
import javafx.scene.control.Label;

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
    @FXML
    private ImageView frictionForceArrow;
    @FXML
    private ImageView appliedForceArrow;
    @FXML
    private ImageView netForceArrow;
    @FXML
    private ImageView negativeFrictionForceArrow;
    @FXML
    private ImageView negativeAppliedForceArrow;
    @FXML
    private ImageView negativeNetForceArrow;
    @FXML
    private HBox negativeNetForceBox;
    @FXML
    private HBox negativeAppliedForceBox;
    @FXML
    private HBox negativeFrictionForceBox;
    @FXML
    private HBox frictionForceBox;
    @FXML
    private HBox netForceBox;
    @FXML
    private HBox appliedForceBox;
    @FXML
    private Label negativeNetForceLabel;
    @FXML
    private Label negativeAppliedForceLabel;
    @FXML
    private Label negativeFrictionForceLabel;
    @FXML
    private Label frictionForceLabel;
    @FXML
    private Label netForceLabel;
    @FXML
    private Label appliedForceLabel;
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
    private double scaleFactor=0.5;
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        loadStatistic();
        loadSurfacePanel();
        disableForceController(true);
        frictionForceArrow.setVisible(false);
        appliedForceArrow.setVisible(false);
        negativeFrictionForceArrow.setVisible(false);
        negativeAppliedForceArrow.setVisible(false);

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
            frictionForceArrow.setVisible(false);
            appliedForceArrow.setVisible(false);
            negativeFrictionForceArrow.setVisible(false);
            negativeAppliedForceArrow.setVisible(false);
            frame = new KeyFrame(Duration.seconds(0.2), event -> {
                animation.setMovement(surfaceTransition, surface, mainCube.getVelocity(), mainPane.getWidth());
                animation.setMovement(backgroundTransition, background, mainCube.getVelocity() / 20,
                        mainPane.getWidth());
                
                try {
                    //forceSimulation.getSur().setStaticCoef(surfaceController.getSSlider().getValue());
                    //forceSimulation.getSur().setKineticCoef(surfaceController.getKSlider().getValue());
                    forceSimulation.setSur(new Surface(surfaceController.getSSlider().getValue(),surfaceController.getKSlider().getValue()));
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
                    double frictionForceValue = forceSimulation.getFrictionForce().getMagnitude();
                    double appliedForceValue = forceSimulation.getAppliedForce().getMagnitude();
                    double netForceValue = forceSimulation.getNetForce().getMagnitude();
                   
                    double frictionForceArrowWidth = Math.abs(frictionForceValue) * scaleFactor;
	                double appliedForceArrowWidth = Math.abs(appliedForceValue) * scaleFactor;
	                if (statisticController.getForceBox().isSelected() && mainCube.getVelocity()!=0) {	                	
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
	                if (statisticController.getSumForceBox().isSelected() && mainCube.getVelocity()!=0) {	                	
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
		         if (statisticController.getValueBox().isSelected() && mainCube.getVelocity()!=0) {		         	
		        	 {
		        		 if (statisticController.getSumForceBox().isSelected()) {
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
                });                                  
        } else {
            mainCylinder = dragDropController.MainCylinder;

            forceSimulation = new ForceSimulation(mainCylinder, mainSurface, appliedForce);
            frictionForceArrow.setVisible(false);
            appliedForceArrow.setVisible(false);
            negativeFrictionForceArrow.setVisible(false);
            negativeAppliedForceArrow.setVisible(false);
            frame = new KeyFrame(Duration.seconds(0.2), event -> {
                animation.setMovement(surfaceTransition, surface, mainCylinder.getVelocity(), mainPane.getWidth());
                animation.setMovement(backgroundTransition, background, mainCylinder.getVelocity() / 20,mainPane.getWidth());
                animation.setRotate(rotate, mainObject, mainCylinder.getVelocity());
                try {
                    //forceSimulation.getSur().setKineticCoef(surfaceController.getKSlider().getValue());
                    //forceSimulation.getSur().setStaticCoef(surfaceController.getSSlider().getValue());
                	forceSimulation.setSur(new Surface(surfaceController.getSSlider().getValue(),surfaceController.getKSlider().getValue()));
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
                    double frictionForceValue = forceSimulation.getFrictionForce().getMagnitude();
                    double appliedForceValue = forceSimulation.getAppliedForce().getMagnitude();
                    double netForceValue = forceSimulation.getNetForce().getMagnitude();
                   
                    double frictionForceArrowWidth = Math.abs(frictionForceValue) * scaleFactor;
	                double appliedForceArrowWidth = Math.abs(appliedForceValue) * scaleFactor;
	                if (statisticController.getForceBox().isSelected() && mainCylinder.getVelocity()!=0) {	                	
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
	                if (statisticController.getSumForceBox().isSelected() && mainCylinder.getVelocity()!=0) {	                	
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
		         if (statisticController.getValueBox().isSelected() && mainCylinder.getVelocity()!=0) {		         	
		        	 {
		        		 if (statisticController.getSumForceBox().isSelected()) {
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
        frictionForceArrow.setVisible(false);
        netForceArrow.setVisible(false);
        appliedForceArrow.setVisible(false);
        negativeFrictionForceArrow.setVisible(false);
        negativeNetForceArrow.setVisible(false);
        negativeAppliedForceArrow.setVisible(false);
        frictionForceLabel.setVisible(false);
        netForceLabel.setVisible(false);
        appliedForceLabel.setVisible(false);
        negativeFrictionForceLabel.setVisible(false);
        negativeNetForceLabel.setVisible(false);
        negativeAppliedForceLabel.setVisible(false);
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
