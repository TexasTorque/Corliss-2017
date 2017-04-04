package org.texastorque.auto;

import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.FlyWheel;
import org.texastorque.subsystem.Gear;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Twinsters;

public abstract class AutonomousCommand {

	protected FlyWheel flyWheel;
	protected DriveBase driveBase;
	protected Gear gearSystem;
	protected Intake intake;
	protected Twinsters twinsters;
	
	public void init() {
		
	}
	
	public abstract void run();
	
	public abstract void reset();
}
