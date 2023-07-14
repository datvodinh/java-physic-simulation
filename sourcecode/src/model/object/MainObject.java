package model.object;

import java.math.BigDecimal;
import java.math.RoundingMode;

import model.force.Force;

public abstract class MainObject {
    
    public static final double MASS_DEFAULT = 50.0; // Default mass in kilograms
    public static final double MAX_VEL = 50; // Maximum velocity in m/s
    public static final double MIN_VEL = -50; // Minimum velocity in m/s

    private double mass = MASS_DEFAULT; // Mass of the object
    private double pos = 0; // Position of the object
    private double accel = 0; // Acceleration of the object
    private double vel = 0; // Velocity of the object

    public MainObject() throws Exception {
    }

    public MainObject(double mass) throws Exception {
        setMass(mass);
    }
    
    // Get the mass of the object
    public double getMass() {
        return mass;
    }

    // Set the mass of the object
    public void setMass(double mass) {
        this.mass = mass;
    }

    // Get the acceleration of the object
    public double getAcceleration() {
        return accel;
    }

    // Set the acceleration of the object
    public void setAcceleration(double accel) {
        this.accel = accel;
    }

    // Update the acceleration of the object based on the net force applied
    public void updateAcceleration(double netForce) {
        setAcceleration(netForce / getMass());
    }

    // Get the velocity of the object
    public double getVelocity() {
        return vel;
    }

    // Set the velocity of the object
    public void setVelocity(double vel) {
        this.vel = vel;
    }

    // Update the velocity of the object based on the time interval and stopping condition
    public void updateVelocity(double delta_t, boolean stop) {
        double oldVel = getVelocity();
        double newVel = oldVel + getAcceleration() * delta_t;

        if ((oldVel * newVel < 0) && stop) {
            setVelocity(0);
        }
        else {
            setVelocity(newVel);
        }

        // The code below can be used to limit the velocity within a specific range
        /*
        if (newVel > MAX_VEL) {
            setVelocity(MAX_VEL);
        }
        else if (newVel < MIN_VEL) {
            setVelocity(MIN_VEL);
        }
        */
    }

    // Get the position of the object
    public double getPosition() {
        return pos;
    }

    // Set the position of the object
    public void setPosition(double pos) {
        this.pos = pos;
    }

    // Update the position of the object based on the time interval
    public void updatePosition(double delta_t) {
        setPosition(getPosition() + getVelocity() * delta_t);
    }

    // Apply net force and fForce to the object over a given time interval
    public void applyForceInTime(Force netforce, Force fForce, double t) {
        updateAcceleration(netforce.getMagnitude());
        updatePosition(t);
        
        if (netforce.getMagnitude() == fForce.getMagnitude()) {
            updateVelocity(t, true);
        }
        else {
            updateVelocity(t, false);
        }
    }

    // Helper method to round a double value to a specified number of decimal places
    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
