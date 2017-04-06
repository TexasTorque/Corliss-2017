package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.util.Side;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class AirShipSide extends AutonomousSequence {

	private Alliance alliance = DriverStation.getInstance().getAlliance();
	private boolean doCorner;
	private boolean doGear;

	private Side side;

	public AirShipSide(boolean doCorner, boolean doGear, Side side) {
		this.doCorner = doCorner;
		this.doGear = doGear;
		this.side = side;
	}
	
	@Override
	public void init() {
		alliance = Side.manageAlliance(alliance, side);
		switch (alliance) {
		case Red:
			if (doCorner) {
				commandList.add(new DistanceDrive(-83));
				commandList.add(new TurnAngle(87));
				commandList.add(new DistanceDrive(-69));
			} else {
				commandList.add(new DistanceDrive(-76));
				commandList.add(new TurnAngle(87));
				commandList.add(new DistanceDrive(-66));
			}
			break;
		case Blue:
			if (doCorner) {
				commandList.add(new DistanceDrive(-61));
				commandList.add(new TurnAngle(60));
				commandList.add(new DistanceDrive(-78));
			} else {
				commandList.add(new DistanceDrive(-88));
				commandList.add(new TurnAngle(111.25));
				commandList.add(new DistanceDrive(-85));
			}
			break;
		default:
			break;
		}
		if (doGear) {
			commandList.addAll(new PlaceGearSequence().getCommands());
		}
	}

}
