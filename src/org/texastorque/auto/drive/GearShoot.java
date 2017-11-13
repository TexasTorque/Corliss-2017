package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.intake.RunFloorIntake;
import org.texastorque.auto.intake.RunFloorIntake.IntakeState;
import org.texastorque.auto.shooter.RunGate;
import org.texastorque.auto.shooter.RunShooter;
import org.texastorque.auto.shooter.RunShooter.Setpoints;
import org.texastorque.auto.shooter.ShootBoilerLayup;
import org.texastorque.auto.twinsters.RunTwinster;
import org.texastorque.auto.util.Pause;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class GearShoot extends AutonomousSequence {

	private Alliance alliance;
	
	public GearShoot() {
		alliance = DriverStation.getInstance().getAlliance();
		init();
	}
	
	@Override
	public void init() {
		switch(alliance) {
		case Red:
			/*commandList.add(new RunDrive(-93, .125, 2.5));
			commandList.add(new RunTurn(-60, .125, 1));
			commandList.add(new RunDrive(-30, 0, 2));
			commandList.addAll(new PlaceGearSequence().getCommands());
			commandList.add(new RunDrive(55, .125, 2.5));
			commandList.add(new RunTurn(45, .125, 1));  //RunTurn(45, .125, 1);
			break;
			*/
			
			commandList.addAll(new AirShipCenter(true).getCommands());
			commandList.add(new RunShooter(Setpoints.LAYUP));
			commandList.add(new RunTurn(-90, .125, 1.25));
			commandList.add(new RunDrive(110, .125, 2));
			commandList.add(new RunTurn(49, .125, 1.5));
			commandList.add(new RunDrive(-10.5, .0625, 1));
			break;
		case Blue:
			/*
			commandList.add(new RunDrive(-94, .125, 2.5));
			commandList.add(new RunTurn(60, .125, 1));
			commandList.add(new RunDrive(-30, 0, 2));
			commandList.addAll(new PlaceGearSequence().getCommands());
			commandList.add(new RunDrive(55, .125, 2.5));
			commandList.add(new RunTurn(-45, .125, 1));  
			break;
			*/

			commandList.addAll(new AirShipCenter(true).getCommands());
			commandList.add(new RunShooter(Setpoints.LAYUP));
			commandList.add(new RunTurn(90, .125, 1.25));
			commandList.add(new RunDrive(110, .125, 2));
			commandList.add(new RunTurn(-56, .125, 1.5));
			commandList.add(new RunDrive(-10.5, .0625, 1));
			break;
		}// TODO Auto-generated method stub

		commandList.add(new RunTwinster(1));
		commandList.add(new RunGate(1));
		commandList.add(new RunFloorIntake(IntakeState.INTAKE));
		
	}

	
}
