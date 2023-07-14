package model.surface;

/**
 * This class is used to represent a surface and contains attributes and methods for adjusting the static friction coefficient and kinetic friction coefficient.
 */
public class Surface {

	private double staticCoef = 0; // static coefficient
	private double kineticCoef = 0; // kinetic coefficient
	public static final double MAX_STA_COEF = 1.0; // maximum static coefficient
	public static final double STEP_COEF = 0.001; // step size for static/kinetic coefficient adjustment

	/**
	 * Default constructor for the Surface class.
	 */
	public Surface() {
	}

	/**
	 * Constructor for the Surface class that sets the static friction coefficient and sets the kinetic friction coefficient as half of the static friction coefficient.
	 *
	 * @param staticCoef The static friction coefficient of the surface.
	 * @throws Exception If an invalid static friction coefficient is provided.
	 */
	public Surface(double staticCoef) throws Exception {
		setStaticCoef(staticCoef);
		setKineticCoef(staticCoef / 2);
	}

	/**
	 * Constructor for the Surface class that sets the static friction coefficient and kinetic friction coefficient.
	 *
	 * @param staticCoef  The static friction coefficient of the surface.
	 * @param kineticCoef The kinetic friction coefficient of the surface.
	 * @throws Exception If an invalid static or kinetic friction coefficient is provided.
	 */
	public Surface(double staticCoef, double kineticCoef) throws Exception {
		setStaticCoef(staticCoef);
		setKineticCoef(kineticCoef);
	}

	/**
	 * Gets the static friction coefficient of the surface.
	 *
	 * @return The static friction coefficient of the surface.
	 */
	public double getStaticCoef() {
		return staticCoef;
	}

	/**
	 * Gets the kinetic friction coefficient of the surface.
	 *
	 * @return The kinetic friction coefficient of the surface.
	 */
	public double getKineticCoef() {
		return kineticCoef;
	}

	/**
	 * Sets the static friction coefficient of the surface.
	 *
	 * @param staticCoef The new static friction coefficient to be set.
	 * @throws Exception If an invalid static friction coefficient is provided.
	 */
	public void setStaticCoef(double staticCoef) throws Exception {
		if (staticCoef < 0) {
			this.staticCoef = 0;
		} else if (staticCoef > MAX_STA_COEF) {
			this.staticCoef = MAX_STA_COEF;
		} else if (staticCoef == 0) {
			// If staticCoef = 0, set both staticCoef and kineticCoef to 0
			kineticCoef = 0;
			this.staticCoef = 0;
		} else if (staticCoef <= getKineticCoef()) {
			this.staticCoef = getKineticCoef() + STEP_COEF;
		} else {
			this.staticCoef = staticCoef;
		}
	}

	/**
	 * Sets the kinetic friction coefficient of the surface.
	 *
	 * @param kineticCoef The new kinetic friction coefficient to be set.
	 * @throws Exception If an invalid kinetic friction coefficient is provided.
	 */
	public void setKineticCoef(double kineticCoef) throws Exception {
		if (kineticCoef < 0) {
			this.kineticCoef = 0;
		} else if (kineticCoef >= MAX_STA_COEF) {
			this.kineticCoef = Math.max(0, getStaticCoef() - STEP_COEF);
		} else if (getStaticCoef() <= kineticCoef) {
			// Handles the case when staticCoef has already been set to 0
			this.kineticCoef = Math.max(0, getStaticCoef() - STEP_COEF);
		} else {
			this.kineticCoef = kineticCoef;
		}
	}

}
