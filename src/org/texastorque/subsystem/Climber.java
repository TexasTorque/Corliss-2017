package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

public class Climber extends Subsystem implements Runnable {
	
	private static volatile Climber instance;
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	
	private double speed = 0d;
	
	@Override
	public void autoInit() { }

	@Override
	public void teleopInit() { }
	
	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}
	
	@Override
	public void run() {
		speed = in.getCL_speed();

		robotOut.setClimberSpeed(speed);
	}
	
	public boolean isClimbing() {
		return speed > 0 ? true : false;
	}

	@Override
	public void smartDashboard() {
	}

	public static synchronized Climber getInstance() {
		return instance == null ? instance = new Climber() : instance;
	}
}
