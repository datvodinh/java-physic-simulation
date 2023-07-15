package model.force;


import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class Vector {
    private BooleanProperty isRightDirection = new SimpleBooleanProperty(true);
    private DoubleProperty magnitude = new SimpleDoubleProperty(0.0);

    public Vector(double magnitude) {
        setMagnitude(magnitude);
    }

    // Get whether the vector is in the right direction
    public boolean isRightDirection() {
        return isRightDirection.get();
    }

    // Set whether the vector is in the right direction
    public void setRightDirection(boolean isRightDirection) {
        this.isRightDirection.set(isRightDirection);
        updateMagnitude();
    }

    // Get the magnitude property of the vector
    public DoubleProperty magnitudeProperty() {
        return magnitude;
    }

    // Get the magnitude of the vector
    public double getMagnitude() {
        return magnitude.get();
    }

    // Set the magnitude of the vector
    public void setMagnitude(double magnitude) {
        this.magnitude.set(magnitude);
        updateDirection();
    }

    // Get the absolute magnitude of the vector
    public double getAbsoluteMagnitude() {
        return Math.abs(magnitude.get());
    }

    // Update the direction of the vector based on the magnitude
    protected void updateDirection() {
        if (magnitude.get() >= 0) {
            isRightDirection.set(true);
        } else {
            isRightDirection.set(false);
        }
    }

    // Update the magnitude of the vector based on the direction
    protected void updateMagnitude() {
        double absMagnitude = Math.abs(magnitude.get());
        if (!isRightDirection.get()) {
            absMagnitude *= -1;
        }
        magnitude.set(absMagnitude);
    }
}
