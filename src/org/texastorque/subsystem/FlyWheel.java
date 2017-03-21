package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePID;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem implements Runnable {

	private static volatile FlyWheel instance;
	
	private static final double ALLOWABLE_ERROR_FLYWHEEL = Constants.FW_ACCEPTABLE.getDouble();
	
	private final RobotOutput robotOut = RobotOutput.getInstance();
	private final Feedback feedback = Feedback.getInstance();
	private final Lights lights = Lights.getInstance();
	private final Climber climber = Climber.getInstance();

	private double leftSpeed = 0d;
	private double rightSpeed = 0d;

	private TorquePID leftFlywheelControl;
	private TorquePID rightFlywheelControl;
	private double setpointLeft;
	private double setpointRight;

	private double leftP = 6;
	private double leftI = 20;
	private double leftD = 15;

	private double rightP = 6;
	private double rightI = 20;
	private double rightD = 15;

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

	@Override
	public void run() {
		setpointLeft = in.getFW_leftSetpoint();
		setpointRight = in.getFW_rightSetpoint();
		
		if (setpointLeft != 0) {
			doLight = true;
			leftFlywheelControl.setSetpoint(1);
			leftSpeed = leftFlywheelControl.calculate(feedback.getFW_leftRate() / setpointLeft);
			
			if (leftSpeed < 0) {
				leftSpeed = 0;
			}
		} else {
			leftFlywheelControl.reset();
			leftSpeed = 0;
			doLight = false;
		}
		
		if (setpointRight != 0) {
			doLight = true;
			rightFlywheelControl.setSetpoint(1);
			rightSpeed = rightFlywheelControl.calculate(feedback.getFW_rightRate() / setpointRight);
			
			if (rightSpeed < 0)
				rightSpeed = 0;
		} else {
			rightFlywheelControl.reset();
			rightSpeed = 0;
			doLight = false;
		}
		
		double errorLeft = Math.abs(in.getFW_leftSetpoint() - feedback.getFW_leftRate());
		double errorRight = Math.abs(in.getFW_rightSetpoint() - feedback.getFW_rightRate());
		boolean flywheelSpinning = in.getFW_leftSetpoint() != 0 || in.getFW_rightSetpoint() != 0;
		
		boolean flywheelReady = flywheelSpinning
				&& errorLeft < ALLOWABLE_ERROR_FLYWHEEL
				&& errorRight < ALLOWABLE_ERROR_FLYWHEEL;
		
		if (flywheelReady) {
			in.setRumble(true);
			doLight = false;
		} else {
			if(Timer.getFPGATimestamp() - lt > .5) {
				in.setRumble(false);
				lt = Timer.getFPGATimestamp();
			}
		}
//		RobotOutput.getInstance().setLight(doLight);
		gateSpeed = in.getFW_gateSpeed();
		hood = in.getFW_hood();
		lights.set(climber.isClimbing(), feedback.getFW_leftRate(), in.getFW_leftSetpoint());
		output();
	}

	private void output() {
		robotOut.setFlyWheelSpeed(leftSpeed, rightSpeed);
		robotOut.setGateSpeed(gateSpeed, gateSpeed);
		robotOut.setHoodSpeed(hood);
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

	public static synchronized FlyWheel getInstance() {
		return instance == null ? instance = new FlyWheel() : instance;
	}
}
