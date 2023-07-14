package model.force;
import model.object.Cube;
import model.object.Cylinder;
import model.object.MainObject;
import model.surface.Surface;

public class FrictionForce extends Force {
	private Surface surface; // The surface the object is on
	private MainObject mainObject; // The main object experiencing the friction force
	private AppliedForce appliedForce; // The applied force acting on the object
	public static final double GRAVITATIONAL_ACCELERATION = 10; // Gravitational acceleration constant

	public FrictionForce(double magnitude) {
		super(magnitude);
	}

	// Calculate and set the friction force
	public void setFrictionForce() {
		if (mainObject != null) {
			double normalForce = mainObject.getMass() * GRAVITATIONAL_ACCELERATION; // Calculate the normal force
			double appliedForceValue = Math.abs(appliedForce.getMagnitude()); // Consider the magnitude of the applied force
			double direction;

			// Determine the direction of the friction force based on the object's velocity and applied force
			if (mainObject.getVelocity() != 0) {
				direction = (mainObject.getVelocity() > 0) ? -1 : 1;
			} else {
				if (appliedForceValue == 0) {
					setMagnitude(0);
					return;
				} else {
					direction = (appliedForce.isRightDirection()) ? -1 : 1;
				}
			}

			// Calculate the friction force based on the object type and applied force value
			if (appliedForceValue != 0) {
				if (mainObject instanceof Cube) {
					// Calculate the friction force for a cube object
					if (appliedForceValue <= surface.getStaticCoef() * normalForce) {
						setMagnitude(direction * appliedForce.getMagnitude()); // Set the friction force magnitude as positive
					} else {
						setMagnitude(direction * normalForce * surface.getKineticCoef()); // Set the friction force magnitude as positive
					}
				} else if (mainObject instanceof Cylinder) {
					// Calculate the friction force for a cylinder object
					if (appliedForceValue <= 3 * surface.getStaticCoef() * normalForce && appliedForceValue > 0) {
						setMagnitude(direction * appliedForce.getMagnitude() / 3); // Set the friction force magnitude as positive
					} else {
						setMagnitude(direction * surface.getKineticCoef() * normalForce); // Set the friction force magnitude as positive
					}
				}
			} else {
				// Calculate the friction force when there is no applied force
				setMagnitude(direction * surface.getKineticCoef() * normalForce);
			}
		}
	}

	public FrictionForce(double magnitude, Surface surface, MainObject mainObject, AppliedForce appliedForce) {
		super(magnitude);
		this.surface = surface;
		this.mainObject = mainObject;
		this.appliedForce = appliedForce;
		setFrictionForce();
	}

	// Set the main object experiencing the friction force
	public void setMainObject(MainObject object) {
		this.mainObject = object;
		setFrictionForce();
	}
}
