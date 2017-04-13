package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePID;
import org.texastorque.torquelib.controlLoop.TorqueRIMP;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class FlyWheel extends Subsystem {

	private static FlyWheel instance;

	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	private double gateSpeed;
	private boolean hood = false;

	private TorquePID leftFlywheelControl;
	private TorquePID rightFlywheelControl;
	private double setpointLeft;
	private double setpointRight;

	private double leftP = .0081;
	private double leftI = .0576; //20
	private double leftD = .0028;

	private double rightP = .108;
	private double rightI = 3.81;
	private double rightD = .0007741;

	private double lt = Timer.getFPGATimestamp();
	private boolean doLight;

//	temporary vars for experimentation with flywheel
	private boolean hasTriggered = false;
	private double cachedTime;
	private boolean temporaryOverride = true;
	private TorqueRIMP leftRIMP;
	private TorqueRIMP rightRIMP;
	private boolean temporaryRIMPTest;

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
	
	@Override
	public void disabledInit() {
		leftSpeed = 0;
		rightSpeed = 0;
		gateSpeed = 0;
		hood = false;
		doLight = false;
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
		
		leftFlywheelControl.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		rightFlywheelControl.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
		
		rightRIMP = new TorqueRIMP(Constants.FW_MVELOCITY.getDouble(), Constants.FW_MACCELERATION.getDouble(), 0);
		leftRIMP = new TorqueRIMP(Constants.FW_MVELOCITY.getDouble(), Constants.FW_MACCELERATION.getDouble(), 0);
		
		leftRIMP.setGains(Constants.FW_RIMP_P.getDouble(), Constants.FW_RIMP_V.getDouble(),
				Constants.FW_RIMP_ffV.getDouble(), Constants.FW_RIMP_ffA.getDouble());
		rightRIMP.setGains(Constants.FW_RIMP_P.getDouble(), Constants.FW_RIMP_V.getDouble(),
				Constants.FW_RIMP_ffV.getDouble(), Constants.FW_RIMP_ffA.getDouble());

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
	public void disabledContinuous() {
		output();
	}

	private void run() {
		if(temporaryRIMPTest) {
			double error = i.getFW_leftSetpoint() - f.getFW_leftRate();
			leftSpeed = leftRIMP.calculate(error, f.getFW_leftRate());
			error = i.getFW_rightSetpoint() - f.getFW_rightRate();
			rightSpeed = rightRIMP.calculate(error, f.getFW_rightRate());
			if(leftSpeed < 0)
				leftSpeed = 0;
			if(rightSpeed < 0)
				rightSpeed = 0;
		} else {
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
			if(i.getG_press()) {
				if(!hasTriggered) {
					hasTriggered = true;
					cachedTime = Timer.getFPGATimestamp();
				}
			} else {
				hasTriggered = false;
			}
			
			gateSpeed = i.getFW_gateSpeed();
			hood = i.getFW_hood();
			Lights.getInstance().set(Climber.getInstance().isClimbing(), Feedback.getInstance().getFW_leftRate(), i.getFW_leftSetpoint());
			}
		output();
	}

	private void output() {
		if(temporaryOverride) {
			double add = 0;
			if(i.getG_press()) {
				double dt = Timer.getFPGATimestamp() - cachedTime;
				add = .044 + (dt * .04);
				if(add > .54)
					add = .54;
			}
			System.out.println(add);
			RobotOutput.getInstance().setFlyWheelSpeed(leftSpeed, rightSpeed);
			RobotOutput.getInstance().setFlyWheelSpeed(.53 + add , .53 + add);
		} else {
//			RobotOutput.getInstance().setFlyWheelSpeed(.2, .2);
			RobotOutput.getInstance().setGateSpeed(gateSpeed, gateSpeed);
			if(!DriverStation.getInstance().isAutonomous())
				RobotOutput.getInstance().setHoodSpeed(hood);
		}
	}

	public void updatePID() {
		leftP = SmartDashboard.getNumber("FW_PIDL_P", leftP);
		leftI = SmartDashboard.getNumber("FW_PIDL_I", leftI);
		leftD = SmartDashboard.getNumber("FW_PIDL_D", leftD);

		rightP = SmartDashboard.getNumber("FW_PIDR_P", rightP);
		rightI = SmartDashboard.getNumber("FW_PIDR_I", rightI);
		rightD = SmartDashboard.getNumber("FW_PIDR_D", rightD);

		leftFlywheelControl.setPIDGains(leftP, leftI, leftD);
		rightFlywheelControl.setPIDGains(rightP, rightI, rightD);
	}

	@Override
	public void smartDashboard() {
		updatePID();
		SmartDashboard.putNumber("FW_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("FW_RIGHTSPEED", rightSpeed);
		SmartDashboard.putNumber("FW_LEFTSETPOINT", setpointLeft);
		SmartDashboard.putNumber("FW_RIGHTSETPOINT", setpointRight);
		SmartDashboard.putBoolean("FW_HOODUP",hood);
	}

	public static FlyWheel getInstance() {
		return instance == null ? instance = new FlyWheel() : instance;
	}

}
