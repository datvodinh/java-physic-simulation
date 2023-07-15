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
    public Slider KSlider, SSlider;
    @FXML
    private TextField KCoeff, SCoeff;
    Surface surface = new Surface();

    /**
     * Getter for the KSlider field.
     *
     * @return The Slider object representing the kinetic coefficient slider.
     */
    public Slider getKSlider() {
        return KSlider;
    }

    /**
     * Getter for the SSlider field.
     *
     * @return The Slider object representing the static coefficient slider.
     */
    public Slider getSSlider() {
        return SSlider;
    }

    /**
     * Getter for the KCoeff field.
     *
     * @return The TextField object displaying the kinetic coefficient.
     */
    public TextField getKCoeff() {
        return KCoeff;
    }

    /**
     * Getter for the SCoeff field.
     *
     * @return The TextField object displaying the static coefficient.
     */
    public TextField getSCoeff() {
        return SCoeff;
    }

    /**
     * Rounds a double value to the specified number of decimal places.
     *
     * @param value  The double value to be rounded.
     * @param places The number of decimal places to round to.
     * @return The rounded double value.
     */
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize the SurfaceController
        KSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                // Update the kinetic coefficient when the slider value changes
                double Kinetic = round(KSlider.getValue(), 2);
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
                // Update the static coefficient when the slider value changes
                double Static = round(SSlider.getValue(), 2);
                try {
                    surface.setStaticCoef(Static);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SCoeff.setText(Double.toString(surface.getStaticCoef()));
            }
        });
    }

    /**
     * Resets the sliders and text fields to their default values.
     */
    public void reset() {
        KSlider.setValue(0.25);
        SSlider.setValue(0.5);
        KCoeff.setText("0.25");
        SCoeff.setText("0.5");
    }

    /**
     * Disables or enables the friction sliders based on the given parameter.
     *
     * @param b true to disable the sliders, false to enable them.
     */
    public void disableFrictionSlider(boolean b) {
        KSlider.setDisable(b);
        SSlider.setDisable(b);
    }
}
