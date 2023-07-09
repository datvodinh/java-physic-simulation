package model.Force;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vector {
    private BooleanProperty isRightDirection=new SimpleBooleanProperty(true);
    private DoubleProperty magnitude=new SimpleDoubleProperty(0.0);
    public Vector(double magnitude) {
        this.setMagnitude(magnitude) ;
    }

    public boolean isRightDirection() {
        return isRightDirection.get();
    }

    public void setRightDirection(boolean isRightDirection) {
        this.isRightDirection.set(isRightDirection);
        updateMagnitude();
    }

    public DoubleProperty magnitudeProperty() {
        return magnitude;
    }

    public double getMagnitude() {
        return magnitude.get();
    }

    public void setMagnitude(double magnitude) {
        this.magnitude.set(magnitude);
        updateDirection();
    }

    public double getAbsoluteMagnitude() {
        return Math.abs(magnitude.get());
    }

    protected void updateDirection() {
        if (magnitude.get() >= 0) {
            isRightDirection.set(true);
        } else {
            isRightDirection.set(false);
        }
    }

    protected void updateMagnitude() {
        double absMagnitude = Math.abs(magnitude.get());
        if (!isRightDirection.get()) {
            absMagnitude *= -1;
        }
        magnitude.set(absMagnitude);
    }
}
