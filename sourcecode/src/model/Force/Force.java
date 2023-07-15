package model.force;

public class Force extends Vector {
    public Force(double magnitude) {
        super(magnitude);
    }
	
    // Calculate the sum of two forces
    public Force sumOfForce(Force otherForce) {
        Force netForce = new Force(this.getMagnitude() + otherForce.getMagnitude());
        netForce.updateDirection();
        return netForce;
    }
}
