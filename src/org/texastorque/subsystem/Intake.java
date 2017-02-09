package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

public class Intake extends Subsystem {

	private static Intake instance;
	
	private double lowerSpeed;
	private double upperSpeed;
	
	@Override
	public void autoInit() {
		lowerSpeed = 0d;
		upperSpeed = 0d;
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}

	private void init() {
		i = HumanInput.getInstance();
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
		lowerSpeed = i.getIN_lowerSpeed();
		upperSpeed = i.getIN_upperSpeed();
		output();
	}
	
	private void output() {
		RobotOutput.getInstance().setIntakeSpeed(upperSpeed, lowerSpeed);
	}
	
	@Override
	public void smartDashboard() {
	}
	
	public static Intake getInstance() {
		return instance == null ? instance = new Intake() : instance;
	}

}
