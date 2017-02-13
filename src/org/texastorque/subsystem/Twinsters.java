package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Twinsters extends Subsystem {

	private static Twinsters instance;
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	private double feederSpeed = 0d;
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	private void init() {
	}
	
	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	private void run() {
		leftSpeed = i.getTW_leftSpeed();
		rightSpeed = i.getTW_rightSpeed();
		feederSpeed = i.getTW_feederSpeed();
		
		output();
	}
	
	private void output() {
		RobotOutput.getInstance().setTwinstersSpeed(leftSpeed, rightSpeed);
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("TW_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("TW_RIGHTSPEED", rightSpeed);
	}
	
	public static Twinsters getInstance() {
		return instance == null ? instance = new Twinsters() : instance;
	}

}
