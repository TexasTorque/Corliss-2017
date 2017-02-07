package org.texastorque.subsystem;

import org.texastorque.interfaces.I_DriveBase;

// instanced, include pertinent methods (remove this header before first commit)
public class DriveBase implements I_DriveBase{

	
	
	@Override
	public void teleopInit() {
		init();
	}

	private void init() {
//		shared code goes here
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
