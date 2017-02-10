package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Pneumatics extends Subsystem{

	private static Pneumatics instance;
	
	private boolean open;
	private boolean extended;
	
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	public void init(){
		i=HumanInput.getInstance();
	}
	
	@Override
	public void autoContinuous() {
		
	}

	@Override
	public void teleopContinuous() {
		
	}

	public void run(){
		open=i.getGI_open();
		extended=i.getGA_extended();
		output();
	}
	
	public void output(){
		RobotOutput.getInstance().openGearIntake(open);
		RobotOutput.getInstance().extendGearArm(extended);
	}
	
	public static Pneumatics getInstance() {
		return instance == null ? instance = new Pneumatics() : instance;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putBoolean("INTAKE_OPEN", open);
		SmartDashboard.putBoolean("ARM_EXTENDED", extended);
	}

}
