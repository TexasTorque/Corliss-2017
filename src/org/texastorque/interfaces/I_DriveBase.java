package org.texastorque.interfaces;

public interface I_DriveBase {

//	inits any values that are necessary (nothing)
	public default void autonomousInit(){};
	
	public void teleopInit();
	
//	run any calculations, get updates from any relevant sensors, send output to robotoutput
	public void teleopContinuous();
	
//	output any variables that are needed for debugging
	public void smartDashboard();
	
}
