package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class BoilerGear extends AutonomousSequence {

	private boolean doGear;
	private Alliance alliance;
	
	public BoilerGear(boolean placeGear) {
		this.doGear = placeGear;
		alliance = DriverStation.getInstance().getAlliance();

		init();
	}

	@Override
	public void init() {
		switch(alliance) {
		case Blue:
			commandList.add(new RunDrive(-24));
			commandList.add(new RunTurn(-45));
			commandList.add(new RunDrive(-60));
			commandList.add(new RunTurn(60));
			commandList.add(new RunDrive(-63));
			break;
		case Red:
			commandList.add(new RunDrive(-24));
			commandList.add(new RunTurn(45));
			commandList.add(new RunDrive(-60));
			commandList.add(new RunTurn(-60));
			commandList.add(new RunDrive(-63));
			break;
		}
		if(doGear)
			commandList.addAll(new PlaceGearSequence().getCommands());
	}
	
}
