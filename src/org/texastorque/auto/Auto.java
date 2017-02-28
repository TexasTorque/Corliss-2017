package org.texastorque.auto;

import java.util.ArrayList;

import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.DriveBase.DriveType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Auto {

	/*
	 * mode 0 - null mode, do nothing mode 1 - gear a left to left * mode 2 -
	 * gear b center to center mode 3 - gear c center-right (next to key) to
	 * right mode 14 - move to hopper (from far) mode 24 - move to hopper (from
	 * center) mode 34 - move to hopper (from near)
	 */

	private static Auto instance;

	private static ArrayList<Integer> autoComponents;
	private static int autoMode;
	
	private Thread thread;

	public Auto() {
	}

	public void init() {
		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", 0);
		analyzeAutoMode();
		thread = new Thread(()-> run());
		thread.start();
	}
	
	private void run() {
		beginAuto();
	}

	protected final void pause(double seconds) {
		double start = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - start < seconds) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		}
		System.out.println("DONE SLEEPING");
	}
	
	private synchronized void beginAuto() {
		while (autoComponents.size() != 0) {
			int currentStep = autoComponents.remove(autoComponents.size()-1);
			switch (currentStep) {
			case 1:
				break;
			case 2:
				drive(-100);
				pause(3.0);
				RobotOutput.getInstance().extendGearHolder(true);
				pause(1.0);
				RobotOutput.getInstance().extendGearHolder(false);
				turn(30);
				break;
			case 3:
				break;
			case 4:
				break;
			default:
				System.out.println("INVALID STEP OR NULL. . ." + currentStep);
				break;
			}
		}
	}
	
	private void drive(double distance) {
		DriveBase.getInstance().setType(DriveType.AUTODRIVE);
		Input.getInstance().setDB_driveSetpoint(distance);
	}
	
	private void turn(double angle) {
		DriveBase.getInstance().setType(DriveType.AUTOTURN);
		Input.getInstance().setDB_turnSetpoint(angle);
	}
	
	private void analyzeAutoMode() {
		autoComponents = new ArrayList<>();
		do {
			int modOut = autoMode % 10;
			autoComponents.add(modOut);
			autoMode /= 10;
		} while (autoMode != 0);
	}

	public void smartDashboard() {
		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", 0);
		SmartDashboard.putNumber("AUTOMODE", autoMode);
	}
	
	public static Auto getInstance() {
		return instance == null ? instance = new Auto() : instance;
	}

}
