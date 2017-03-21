package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Twinsters extends Subsystem implements Runnable {

	private static volatile Twinsters instance;
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	private double feederSpeed = 0d;
	
	@Override
	public void autoInit() { }

	@Override
	public void teleopInit() { }
	
	@Override
	public void autoContinuous() {
//		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	@Override
	public void run() {
		leftSpeed = in.getTW_leftSpeed();
		rightSpeed = in.getTW_rightSpeed();
		feederSpeed = in.getTW_feederSpeed();

		robotOut.setTwinstersSpeed(leftSpeed, rightSpeed, feederSpeed);
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("TW_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("TW_RIGHTSPEED", rightSpeed);
	}
	
	public static synchronized Twinsters getInstance() {
		return instance == null ? instance = new Twinsters() : instance;
	}
}
