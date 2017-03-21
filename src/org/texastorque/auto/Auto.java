package org.texastorque.auto;

import java.util.ArrayList;

import org.texastorque.feedback.Feedback;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.DriveBase.DriveType;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto implements Runnable {

	/*
	 * mode 0 - null mode, do nothing mode 1 - gear a left to left * mode 2 -
	 * gear b center to center mode 3 - gear c center-right (next to key) to
	 * right mode 14 - move to hopper (from far) mode 24 - move to hopper (from
	 * center) mode 34 - move to hopper (from near)
	 */

	/* Singleton */
	
	private static Auto instance;
	
	
	/* Static */
	
	private static ArrayList<Integer> autoComponents;
	private static int autoMode;
	private static ArrayList<Integer> previousModes = new ArrayList<>();
	

	/* Instance */
	
	private final Input autoInput = Input.getInstance();
	private final RobotOutput robotOut = RobotOutput.getInstance();
	private final Feedback feedback = Feedback.getInstance();
	private final DriveBase driveBase = DriveBase.getInstance();
	private final Alliance currentAlliance = DriverStation.getInstance().getAlliance();
	
	private boolean doGearCenter = true;
	private boolean doGearSide = false;
	private boolean cornerAuto = true;
	private boolean isActionDone = false;

	private Thread thread;

	public Auto() {
		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", -1);
		analyzeAutoMode();
		
		thread = new Thread(this);
		thread.start();
	}

	protected final void pause(double seconds) {
		pause(seconds, true);
	}

	protected final void pause(double seconds, boolean allowInterupts) {
		double start = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - start < seconds) {
			if (allowInterupts && isActionDone) {
				isActionDone = false;
				break;
			}
			try {
				Thread.sleep(1);
			} catch (Exception e) {
			}
		}
		System.out.println("DONE SLEEPING");
	}

	/**
	 * This method is called to initialize the autonomous process.
	 */
	public void run() {
		robotOut.upShift(false);
		robotOut.extendGearHolder(false);
		feedback.resetDB_gyro();
		feedback.resetDB_encoders();
		
		while (autoComponents.size() != 0) {
			int lastIndex = autoComponents.size() - 1;
			int currentStep = autoComponents.remove(lastIndex);
			
			switch (currentStep) {
			// No Sensors
			case 1:
				driveBase.setType(DriveType.AUTOOVERRIDE);
				robotOut.setDriveBaseSpeed(-.5, -.5);
				pause(3.0);
				robotOut.setDriveBaseSpeed(0d, 0d);
				break;
				
			// Left Gear
			case 2:
				if (cornerAuto)
					cornerAuto2();
				else
					auto2();
				if (doGearSide) {
					dropGear();
				}
				break;
				
			// Center Gear
			case 3:
				drive(-76);
				pause(4.0);
				if (doGearCenter) {
					dropGear();
				}
				break;
				
			// Right Gear
			case 4:
				if (cornerAuto) {
					cornerAuto4();
				} else {
					auto4();
				}
				
				if (doGearSide) {
					dropGear();
				}
				break;
			case 5:
				switch (previousModes.get(previousModes.size() - 1)) {
				case 2:
					driveToHopper();
					break;
				case 4:
					break;
				}
				break;
				
			case 6:
				autoShoot();
				break;
				
			case 7:
				switch (previousModes.get(previousModes.size() - 1)) {
				case 4:
					scurry4();
					break;
				case 2:
					scurry2();
					break;
				}
				break;
				
			case 0:
				break;
				
			default:
				System.out.println("INVALID STEP OR NULL. . ." + currentStep);
				break;
			}
			previousModes.add(currentStep);
		}
	}

	public void driveToHopper() {
		drive(76);
		pause(3.0);
		turn(30);
		pause(2.0);
		drive(35);
		pause(3.0);
	}

	public void autoShoot() {
		switch (previousModes.get(previousModes.size() - 1)) {
		case 5:
			switch (previousModes.get(previousModes.size() - 2)) {
			case 2:
				autoInput.setFW_leftSetpoint(4200);
				autoInput.setFW_rightSetpoint(4050);
				
				pause(3.0, false);
				drive(-8);
				pause(.5);
				turn(-90);
				pause(.75, false);
				
				robotOut.setTwinstersSpeed(1, 1, 1);
				robotOut.setIntakeSpeed(1);
				autoInput.setFW_gateSpeed(.4);
				break;
			}
			break;
		case 2:
			switch (currentAlliance) {
			case Blue:
				pause(.5, false);
				drive(20);
				pause(1.5);
				turn(-15);
				pause(.5);
				drive(96);
				
				autoInput.setFW_leftSetpoint(3000);
				autoInput.setFW_rightSetpoint(2850);
				
				pause(3.0);
				
				robotOut.setTwinstersSpeed(1, 1, 1);
				robotOut.setIntakeSpeed(1);
				autoInput.setFW_gateSpeed(.4);
				break;
			}
			break;
		case 4:
			switch (currentAlliance) {
			case Red:
				pause(.5, false);
				drive(20);
				pause(1.5);
				turn(15);
				pause(.5);
				drive(96);
				
				autoInput.setFW_leftSetpoint(3000);
				autoInput.setFW_rightSetpoint(2850);
				
				pause(3.0);
				
				robotOut.setTwinstersSpeed(1, 1, 1);
				robotOut.setIntakeSpeed(1);
				autoInput.setFW_gateSpeed(.4);
				break;
			}
			break;
		}
	}

	public void visionAlign() {
		do {
			SmartDashboard.putString("GOODPACKET", "FALSE");
		} while (!feedback.getPX_goodPacket());
		SmartDashboard.putString("GOODPACKET", "TRUE");
		
		turn(feedback.getPX_HorizontalDegreeOff());
		pause(2.0);
	}

	public void ultrasonicDrive() {
		driveBase.setType(DriveType.AUTOIRDRIVE);
		pause(5.0, false);
	}

	private void cornerAuto2() {
		switch (currentAlliance) {
		case Red:
			drive(-75);
			pause(3.0);
			turn(87);
			pause(3.0);
			drive(-69);
			pause(3.0);
			break;
		case Blue:
			drive(-61);
			pause(5.0);
			turn(60);
			pause(1);
			drive(-78);
			pause(5.0);
			break;
		default:
			break;
		}
	}

	private void cornerAuto4() {
		switch (currentAlliance) {
		case Red:
			drive(-61);
			pause(5.0);
			turn(-60);
			pause(1);
			drive(-78);
			pause(5.0);
			break;
		case Blue:
			drive(-75);
			pause(3.0);
			turn(-87);
			pause(3.0);
			drive(-69);
			pause(3.0);
			break;
		default:
			break;
		}
	}

	private void auto2() {
		switch (currentAlliance) {
		case Red:
			drive(-76);
			pause(3.0);
			turn(87);
			pause(3.0);
			drive(-66);
			pause(3.0);
			break;
		case Blue:
			drive(-88);
			pause(4.0);
			turn(111.25);
			pause(3.0);
			drive(-85);
			pause(3.0);
			break;
		default:
			break;
		}
	}

	private void auto4() {
		switch (currentAlliance) {
		case Red:
			drive(-88);
			pause(4.0);
			turn(-111);
			pause(3.0);
			drive(-85);
			pause(3.0);
			break;
		case Blue:
			drive(-76);
			pause(3.0);
			turn(-87);
			pause(3.0);
			drive(-64);
			pause(3.0);
			break;
		default:
			break;
		}
	}

	private void scurry2() {
		switch(currentAlliance) {
		case Red:
			drive(40);
			pause(2.0);
			turn(-60);
			pause(1.0);
			drive(-288);
			pause(10.0);
			break;
		case Blue:
			drive(40);
			pause(2.0);
			turn(-60);
			pause(1.0);
			drive(-144);
			pause(6.0);
			turn(45);
			pause(2.0);
			drive(-216);
			pause(6.0);
			break;
		}
		
	}

	private void scurry4() {
		switch(currentAlliance) {
		case Red:
			drive(40);
			pause(2.0);
			turn(60);
			pause(1.0);
			drive(-144);
			pause(6.0);
			turn(-45);
			pause(2.0);
			drive(-216);
			pause(6.0);
			break;
		case Blue:
			drive(40);
			pause(2.0);
			turn(60);
			pause(1.0);
			drive(-288);
			pause(10.0);
			break;
		}
	}

	/**
	 * Drops the gear off at the lift.
	 */
	private void dropGear() {
		robotOut.extendGearHolder(true);
		pause(.0625, false);
		drive(14);
		pause(2.0);
	}

	/**
	 * Executes an autonomous turn which goes the requested distance, which is
	 * in Inches
	 * 
	 * @param distance
	 *            - the distance which the robot should drive to. Positive
	 *            indicates forwards, Negative indicates backwards
	 */
	private void drive(double distance) {
		feedback.resetDB_encoders();
		driveBase.setType(DriveType.AUTODRIVE);
		autoInput.setDB_driveSetpoint(distance);
	}

	/**
	 * Executes an autonomous turn with the passed in angle.
	 * 
	 * @param angle
	 *            - the angle to turn through. Robot Right is positive, Robot
	 *            Left is negative
	 */
	private void turn(double angle) {
		driveBase.setType(DriveType.AUTOTURN);
		feedback.resetDB_gyro();
		autoInput.setDB_turnSetpoint(angle);
	}

	/**
	 * This method sets a flag to let the auto mode know that the current step
	 * has been completed.
	 */
	public void setActionDone() {
		isActionDone = true;
	}

	/**
	 * This method breaks down the passed in automode number into steps.
	 */
	private void analyzeAutoMode() {
		autoComponents = new ArrayList<>();
		do {
			int modOut = autoMode % 10;
			autoComponents.add(modOut);
			autoMode /= 10;
		} while (autoMode != 0);
	}

	/**
	 * Put all necessary values to SmartDashboard;
	 */
	public void smartDashboard() {
		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", 0);
		SmartDashboard.putNumber("AUTOMODE", autoMode);
	}

	public static Auto getInstance() {
		return instance == null ? instance = new Auto() : instance;
	}
}
