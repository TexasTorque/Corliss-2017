package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear extends Subsystem{

	private static Gear instance;
	
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
		open=i.getGR_open();
		extended=i.getGH_extended();
		output();
	}
	
	public void output(){
		RobotOutput.getInstance().openGearRamp(open);
		RobotOutput.getInstance().extendGearHolder(extended);
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
