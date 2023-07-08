package controller;

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
                KCoeff.setText(Double.toString(surface.getKineticCoef()));
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
                SCoeff.setText(Double.toString(surface.getStaticCoef()));
            }
            
        });
    }

    
}
