package org.texastorque.subsystem;

public class Conveyor extends Subsystem {

	private static Conveyor instance;
	
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

	public static Conveyor getInstance() {
		return instance == null ? instance = new Conveyor() : instance;
	}
	
}
