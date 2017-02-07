package org.texastorque.subsystem;

import org.texastorque.interfaces.TorqueSubsystem;

// instanced, include pertinent methods (remove this header before first commit)
public class DriveBase implements TorqueSubsystem{

	public DriveBase() {
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
//		shared code goes here
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
//		shared code goes here
	}
	
	@Override
	public void smartDashboard() {

	}
	
}
