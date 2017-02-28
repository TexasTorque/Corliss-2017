package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear extends Subsystem{

	private static Gear instance;
	
	private boolean open = false;
	private boolean extended = false;
	
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	public void init(){
	}
	
	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	public void run(){
		open=i.getGR_open();
		extended=i.getGH_extended();
		output();
	}
	
	public void output(){
		if(i instanceof HumanInput) {
			RobotOutput.getInstance().openGearRamp(open);
			RobotOutput.getInstance().extendGearHolder(extended);
		}
	}
	
	public static Gear getInstance() {
		return instance == null ? instance = new Gear() : instance;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putBoolean("INTAKE_OPEN", open);
		SmartDashboard.putBoolean("ARM_EXTENDED", extended);
	}

}
