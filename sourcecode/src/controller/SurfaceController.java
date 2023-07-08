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
import model.surface.Surface;

public class SurfaceController implements Initializable {
    @FXML
    private Slider KSlider,SSlider;
    @FXML
    private TextField KCoeff,SCoeff;
    Surface surface = new Surface();
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        KSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Kinetic = KSlider.getValue();
                try {
                    surface.setKineticCoef(Kinetic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                KCoeff.setText((Double.toString(round(surface.getKineticCoef(),4))));
            }

        });
        SSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Static = SSlider.getValue();
                try {
                    surface.setStaticCoef(Static);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SCoeff.setText(Double.toString(round(surface.getStaticCoef(),4)));
            }

        });

    }
    
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    
}
