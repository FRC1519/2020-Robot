package org.mayheminc.robot2019.subsystems;

import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

/**
 * 
 * @author user This is a class to hold the Heading error of the drive.
 */
public class PIDHeadingError implements PIDSource {

	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {

	}

	@Override
	public PIDSourceType getPIDSourceType() {
		return PIDSourceType.kDisplacement;
	}

	@Override
	public double pidGet() {
		return m_Error;
	}

	public double m_Error = 0.0;
}
