package org.texastorque.auto.shooter;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.drive.RunDrive;
import org.texastorque.auto.drive.RunTurn;
import org.texastorque.auto.intake.RunFloorIntake;
import org.texastorque.auto.intake.RunFloorIntake.IntakeState;
import org.texastorque.auto.twinsters.RunTwinster;
import org.texastorque.auto.util.Pause;

import edu.wpi.first.wpilibj.DriverStation;

public class DriveToHopper extends AutonomousSequence {

	public DriveToHopper() {
		init();
	}
	
	@Override
	public void init() {
		switch(DriverStation.getInstance().getAlliance()) {
		case Blue:
			commandList.add(new RunDrive(-80, .125, 2));
			commandList.add(new RunTurn(90, .125, 1));
			commandList.add(new RunDrive(28, 0, 1.5));
			commandList.add(new RunFloorIntake(IntakeState.INTAKEHOPPER));
			commandList.add(new RunTwinster(1));
			commandList.add(new Pause(2));
			commandList.add(new RunTwinster(1));
			commandList.add(new RunDrive(-10, 0, 1.5));
			commandList.add(new RunTurn(-80,0,1));
			commandList.addAll(new VisionTest().getCommands());
			commandList.addAll(new ShootHopper().getCommands());
			break;
		case Red:
			commandList.add(new RunDrive(-80, .125, 2));
			commandList.add(new RunTurn(-90, .125, 1));
			commandList.add(new RunDrive(28, 0, 1.5));
			commandList.add(new RunFloorIntake(IntakeState.INTAKEHOPPER));
			commandList.add(new RunTwinster(1));
			commandList.add(new Pause(2));
			commandList.add(new RunTwinster(1));
			commandList.add(new RunDrive(-10, 0, 1.5));
			commandList.add(new RunTurn(80,0,1));
			commandList.addAll(new VisionTest().getCommands());
			commandList.addAll(new ShootHopper().getCommands());
			break;
		}
	}
	
}
