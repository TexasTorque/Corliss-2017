package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.intake.RunFloorIntake;
import org.texastorque.auto.intake.RunFloorIntake.IntakeState;
import org.texastorque.auto.shooter.RunShooter.Setpoints;
import org.texastorque.auto.twinsters.RunTwinster;
import org.texastorque.auto.util.Pause;

public class ShootHopper extends AutonomousSequence {

	public ShootHopper() {
		init();
	}
	
	@Override
	public void init() {
		commandList.add(new RunShooter(Setpoints.LONGSHOT));
		commandList.add(new Pause(.5));
		commandList.add(new Pause(.5));
		commandList.add(new RunTwinster(1));
		commandList.add(new RunGate(1));
	}
}
