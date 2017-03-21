package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear extends Subsystem implements Runnable {

	private static volatile Gear instance;
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	
	private boolean open = false;
	private boolean extended = false;
	private boolean scoopDown = false;
	
	
	@Override
	public void autoInit() { }

	@Override
	public void teleopInit() { }
	
	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	@Override
	public void run() {
		open = in.getGR_open();
		extended = in.getGH_extended();
		scoopDown = in.getGC_down();
		
		output();
	}
	
	public void output(){
		if(in instanceof HumanInput) {
			robotOut.openGearRamp(open);
			robotOut.extendGearHolder(extended);
			robotOut.deployGearCollector(scoopDown);
		}
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putBoolean("INTAKE_OPEN", open);
		SmartDashboard.putBoolean("ARM_EXTENDED", extended);
	}

	public static synchronized Gear getInstance() {
		return instance == null ? instance = new Gear() : instance;
	}
}
