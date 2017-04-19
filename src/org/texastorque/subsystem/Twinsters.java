package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Twinsters extends Subsystem {

	private static Twinsters instance;
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}
	
	@Override
	public void disabledInit() {
		leftSpeed = 0;
		rightSpeed = 0;
	}

	private void init() {
	}
	
	@Override
	public void autoContinuous() {
//		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}
	
	@Override
	public void disabledContinuous() {
		output();
	}

	private void run() {
		leftSpeed = i.getTW_leftSpeed();
		rightSpeed = i.getTW_rightSpeed();
		
		output();
	}
	
	private void output() {
		if(i instanceof HumanInput) {
			if(i.getVI_rpmsGood()) {
				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
			} else {
				RobotOutput.getInstance().setTwinstersSpeed(leftSpeed, rightSpeed);
			}
		}
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
