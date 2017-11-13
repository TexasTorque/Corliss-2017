package org.texastorque.auto.drive;

import org.texastorque.auto.AutonomousSequence;
import org.texastorque.auto.gear.PlaceGearSequence;
import org.texastorque.auto.util.Pause;

import edu.wpi.first.wpilibj.DriverStation;

public class AirShipCenter extends AutonomousSequence {

	boolean dropGear;
	boolean scurry = false;
	
	public AirShipCenter(boolean dropGear) {
		this.dropGear = dropGear;
		init();
	}
	
	@Override
	public void init() {
		switch(DriverStation.getInstance().getAlliance()) {
			case Red:
				commandList.add(new RunDrive(-79, .0125, 1.25));
				break;
			case Blue:
				commandList.add(new RunDrive(-78, .0125, 1.5));
				break;
		}
		commandList.addAll(new PlaceGearSequence().getCommands());
			
		if(scurry){
			switch(DriverStation.getInstance().getAlliance()){
				case Red:
					commandList.add(new RunTurn(90, .125, 2));
					commandList.add(new RunDrive(75, .125, 2));
					commandList.add(new RunTurn(90, .125, 2));
					commandList.add(new RunDrive(200, .125, 4));
					break;
				case Blue:
					commandList.add(new RunTurn(-90, .125, 2));
					commandList.add(new RunDrive(75, .125, 2));
					commandList.add(new RunTurn(-90, .125, 2));
					commandList.add(new RunDrive(200, .125, 4));
					break;
				default:
					break;
			}
		}
	}
	
}
