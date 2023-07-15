package model.object;

import model.force.Force;

public class Cylinder extends MainObject {
    public static final double MAX_RADIUS = 1.0; // Maximum radius of the cylinder
    public static final double MIN_RADIUS = 0.5; // Minimum radius of the cylinder
    private double radius = MIN_RADIUS; // Radius of the cylinder
    private double angularPos = 0; // Angular position of the cylinder
    private double angularVel = 0; // Angular velocity of the cylinder
    private double gamma = 0; // Friction coefficient

    public Cylinder() throws Exception {
        super();
    }

    public Cylinder(double mass) throws Exception {
        super(mass);
    }

    public Cylinder(double mass, double radius) throws Exception {
        super(mass);
        setRadius(radius);
    }
    
    // Get the radius of the cylinder
    public double getRadius() {
        return radius;
    }

    // Set the radius of the cylinder, ensuring it stays within the valid range
    public void setRadius(double radius) {
        this.radius = radius;
        if (radius > MAX_RADIUS) {
            this.radius = MAX_RADIUS;
        }
        else if (radius < MIN_RADIUS) {
            this.radius = MIN_RADIUS;
        }
    }

    // Get the angular position of the cylinder
    public double getAngularPos() {
        return angularPos;
    }

    // Set the angular position of the cylinder
    public void setAngularPos(double angularPos) {
        this.angularPos = angularPos;
    }

    // Get the angular velocity of the cylinder
    public double getAngularVel() {
        return angularVel;
    }

    // Set the angular velocity of the cylinder
    public void setAngularVel(double angularVel) {
        this.angularVel = angularVel;
    }
    
    // Get the friction coefficient of the cylinder
    public double getGamma(){
        return this.gamma;
    }

    // Set the friction coefficient of the cylinder based on the given friction value
    public void setGamma(double friction) {
        this.gamma = friction * 2 / (getMass() * getRadius() * getRadius());
    }

    // Update the angular velocity of the cylinder based on the given friction and time interval
    public void updateAngularVel(double friction, double delta_t, boolean stop, boolean stop2) {
        setGamma(friction);
        double oldAngVel = getAngularVel();
        double newAngvel = getAngularVel() + getGamma() * delta_t;
        if (stop2) {
            setGamma(0);
            setAngularVel(0);
        }
        else if ((oldAngVel * newAngvel < 0) && stop) {
            setAngularVel(0);
        }
        else {
            setAngularVel(newAngvel);
        }
    }
    
    // Update the angular position of the cylinder based on the given time interval
    public void updateAngularPos(double delta_t) {
        setAngularPos(getAngularPos() + getAngularVel() * delta_t);
    }
    
    // Apply the net force and fForce to the cylinder over the given time interval
    @Override
    public void applyForceInTime(Force netforce, Force fForce, double t) {
        updateAcceleration(netforce.getMagnitude());
        updatePosition(t);
        
        if (netforce.getMagnitude() == fForce.getMagnitude()) {
            updateVelocity(t, true);
        }
        else {
            updateVelocity(t, false);
        }
        
        updateAngularPos(t);
        
        if (netforce.getMagnitude() == fForce.getMagnitude()) {
            if (getAngularVel() == 0) {
                updateAngularVel(fForce.getMagnitude(), t, true, true);
            }
            else {
                updateAngularVel(fForce.getMagnitude(), t, true, false);
            }
            
        }
        else {
            updateAngularVel(-fForce.getMagnitude(), t,false,false);
        }
    }
}
