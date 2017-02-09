package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem {

	private static FlyWheel instance;
	
	private double leftSpeed;
	private double rightSpeed;
	
	public FlyWheel() {
		leftSpeed = 0d;
		rightSpeed = 0d;
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
		i = HumanInput.getInstance();
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
		output();
	}

	private void output() {
		RobotOutput.getInstance().setDriveFlyWheelSpeed(leftSpeed, rightSpeed);
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
