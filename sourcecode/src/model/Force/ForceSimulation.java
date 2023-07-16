package model.force;
import model.surface.Surface;
import model.object.MainObject;

public class ForceSimulation {
	private MainObject mainObject; // The main object in the force simulation
	private Surface surface; // The surface the object is on
	private AppliedForce appliedForce; // The applied force acting on the object
	private FrictionForce frictionForce; // The friction force between the object and the surface
	private Force netForce = new Force(0); // The net force acting on the object
	private Vector sysVel = new Vector(0); // The system velocity
	private Vector sysAcc = new Vector(0); // The system acceleration
	private double sysAngAcc; // The system angular acceleration
	private double sysAngVel; // The system angular velocity

	public ForceSimulation() {
		this.surface = new Surface();
		this.appliedForce = new AppliedForce(0);
		this.frictionForce = new FrictionForce(0);
	}

	public ForceSimulation(MainObject mainObject, Surface surface, AppliedForce appliedForce) {
		this.mainObject = mainObject;
		this.surface = surface;
		this.appliedForce = appliedForce;
		this.frictionForce = new FrictionForce(0, surface, mainObject, appliedForce);
		setNetForce();
	}

	// Set the net force by summing the applied force and friction force
	public void setNetForce() {
		Force newNetForce = appliedForce.sumOfForce(frictionForce);
		netForce.setMagnitude(newNetForce.getMagnitude());
	}

	// Set the system velocity
	public void setSysVel(Vector vector) {
		this.sysVel = vector;
	}

	// Set the system acceleration
	public void setSysAcc(Vector vector) {
		this.sysAcc = vector;
	}

	// Get the system velocity
	public Vector getSysVel() {
		return sysVel;
	}

	// Get the system acceleration
	public Vector getSysAcc() {
		return sysAcc;
	}

	// Get the system angular velocity
	public double getSysAngVel() {
		return sysAngVel;
	}

	// Get the system angular acceleration
	public double getSysAngAcc() {
		return sysAngAcc;
	}

	// Set the system angular velocity
	public void setSysAngVel(double sysAngVel) {
		this.sysAngVel = sysAngVel;
	}

	// Set the system angular acceleration
	public void setSysAngAcc(double sysAngAcc) {
		this.sysAngAcc = sysAngAcc;
	}

	// Get the main object in the force simulation
	public MainObject getMainObject() {
		return mainObject;
	}

	// Set the main object in the force simulation
	public void setObject(MainObject obj) {
		this.mainObject = obj;
		if (obj != null) {
			this.sysAcc = getSysAcc();
			this.sysVel = getSysVel();
		} else {
			this.sysAcc = new Vector(0);
			this.sysVel = new Vector(0);
		}
	}

	// Get the surface the object is on
	public Surface getSur() {
		return surface;
	}

	// Set the surface the object is on
	public void setSurface(Surface surface) {
		this.surface = surface;
		frictionForce = new FrictionForce(0, surface, mainObject, appliedForce);
		setNetForce();
	}

	// Get the applied force acting on the object
	public AppliedForce getAppliedForce() {
		return appliedForce;
	}

	// Set the magnitude of the applied force
	public void setAppliedForce(double appliedForce) {
		this.appliedForce.setMagnitude(appliedForce);
		frictionForce = new FrictionForce(0, surface, mainObject, this.appliedForce);
		setNetForce();
	}

	// Get the friction force between the object and the surface
	public FrictionForce getFrictionForce() {
		return frictionForce;
	}

	// Calculate and set the friction force
	public void setFrictionForce() {
		frictionForce.setFrictionForce();
	}

	// Get the net force acting on the object
	public Force getNetForce() {
		return netForce;
	}

	// Apply the net force and friction force on the object over a given time interval
	public void applyForceInTime(double t) {
		mainObject.applyForceInTime(netForce, frictionForce, t);
	}
}
