package controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import model.Surface.Surface;

public class SurfaceController implements Initializable {
    @FXML
    private Slider KSlider,SSlider;
    @FXML
    private TextField KCoeff,SCoeff;
    Surface surface = new Surface();
    
    public Slider getKSlider() {
        return KSlider;
    }

    public Slider getSSlider() {
        return SSlider;
    }

    public TextField getKCoeff() {
        return KCoeff;
    }

    public TextField getSCoeff() {
        return SCoeff;
    }

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        KSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Kinetic = round(KSlider.getValue(),2);
                try {
                    surface.setKineticCoef(Kinetic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                KCoeff.setText(Double.toString(surface.getKineticCoef()));
            }
            
        });
        SSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Static = round(SSlider.getValue(),2);
                try {
                    surface.setStaticCoef(Static);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SCoeff.setText(Double.toString(surface.getStaticCoef()));
            }
            
        });
    }

    
}