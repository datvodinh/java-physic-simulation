package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Translate;
import model.object.MainObject;
import javafx.util.Duration;


public class StatisticController {
    private MainObject mainObject;

    private Slider mySlider;

    private Slider kineticSlider;

    @FXML
    private CheckBox massCheckBox; 

    @FXML
    private Label massLabel = new Label();


    public void handleMassCheckBox(){
        if (!massCheckBox.isSelected()){
            massLabel.setText(null);
            massLabel.setStyle("");
            
        }else {
            massLabel.setText(String.valueOf(mainObject.getMass())+"Kg");
            massLabel.setStyle("-fx-background-color:white");
        }
    }

    @FXML 
    private CheckBox velCheckBox;

    @FXML
    private Label velLabel;
    

    public void handleVelCheckBox(){
        if (!velCheckBox.isSelected()){
            Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                velLabel.setText("v:" + Double.toString(round(mainObject.getVelocity(), 0)) + "m/s");

            }));
            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
        }else{
            velLabel.setText(null);
        }
    }

    @FXML
    private CheckBox accCheckBox;

    @FXML
    private Label accLabel;

    public void handleAccCheckBox(){
        if (accCheckBox.isSelected()){
            Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                accLabel.setText("a:" + Double.toString(round(mainObject.getAcceleration(), 1)) + "m/s²");

            }));
            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
        }else{
            accLabel.setText(null);
        }
    }

    @FXML
    private CheckBox forceCheckBox;

    @FXML
    private Label applyArrow;

    @FXML
    private Label frictionArrow;

    public void handleForceCheckBox(){
        ImageView aArrowBackground = new ImageView(new Image(getClass().getResourceAsStream("applyForceArrow.png")));
        
        aArrowBackground.setFitHeight(50);

        applyArrow.setText(null);

        ImageView fArrowBackground = new ImageView(new Image(getClass().getResourceAsStream("frictionForceArrow.png")));
        
        fArrowBackground.setFitHeight(50);

        frictionArrow.setText(null);

        Translate translate = new Translate();
        
        
            Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                
                mySlider.valueProperty().addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                        if (forceCheckBox.isSelected()){
                            if (newValue.doubleValue() * oldValue.doubleValue() > 0) {
                                if (newValue.doubleValue() <= 0){
                                    applyArrow.translateXProperty().bind((translate.xProperty().multiply(2))
                                    .subtract(applyArrow.widthProperty()));
                                }else{
                                    applyArrow.translateXProperty().bind((translate.xProperty()));
                                }
                            }
                            if (round(mySlider.getValue(), 1) > 0){

                                applyArrow.setScaleX(1);

                                applyArrow.setGraphic(aArrowBackground);
                                aArrowBackground.setFitWidth(round(mySlider.getValue(),1));

                            }else if(round(mySlider.getValue(), 1) < 0){

                                applyArrow.setScaleX(-1);

                                applyArrow.setGraphic(aArrowBackground);
                                aArrowBackground.setFitWidth(round(-mySlider.getValue(),1));

                            }else{
                                applyArrow.setGraphic(null);
                                
                            }
                        }else{
                                applyArrow.setGraphic(null);}
                    }

                });

                if (forceCheckBox.isSelected()){

                    kineticSlider.valueProperty().addListener(new ChangeListener<Number>() {

                        @Override
                        public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                            if (round(kineticSlider.getValue(), 1) > 0){

                                frictionArrow.setScaleX(-1);

                                frictionArrow.setGraphic(fArrowBackground);
                                fArrowBackground.setFitWidth(kineticSlider.getValue());
                            }   
                        }

                    });
                }else{
                    frictionArrow.setGraphic(null);
                }
                
            }));

            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
        
    }

    @FXML
    private CheckBox sumForcesCheckBox;

    @FXML
    private Label sumForceArrow;

    public void handleSumForcesCheckBox(){
        ImageView sumForceLabelBackground = new ImageView(new Image(getClass().getResourceAsStream("sumForceArrow.png")));

        sumForceLabelBackground.setFitHeight(50);

        sumForceArrow.setText(null);

        Translate translate = new Translate();
        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            mySlider.valueProperty().addListener(new ChangeListener<Number>() {

                @Override
                public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                    if (sumForcesCheckBox.isSelected()){
                        if (newValue.doubleValue() * oldValue.doubleValue() > 0) {
                            if (newValue.doubleValue() <= 0){
                                sumForceArrow.translateXProperty().bind((translate.xProperty().multiply(2))
                                .subtract(sumForceArrow.widthProperty()));
                            }else{
                                sumForceArrow.translateXProperty().bind((translate.xProperty()));
                            }
                        }
                        if (round(mySlider.getValue()-kineticSlider.getValue(),1) > 0){

                            sumForceArrow.setScaleX(1);

                            sumForceArrow.setGraphic(sumForceLabelBackground);
                            sumForceLabelBackground.setFitWidth(round(mySlider.getValue()-kineticSlider.getValue(),1)); 
                            

                        }else if(round(mySlider.getValue()-kineticSlider.getValue(),1) < 0){

                            sumForceArrow.setScaleX(-1);

                            sumForceArrow.setGraphic(sumForceLabelBackground);
                            sumForceLabelBackground.setFitWidth(round(-mySlider.getValue()+kineticSlider.getValue(),1));

                        }else{
                            sumForceArrow.setGraphic(null);
                            
                        }
                    }else{
                        sumForceArrow.setGraphic(null);
                    }
                }

            });

            
                
            }));

            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
        
        
        
    }

    @FXML
    private CheckBox posCheckBox;

    @FXML
    private Label posLabel;

    public void handelPosCheckBox(){
        if (posCheckBox.isSelected()){
            
            Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                posLabel.setText(mainObject.getPosition()+"m");
                
            }));
        

            timeLine.setCycleCount(Animation.INDEFINITE);
            timeLine.play();
        }
    }


    @FXML
    private CheckBox valueCheckBox;
    
    @FXML 
    private Label sumForceArrowLabel;

    @FXML
    private Label applyArrowLabel;

    @FXML
    private Label frictionArrowLabel;

    public void handlValueCheckBox(){  
        Translate translate = new Translate();
        
        sumForceArrowLabel.setText(null);
        sumForceArrowLabel.translateYProperty().bind((translate.yProperty()).add(sumForceArrow.heightProperty().divide(2)));

        applyArrowLabel.setText(null);

        frictionArrowLabel.setText(null);

        Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            sumForceArrowLabel.setText(String.valueOf(sumForceArrow.getWidth()));
            applyArrowLabel.setText(String.valueOf(applyArrow.getWidth()));
            frictionArrowLabel.setText(String.valueOf(frictionArrow.getWidth()));
            
        }));

        timeLine.setCycleCount(Animation.INDEFINITE);
        timeLine.play();
        timeLine.setCycleCount(0);
        }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
