package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem {

	private static FlyWheel instance;

	private double leftSpeed = 0d;
	private double rightSpeed = 0d;

	private TorquePID leftFlywheelControl;
	private TorquePID rightFlywheelControl;
	private double setpointLeft;
	private double setpointRight;

	private double leftP = 6;
	private double leftI = 20; //20
	private double leftD = 10;

	private double rightP = 6;
	private double rightI = 20;
	private double rightD = 10;

	private double lt = Timer.getFPGATimestamp();

	private boolean hood;
	private boolean doLight;
	
	private double gateSpeed;

	public FlyWheel() {
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
		SmartDashboard.putNumber("FW_PIDL_P", leftP);
		SmartDashboard.putNumber("FW_PIDL_I", leftI);
		SmartDashboard.putNumber("FW_PIDL_D", leftD);

		SmartDashboard.putNumber("FW_PIDR_P", rightP);
		SmartDashboard.putNumber("FW_PIDR_I", rightI);
		SmartDashboard.putNumber("FW_PIDR_D", rightD);

		leftFlywheelControl = new TorquePID(leftP, leftI, leftD);
		leftFlywheelControl.setControllingSpeed(true);
		leftFlywheelControl.setEpsilon(350);

		rightFlywheelControl = new TorquePID(rightP, rightI, rightD);
		rightFlywheelControl.setControllingSpeed(true);
		rightFlywheelControl.setEpsilon(350);
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
		setpointLeft = i.getFW_leftSetpoint();
		setpointRight = i.getFW_rightSetpoint();
		if (setpointLeft != 0) {
			doLight = true;
			leftFlywheelControl.setSetpoint(1);
			leftSpeed = leftFlywheelControl.calculate(Feedback.getInstance().getFW_leftRate() / setpointLeft);
			if (leftSpeed < 0)
				leftSpeed = 0;
		} else {
			leftFlywheelControl.reset();
			leftSpeed = 0;
			doLight = false;
		}
		if (setpointRight != 0) {
			doLight = true;
			rightFlywheelControl.setSetpoint(1);
			rightSpeed = rightFlywheelControl.calculate(Feedback.getInstance().getFW_rightRate() / setpointRight);
			if (rightSpeed < 0)
				rightSpeed = 0;
		} else {
			rightFlywheelControl.reset();
			rightSpeed = 0;
			doLight = false;
		}
		if ((i.getFW_leftSetpoint() != 0 || i.getFW_rightSetpoint() != 0)
				&& Math.abs(i.getFW_leftSetpoint() - Feedback.getInstance().getFW_leftRate()) < Constants.FW_ACCEPTABLE.getDouble()
				&& Math.abs(i.getFW_rightSetpoint() - Feedback.getInstance().getFW_rightRate()) < Constants.FW_ACCEPTABLE.getDouble()) {
			i.setRumble(true);
			doLight = false;
		} else {
			if(Timer.getFPGATimestamp() - lt > .5) {
				i.setRumble(false);
				lt = Timer.getFPGATimestamp();
			}
		}
		RobotOutput.getInstance().setLight(doLight);
		gateSpeed = i.getFW_gateSpeed();
		hood = i.getFW_hood();
		output();
	}

	private void output() {
		RobotOutput.getInstance().setFlyWheelSpeed(leftSpeed, rightSpeed);
		RobotOutput.getInstance().setGateSpeed(gateSpeed, gateSpeed);
		RobotOutput.getInstance().setHoodSpeed(hood);
	}

	public void updatePID() {
		leftFlywheelControl.setPIDGains(leftP, leftI, leftD);
		rightFlywheelControl.setPIDGains(rightP, rightI, rightD);
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("FW_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("FW_RIGHTSPEED", rightSpeed);
		SmartDashboard.putNumber("FW_LEFTSETPOINT", setpointLeft);
		SmartDashboard.putNumber("FW_RIGHTSETPOINT", setpointRight);
		SmartDashboard.putBoolean("FW_HOODUP",hood);
		updatePID();
	}

	public static FlyWheel getInstance() {
		return instance == null ? instance = new FlyWheel() : instance;
	}

}
