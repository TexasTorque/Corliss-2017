package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.intake.RunFloorIntake;
import org.texastorque.auto.intake.RunFloorIntake.IntakeState;
import org.texastorque.auto.shooter.RunShooter.Setpoints;
import org.texastorque.auto.twinsters.RunTwinster;
import org.texastorque.auto.util.Pause;

public class ShootBoilerLayup extends AutonomousSequence {

	public ShootBoilerLayup() {
		init();
	}

	@Override
	public void init() {
		commandList.add(new RunShooter(Setpoints.LAYUP));
		commandList.add(new Pause(1));
		commandList.add(new RunTwinster(1));
		commandList.add(new RunFloorIntake(IntakeState.INTAKE));
		commandList.add(new Pause(4));
	}
	
}
