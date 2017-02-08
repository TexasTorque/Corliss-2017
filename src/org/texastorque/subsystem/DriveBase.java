package org.texastorque.subsystem;

import org.texastorque.interfaces.TorqueSubsystem;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.util.TorqueMathUtil;

// instanced, include pertinent methods (remove this header before first commit)
public class DriveBase implements TorqueSubsystem{

	private Input i;
	
	private double leftSpeed;
	private double rightSpeed;
	
	public DriveBase() {
		i = HumanInput.getInstance();
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
		leftSpeed = 0.0;
		rightSpeed = 0.0;
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
		leftSpeed = i.getDB_leftSpeed();
		rightSpeed = i.getDB_rightSpeed();
		output();
	}

	private void output() {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, 1.0);
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, 1.0);
		RobotOutput.getInstance().setDriveBaseSpeed(leftSpeed, rightSpeed);
	}
	
	@Override
	public void smartDashboard() {

	}
	
}
