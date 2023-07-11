package model.force;
import model.surface.Surface;
import model.object.MainObject;
public class ForceSimulation {
	private MainObject mainObject;
	private Surface surface;
	private AppliedForce appliedForce;
	private FrictionForce frictionForce;
	private Force netForce=new Force(0);
	private Vector sysVel=new Vector(0);
	private Vector sysAcc=new Vector(0);
	private double sysAngAcc;
	private double sysAngVel;
	public ForceSimulation() {
		this.surface = new Surface();
		this.appliedForce = new AppliedForce(0);
		this.frictionForce = new FrictionForce(0);
	}
	public ForceSimulation(MainObject mainObject, Surface surface, AppliedForce appliedForce) {
		this.mainObject = mainObject;
		this.surface = surface;
		this.appliedForce = appliedForce;
		this.frictionForce= new FrictionForce(0,surface,mainObject,appliedForce);
		setNetForce();
	}
	public void setNetForce() {
		Force newNetForce = appliedForce.sumOfForce(frictionForce);
		netForce.setMagnitude(newNetForce.getMagnitude());
	}
	public void setSysVel(Vector vector) {
		this.sysVel=vector;
	}
	public void setSysAcc(Vector vector) {
		this.sysAcc=vector;
	}
	public Vector getSysVel() {
		return sysVel;
	}
	public Vector getSysAcc() {
		return sysAcc;
	}
	public double getSysAngVel() {
		return sysAngVel;
	}
	public double getSysAngAcc() {
		return sysAngAcc;
	}
	public void setSysAngVel(double sysAngVel) {
		this.sysAngVel=sysAngVel;
	}
	public void setSysAngAcc(double sysAngAcc) {
		this.sysAngAcc=sysAngAcc;
	}
	public MainObject getMainObject() {
		return mainObject;
	}
	public void setObject(MainObject obj) {
		this.mainObject=obj;
		if (obj != null) {
			this.sysAcc=getSysAcc();
			this.sysVel=getSysVel();
		} else {
			this.sysAcc=new Vector(0);
			this.sysVel=new Vector(0);
		}

	}
	public Surface getSur() {
		return surface;
	}
	public void setSur(Surface surface) {
		this.surface=surface;
		frictionForce=new FrictionForce(0,surface,mainObject,appliedForce);
		setNetForce();
	}
	public Force getAppliedForce() {
		return appliedForce;
	}
	public void setAppliedForce(double appliedForce) {
		if (this.appliedForce.getMagnitude()*appliedForce<0) {
			frictionForce.setMagnitude(-frictionForce.getMagnitude());
		}
		this.appliedForce.setMagnitude(appliedForce);
		setNetForce();
	}

	public Force getFrictionForce() {
		return frictionForce;
	}
	
	public void setFrictionForce() {
		frictionForce.setFrictionForce();
	}

	public Force getNetForce() {
		return netForce;
	}
	
	public void applyForceInTime(double t) {
		getMainObject().applyForceInTime(getNetForce(), getFrictionForce(), t);
	}
}
