
package model.Surface;
/**
 * This class is used to represent surface which contains attributes and methods
 * for adjusting static friction coefficient and kinetic friction coefficient
 *
 */
public class Surface {

	private double staticCoef = 0; //static coefficient
	
	private double kineticCoef =0; // kinetic coefficient
	
	public static final double MAX_STA_COEF = 1.0;
	/**
	 * Holds the step of static/kinetic coefficient of class Surface
	 */
	public static final double STEP_COEF = 0.001;

	/**
	 * Default class constructor
	 */
	public Surface() {
	}

	/**
	 * Class constructor specifying static friction coefficient and set kinetic
	 * friction coefficient equals static friction coefficient / 2
	 * 
	 * @param staticCoef The static friction coefficient of this Surface
	 * @throws Exception Throw exception if invalid static friction coefficient
	 * @see #setStaticCoef(double)
	 */
	public Surface(double staticCoef) throws Exception {
		setStaticCoef(staticCoef);
		setKineticCoef(staticCoef / 2);
	}

	/**
	 * Class constructor specifying static friction coefficient and kinetic friction
	 * coefficient
	 * 
	 * @param staticCoef The static friction coefficient of this Surface
	 * @param kineticCoef  The kinetic friction coefficient of this Surface
	 * @throws Exception Throw exception if invalid static / kinetic friction
	 */
	public Surface(double staticCoef, double kineticCoef) throws Exception {
		setStaticCoef(staticCoef);
		setKineticCoef(kineticCoef);
	}


	/**
	 * Gets the static friction coefficient of this Surface
	 * @return The static friction coefficient of this Surface
	 */
	public double getStaticCoef() {
		return staticCoef;
	}


	/**
	 * Gets the kinetic friction coefficient of this Surface
	 * @return the kinetic friction coefficient of this Surface
	 */
	public double getKineticCoef() {
		return kineticCoef;
	}

	/**
	 * Changes the static friction coefficient of this Surface
	 * @param staticCoef This Surface's new static friction coefficient
	 * @throws Exception Throw exception if invalid static friction coefficient
	 */
	public void setStaticCoef(double staticCoef) throws Exception {
		if (staticCoef < 0) {
			this.staticCoef = 0;
		} else if (staticCoef > MAX_STA_COEF) {
			this.staticCoef = MAX_STA_COEF;
		} else if (staticCoef == 0) {
			// Sets both staticCoef and kineticCoef to 0 if staticCoef = 0
			kineticCoef = 0 ;
			this.staticCoef = 0;
		} else if (staticCoef <= getKineticCoef()) {
			this.staticCoef = getKineticCoef() + STEP_COEF;
		} else {
			this.staticCoef = staticCoef;
		}
	}

	/**
	 * Changes the kinetic friction coefficient of this Surface
	 * @param staticCoef This Surface's new kinetic friction coefficient
	 * @throws Exception Throw exception if invalid kinetic friction coefficient
	 */
	public void setKineticCoef(double kineticCoef) throws Exception {
		if (kineticCoef < 0) {
			this.kineticCoef = 0;
		} else if (kineticCoef >= MAX_STA_COEF) {
			this.kineticCoef = Math.max(0, getStaticCoef() - STEP_COEF);
		} else if (getStaticCoef() <= kineticCoef) {
			// Handles case when staticCoef has already = 0
			this.kineticCoef = Math.max(0, getStaticCoef() - STEP_COEF);
		} else {
			this.kineticCoef = kineticCoef;
		}
	}

}
