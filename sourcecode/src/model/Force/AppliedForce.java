package model.force;

public class AppliedForce extends Force {
	public static final double ABSOLUTE_MAX_APPLIEDFORCE=6996;
	public AppliedForce(double magnitude) {
		super(magnitude);
	}
	@Override
	public void setMagnitude(double magnitude) {
		double cappedValue = Math.max(Math.min(magnitude,ABSOLUTE_MAX_APPLIEDFORCE ), -ABSOLUTE_MAX_APPLIEDFORCE);
		super.setMagnitude(cappedValue);
	}
}
