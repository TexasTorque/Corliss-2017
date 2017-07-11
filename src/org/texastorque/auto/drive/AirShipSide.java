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
				switch(side) {
				case LEFT:
					commandList.add(new RunDrive(-77, .125, 2));
					commandList.add(new RunTurn(89, .125, 2));
					commandList.add(new RunDrive(-66, 0, 1.5));
					break;
				case RIGHT:
					commandList.add(new RunDrive(-70, .125, 2));
					commandList.add(new RunTurn(-89, .125, 2));
					commandList.add(new RunDrive(-61, 0, 1.5));
					break;
				}
			} else {
				commandList.add(new RunDrive(-78, .125, 2));
				commandList.add(new RunTurn(87, .125, 2));
				commandList.add(new RunDrive(-65, 0, 2));
			}
			break;
		case Blue:
			if (doCorner) {
				switch(side) {
				case LEFT:
					commandList.add(new RunDrive(-62, .125, 2.5));
					commandList.add(new RunTurn(60, .125, 1));
					commandList.add(new RunDrive(-80, 0, 2.5));
					break;
				case RIGHT:
					commandList.add(new RunDrive(-64, .125, 2.5));
					commandList.add(new RunTurn(-52, .125, 1));
					commandList.add(new RunDrive(-73, 0, 2.5));
					break;
				}
			} else {
				commandList.add(new RunDrive(-86, .125));
				commandList.add(new RunTurn(111.25, .125, 2));
				commandList.add(new RunDrive(-85, 0));
			}
			break;
		default:
			break;
		}
		if (doGear) {
			commandList.addAll(new PlaceGearSequence().getCommands());
		}
		switch (alliance) {
		case Red:
	//		if (doCorner) {
				switch(side) {
				case LEFT:
					commandList.add(new RunDrive(45, .125, 2));
					commandList.add(new RunTurn(-110, .125, 2));
					commandList.add(new RunDrive(-96, 0, 1.5));
					break;
				case RIGHT:
					commandList.add(new RunDrive(50, .125, 2));
					commandList.add(new RunTurn(89, .125, 2));
					commandList.add(new RunDrive(-91, 0, 1.5));
					break;
				}
		/*	} else {
				commandList.add(new RunDrive(-78, .125, 2));
				commandList.add(new RunTurn(87, .125, 2));
				commandList.add(new RunDrive(-65, 0, 2));
			} */
			break;
		case Blue:
			//if (doCorner) {
				switch(side) {
				case LEFT:
					commandList.add(new RunDrive(62, .125, 2.5));
					commandList.add(new RunTurn(-60, .125, 1));
					commandList.add(new RunDrive(-80, 0, 2.5));
					break;
				case RIGHT:
					commandList.add(new RunDrive(74, .125, 2.5));
					commandList.add(new RunTurn(52, .125, 1));
					commandList.add(new RunDrive(-73, 0, 2.5));
					break;
				}
			/*} else {
				commandList.add(new RunDrive(-86, .125));
				commandList.add(new RunTurn(111.25, .125, 2));
				commandList.add(new RunDrive(-85, 0));
			}*/
			break;
		default:
			break;

	}

   }
	
}
