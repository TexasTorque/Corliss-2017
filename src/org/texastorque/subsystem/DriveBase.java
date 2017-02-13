package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorqueSMP;
import org.texastorque.torquelib.util.TorqueMathUtil;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

// instanced, include pertinent methods (remove this header before first commit)
public class DriveBase extends Subsystem {

	private static DriveBase instance;
	
	private double leftSpeed = 0d;
	private double rightSpeed = 0d;
	
	private TorqueSMP smpL;
	private TorqueSMP smpR;
	
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
		smpL = new TorqueSMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble());
		smpR = new TorqueSMP(Constants.DB_MVELOCITY.getDouble(), Constants.DB_MACCELERATION.getDouble());
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
		smpL.generate(Feedback.getInstance().getDB_leftDistance());
		smpR.generate(Feedback.getInstance().getDB_rightDistance());
		
		leftSpeed = smpL.getVelocity(Timer.getFPGATimestamp());
		rightSpeed = smpR.getVelocity(Timer.getFPGATimestamp());
		
		output();
	}
	
	private void output() {
		leftSpeed = TorqueMathUtil.constrain(leftSpeed, 1.0);
		rightSpeed = TorqueMathUtil.constrain(rightSpeed, 1.0);
		RobotOutput.getInstance().setDriveBaseSpeed(leftSpeed, rightSpeed);
	}
	
	@Override
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTSPEED", leftSpeed);
		SmartDashboard.putNumber("DB_RIGHTSPEED", rightSpeed);
	}
	
	public static DriveBase getInstance() {
		return instance == null ? instance = new DriveBase() : instance;
	}
	
}
