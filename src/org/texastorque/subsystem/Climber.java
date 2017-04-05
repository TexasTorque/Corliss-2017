package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

public class Climber extends Subsystem {

	private static Climber instance;
	
	private double speed = 0d;
	
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
		speed = 0;
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
	
	@Override
	public void disabledContinuous() {
		output();
	}
	
	private void run() {
		speed = i.getCL_speed();
		
		output();
	}
	
	private void output() {
		RobotOutput.getInstance().setClimberSpeed(speed);
	}
	
	public boolean isClimbing() {
		return speed > 0 ? true : false;
	}

	@Override
	public void smartDashboard() {
	}

	public static Climber getInstance() {
		return instance == null ? instance = new Climber() : instance;
	}
}
