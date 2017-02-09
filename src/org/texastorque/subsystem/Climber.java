package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;

public class Climber extends Subsystem {

	private static Climber instance;
	
	private double speed;
	
	@Override
	public void autoInit() {
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
		speed = i.getCL_speed();
		
		output();
	}
	
	private void output() {
		
	}

	@Override
	public void smartDashboard() {
	}

	public static Climber getInstance() {
		return instance == null ? instance = new Climber() : instance;
	}
}
