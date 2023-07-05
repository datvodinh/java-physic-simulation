package controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Slider;
import model.Surface.Surface;

public class SurfaceController {
    private Slider kineticSlider;
    private Slider staticSlider;
    private Surface surface;

    public SurfaceController(Slider kineticSlider, Slider staticSlider, Surface surface) {
        this.kineticSlider = kineticSlider;
        this.staticSlider = staticSlider;
        this.surface = surface;
        kineticSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Kinetic = kineticSlider.getValue();
                try{
                    surface.setKineticCoef(Kinetic);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
        staticSlider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
                double Static = staticSlider.getValue();
                try{
                    surface.setStaticCoef(Static);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}
