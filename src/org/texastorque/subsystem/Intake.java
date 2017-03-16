package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

public class Intake extends Subsystem {

	private static Intake instance;
	
	private double speed = 0d;
	
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
//		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}

	private void run() {
		speed = i.getIN_speed();
		output();
	}
	
	private void output() {
		RobotOutput.getInstance().setIntakeSpeed(speed);
	}
	
	@Override
	public void smartDashboard() {
	}
	
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}

}
