package model.Object;

import java.math.BigDecimal;
import java.math.RoundingMode;

import model.Force.Force;

public abstract class MainObject {
    
    public static final double MASS_DEFAULT = 50.0; // kg
    public static final double MAX_VEL = 50; // m/s
    public static final double MIN_VEL = -50; // m/s

    private double mass = MASS_DEFAULT;
    private double pos = 0;
    private double accel = 0;
    private double vel = 0;

    public MainObject() throws Exception {
    }

    public MainObject(double mass) throws Exception {
        setMass(mass);
    }
    
    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public double getAcceleration() {
        return accel;
    }


    public void setAcceleration(double accel) {
        this.accel = accel;
    }

    public double updateAcceleration(double netForce) {
        return netForce / getMass();
    }

    public double getVelocity() {
        return vel;
    }

    public void setVelocity(double vel) {
        this.vel = vel;
    }

    public void updateVelocity(double delta_t) {
        double oldVel = getVelocity();
        double newVel = oldVel + getAcceleration() * delta_t;

        if (oldVel * newVel < 0) {
            setVelocity(0);
        }
        else {
            setVelocity(newVel);
        }

        if (newVel > MAX_VEL) {
            setVelocity(MAX_VEL);
        }
        else if (newVel < MIN_VEL) {
            setVelocity(MIN_VEL);
        }
    }
    public double getPosition() {
        return pos;
    }

    public void setPosition(double pos) {
        this.pos = pos;
    }

    public void updatePosition( double delta_t) {
        setPosition(getPosition() + getVelocity() * delta_t);
    }

    public void applyForceInTime(Force netforce, Force fForce, double t) {
        updateAcceleration(netforce.getMagnitude());
        updatePosition(t);
		updateVelocity(t);
		
	}

    

    public static double round(double value, int places) {
        if (places < 0)
            throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
