package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.util.Side;

import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;

public class AirShipSide extends AutonomousSequence {

	XMLSerializer s = new XMLSerializer(); 
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
					commandList.add(new RunDrive(-81, .125, 2));
					commandList.add(new RunTurn(89, .125, 2));
					commandList.add(new RunDrive(-67, 0, 1.5));
					break;
				case RIGHT:
					commandList.add(new RunDrive(-70, .125, 1.25));
					commandList.add(new RunTurn(-89, .125, 1));
					commandList.add(new RunDrive(-61, 0, 1.25));
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
					commandList.add(new RunDrive(-65.5, .125, 2.5));
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
			//commandList.add(new RunTurn(26, .125, 1));
			//commandList.add(new RunDrive(200, .125, 4));
			
		}
	switch (alliance) {
		
		
		case Red:
	//		if (doCorner) {
				switch(side) {
				case LEFT:
					commandList.add(new RunTurn(-65, .125, 2));
					commandList.add(new RunDrive(-280, 0, 1.5));
					break;
				default:
					break;
		
				}
		/*	} else {
				commandList.add(new RunDrive(-78, .125, 2));
				commandList.add(new RunTurn(87, .125, 2));
				commandList.add(new RunDrive(-65, 0, 2));
			} */
			break;
		/*case Blue:
			//if (doCorner) {
				switch(side) {
				case RIGHT:
					commandList.add(new RunTurn(45, .125, 1));
	//				commandList.add(new RunDrive(-130, 0, 2.5));
					break;
				default:
					break;
				}
			/*} else {
				commandList.add(new RunDrive(-86, .125));
				commandList.add(new RunTurn(111.25, .125, 2));
				commandList.add(new RunDrive(-85, 0));
			}*/
		
		default:
			break;

	}

   }
	
}
