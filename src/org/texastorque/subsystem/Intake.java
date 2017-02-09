package org.texastorque.subsystem;

public class Intake extends Subsystem {

	private static Intake instance;
	
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
		output();
	}
	
	private void output() {
		
	}
	
	@Override
	public void smartDashboard() {
	}
	
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}

}
