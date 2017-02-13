package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem {

	private static FlyWheel instance;
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	
	private double gateSpeed;
	
	public FlyWheel() {
		init();
	}
	
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
		leftSpeed = i.getFW_leftSpeed();
		rightSpeed = i.getFW_rightSpeed();
		gateSpeed = i.getFW_gateSpeed();
		output();
	}

	private void output() {
		RobotOutput.getInstance().setFlyWheelSpeed(leftSpeed, rightSpeed, gateSpeed);
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("FW_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("FW_RIGHTSPEED", rightSpeed);
	}
	
	public static FlyWheel getInstance() {
		return instance == null ? instance = new FlyWheel() : instance;
	}

}
