package org.texastorque.auto.gear;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.drive.DistanceDrive;

public class GearBackup extends AutonomousSequence {

	@Override
	public void init() {
		commandList.addLast(new DistanceDrive(-12,1));
	}
	
}
