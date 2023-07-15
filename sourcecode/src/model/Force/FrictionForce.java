package model.force;
import model.object.Cube;
import model.object.Cylinder;
import model.object.MainObject;
import model.surface.Surface;
public class FrictionForce extends Force{
	public FrictionForce(double magnitude) {
		super(magnitude);
	}

	private Surface surface;
	private MainObject mainObject;
	private AppliedForce appliedForce;
	public static final double GRAVITATIONAL_ACCELERATION = 10;
	public void setFrictionForce() {
        if (mainObject != null) {
            double normalForce = mainObject.getMass() * GRAVITATIONAL_ACCELERATION;
            double appliedForceValue = Math.abs(appliedForce.getMagnitude()); // Consider the magnitude of the applied force
            double direction=0;
            if (mainObject.getVelocity() != 0) {
				direction = (mainObject.getVelocity()>0) ? -1 : 1;
			} else {
				if (appliedForceValue == 0) {
					setMagnitude(0);
					return;
				} else {
					direction = (appliedForce.isRightDirection() == true) ? -1 : 1;
				}
			}
			if (mainObject instanceof Cube) {
				if (appliedForceValue>0 && appliedForceValue <= surface.getStaticCoef() * normalForce) {
					setMagnitude(-appliedForce.getMagnitude()); // Set the friction force magnitude as positive
				} else {
					setMagnitude(direction * normalForce * (surface.getKineticCoef())); // Set the friction force magnitude as positive
				}
			
		} else if (mainObject instanceof Cylinder) {
			if (appliedForceValue>0 && appliedForceValue <= 3 * surface.getStaticCoef() * normalForce ) {
				setMagnitude(-appliedForce.getMagnitude() / 3); // Set the friction force magnitude as positive
			} else {
				setMagnitude(direction * surface.getKineticCoef() * normalForce); // Set the friction force magnitude as positive
			}
	} 
			
			
	}
}

	public FrictionForce(double magnitude, Surface surface,MainObject mainObject, AppliedForce appliedForce) {
		super(magnitude);
		this.surface = surface;
		this.mainObject = mainObject;
		this.appliedForce = appliedForce;
		setFrictionForce();
	}
	public void setMainObject(MainObject object) {
        this.mainObject = object;
        setFrictionForce();
    }
	
}
