package org.texastorque.subsystem;

import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Conveyor extends Subsystem {

	private static Conveyor instance;

	private double leftSpeed;
	private double rightSpeed;

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
		leftSpeed = i.getCN_leftSpeed();
		rightSpeed = i.getCN_rightSpeed();
		output();
	}

	private void output() {
		RobotOutput.getInstance().setConveyorSpeed(leftSpeed, rightSpeed);
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("CN_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("CN_RIGHTSPEED", rightSpeed);
	}

	public static Conveyor getInstance() {
		return instance == null ? instance = new Conveyor() : instance;
	}

}
