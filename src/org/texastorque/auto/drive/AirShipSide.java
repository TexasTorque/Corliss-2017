package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.util.Side;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class AirShipSide extends AutonomousSequence {

	private Alliance alliance;
	private boolean doCorner;
	private boolean doGear;

	private Side side;

	public AirShipSide(boolean doCorner, boolean doGear, Side side) {
		this.doCorner = doCorner;
		this.doGear = doGear;
		this.side = side;
		alliance = DriverStation.getInstance().getAlliance();
		init();
	}
	
	@Override
	public void init() {
		alliance = Side.manageAlliance(alliance, side);
		switch (alliance) {
		case Red:
			if (doCorner) {
				commandList.add(new RunDrive(-79));
				commandList.add(new RunTurn(-89));
				commandList.add(new RunDrive(-66));
			} else {
				commandList.add(new RunDrive(-76));
				commandList.add(new RunTurn(87));
				commandList.add(new RunDrive(-66));
			}
			break;
		case Blue:
			if (doCorner) {
				commandList.add(new RunDrive(-65));
				commandList.add(new RunTurn(60));
				commandList.add(new RunDrive(-78));
			} else {
				commandList.add(new RunDrive(-88));
				commandList.add(new RunTurn(111.25));
				commandList.add(new RunDrive(-85));
			}
			break;
		default:
			break;
		}
		if (doGear) {
			commandList.addAll(new PlaceGearSequence().getCommands());
			commandList.addAll(new PlaceGearSequence().getCommands());
		}
	}

}
