package model.object;

import model.force.Force;

public class Cylinder extends MainObject {
    public static final double MAX_RADIUS = 1.0;
    public static final double MIN_RADIUS = 0.2;
    private double radius = MIN_RADIUS;
    private double angularPos = 0;
    private double angularVel = 0;
    private double gamma = 0;

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
    
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
        if (radius > MAX_RADIUS) {
            this.radius = MAX_RADIUS;
        }
        else if (radius < MIN_RADIUS) {
            this.radius = MIN_RADIUS;
        }
    }

    public double getAngularPos() {
        return angularPos;
    }

    public void setAngularPos(double angularPos) {
        this.angularPos = angularPos;
    }

    public double getAngularVel() {
        return angularVel;
    }

    public void setAngularVel(double angularVel) {
        this.angularVel = angularVel;
    }
    
    public double getGamma(){
        return this.gamma;
    }


    public void setGamma(double friction) {
        this.gamma = friction * 2 / (getMass() * getRadius() * getRadius());
    }

    public void updateAngularVel(double friction, double delta_t) {
        setGamma(friction);
        setAngularVel(getAngularVel() + getGamma() * delta_t);
    }
    
    public void updateAngularPos(double delta_t) {
        setAngularPos(getAngularPos() + getAngularVel() * delta_t);
    }
    
    @Override
    public void applyForceInTime(Force netforce, Force fForce, double t) {
            updateAcceleration(netforce.getMagnitude());
            updatePosition(t);
            updateVelocity(t);
            updateAngularPos(t);
            updateAngularVel(-fForce.getMagnitude(), t);
        }
        


}
