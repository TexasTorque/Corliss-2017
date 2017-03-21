package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

public class Intake extends Subsystem implements Runnable {

	private static volatile Intake instance;
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	
	private double speed = 0d;
	
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
		speed = in.getIN_speed();
		robotOut.setIntakeSpeed(speed);
	}
	
	@Override
	public void smartDashboard() { }
	
	public static synchronized Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}

}
