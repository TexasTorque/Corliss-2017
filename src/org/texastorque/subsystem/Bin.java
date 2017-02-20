package org.texastorque.subsystem;

import org.texastorque.io.RobotOutput;

public class Bin extends Subsystem {

	private static Bin instance;
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	private void init() {
		RobotOutput.getInstance().setBinExtension(false);
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
		RobotOutput.getInstance().setBinExtension(true);
	}
	
	@Override
	public void smartDashboard() {
	}
	
	public static Bin getInstance() {
		return instance == null ? instance = new Bin() : instance;
	}
	
}
