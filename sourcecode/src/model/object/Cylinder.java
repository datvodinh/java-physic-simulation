package model.object;
public class Cylinder extends MainObject {
    public static final double MAX_RADIUS = 1.0;
    public static final double MIN_RADIUS = 0.2;
    private double radius = MIN_RADIUS;
    private double angularPos = 0;
    private double angularVel = 0;
    private double gamma;

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
        this.gamma = friction / (1 / 2 * getMass() * Math.pow(getRadius(), 2));
    }

    public void updateAngularVel(double friction, double delta_t) {
        setGamma(friction);
        setAngularVel(getAngularVel() + getGamma() * delta_t);
    }
    
    public void updateAngularPos(double delta_t) {
        setAngularPos(getAngularPos()+ getAngularVel() * delta_t);
    }
    
    public void update(double a, double delta_t) {
        updateVelocity(a, delta_t);
        updatePosition(delta_t);
        updateAngularVel(a, delta_t);
        updateAngularPos(delta_t);

        if (round(getVelocity(),0) > MAX_VEL) {
            setVelocity(MAX_VEL);
        } else if (round(getVelocity(),0) < MIN_VEL) {
            setVelocity(MIN_VEL);
        }

    }
    


}
