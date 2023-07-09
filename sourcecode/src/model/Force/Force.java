package model.Force;

public class Force extends Vector{
	public Force(double magnitude) {
        super(magnitude);
    }
	
    public Force sumOfForce(Force otherForce) {
        Force netForce =new Force(this.getMagnitude() + otherForce.getMagnitude());
        netForce.updateDirection();
        return netForce;
    }
    
}

