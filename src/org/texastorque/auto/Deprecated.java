//package org.texastorque.auto;
//
//import java.util.ArrayList;
//import java.util.LinkedList;
//
//import org.texastorque.feedback.Feedback;
//import org.texastorque.io.Input;
//import org.texastorque.io.RobotOutput;
//import org.texastorque.subsystem.DriveBase;
//import org.texastorque.subsystem.DriveBase.DriveType;
//
//import edu.wpi.first.wpilibj.DriverStation;
//import edu.wpi.first.wpilibj.Timer;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//
//public class Deprecated {
//
//	/*
//	 * mode 0 - null mode, do nothing mode 1 - gear a left to left * mode 2 -
//	 * gear b center to center mode 3 - gear c center-right (next to key) to
//	 * right mode 14 - move to hopper (from far) mode 24 - move to hopper (from
//	 * center) mode 34 - move to hopper (from near)
//	 */
//
//	private static AutoManager instance;
//
//	private static LinkedList<AutonomousCommand> commandList = new LinkedList<>();
//	
//	private static ArrayList<Integer> autoComponents;
//	private static int autoMode;
//	private boolean doGearCenter = true;
//	private boolean doGearSide = true;
//	private boolean cornerAuto = true;
//	private boolean isActionDone = false;
//	private static ArrayList<Integer> previousModes = new ArrayList<>();
//
//	private Thread thread;
//
//	public AutoManager() {
//		// init();
//	}
//
//	public void init() {
//		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", -1);
//		analyzeAutoMode();
//		thread = new Thread(() -> run());
//		thread.start();
//	}
//
//	private void run() {
//		beginAuto();
//	}
//
//	public final void stop() {
//		try {
//			thread.join(500);
//			thread.interrupt();
//		} catch (Exception e) {
//			if (e instanceof InterruptedException)
//				thread.interrupt();
//		}
//	}
//
//	/**
//	 * This method breaks down the passed in automode number into steps.
//	 */
//	private void analyzeAutoMode() {
//		autoComponents = new ArrayList<>();
//		do {
//			int modOut = autoMode % 10;
//			autoComponents.add(modOut);
//			autoMode /= 10;
//		} while (autoMode != 0);
//	}
//
//	protected final void pause(double seconds) {
//		pause(seconds, true);
//	}
//
//	protected final void pause(double seconds, boolean allowInterupts) {
//		double start = Timer.getFPGATimestamp();
//		while (Timer.getFPGATimestamp() - start < seconds) {
//			if (allowInterupts && isActionDone) {
//				isActionDone = false;
//				break;
//			}
//			try {
//				Thread.sleep(1);
//			} catch (Exception e) {
//				if (e instanceof InterruptedException) {
//					thread.interrupt();
//				}
//			}
//		}
//		System.out.println("DONE SLEEPING");
//	}
//
//	/**
//	 * This method is called to initialize the autonomous process.
//	 */
//	private synchronized void beginAuto() {
//		RobotOutput.getInstance().upShift(false);
//		RobotOutput.getInstance().extendGearHolder(false);
//		Feedback.getInstance().resetDB_gyro();
//		Feedback.getInstance().resetDB_encoders();
//		while (!thread.isInterrupted() && autoComponents.size() != 0) {
//			int currentStep = autoComponents.remove(autoComponents.size() - 1);
//			switch (currentStep) {
//			// No Sensors
//			case 1:
//				DriveBase.getInstance().setType(DriveType.AUTOOVERRIDE);
//				RobotOutput.getInstance().setDriveBaseSpeed(-.5, -.5);
//				pause(3.0);
//				RobotOutput.getInstance().setDriveBaseSpeed(0d, 0d);
//				break;
//			// Left Gear
//			case 2:
//				if (cornerAuto)
//					cornerAuto2();
//				else
//					auto2();
//				if (doGearSide) {
//					dropGear();
//				}
//				break;
//			// Center Gear
//			case 3:
//				drive(-76);
//				pause(4.0);
//				if (doGearCenter)
//					dropGear();
//				break;
//			// Right Gear
//			case 4:
//				if (cornerAuto)
//					cornerAuto4();
//				else
//					auto4();
//				if (doGearSide)
//					dropGear();
//				break;
//			case 5:
//				switch (previousModes.get(previousModes.size() - 1)) {
//				case 2:
//					driveToHopper();
//					break;
//				case 4:
//					break;
//				}
//				break;
//			case 6:
//				autoShoot();
//				break;
//			case 7:
//				switch (previousModes.get(previousModes.size() - 1)) {
//				case 4:
//					scurry4();
//					break;
//				case 2:
//					scurry2();
//					break;
//				}
//				break;
//			case 8:
//				RobotOutput.getInstance().setHoodSpeed(true);
//				Input.getInstance().setFW_rightSetpoint(3050);
//				Input.getInstance().setFW_leftSetpoint(3200);
//				pause(1.0, false);
//				Input.getInstance().setFW_rightSetpoint(3050);
//				Input.getInstance().setFW_leftSetpoint(3200);
//				pause(1.0, false);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				Input.getInstance().setFW_gateSpeed(.5);
//				pause(3.0, false);
//				pause(1.0, true);
//				Input.getInstance().setFW_rightSetpoint(0);
//				Input.getInstance().setFW_leftSetpoint(0);
//				RobotOutput.getInstance().setTwinstersSpeed(0, 0);
//				RobotOutput.getInstance().setIntakeSpeed(0, 0);
//				Input.getInstance().setFW_gateSpeed(0);
//				switch (DriverStation.getInstance().getAlliance()) {
//				case Blue:
//					drive(-24);
//					pause(2.0);
//					turn(-45);
//					pause(5.0);
//					drive(-60);
//					pause(3.0);
//					turn(60);
//					pause(2.0);
//					drive(-63);
//					pause(3.0);
//					dropGear();
//					break;
//				case Red:
//					drive(-24);
//					pause(2.0);
//					turn(45);
//					pause(5.0);
//					drive(-60);
//					pause(3.0);
//					turn(-60);
//					pause(2.0);
//					drive(-63);
//					pause(3.0);
//					dropGear();
//					break;
//				}
//			break;
//			default:
//				RobotOutput.getInstance().setHoodSpeed(true);
//				System.out.println("INVALID STEP OR NULL. . ." + currentStep);
//				break;
//			}
//			previousModes.add(currentStep);
//		}
//		while (thread.isAlive()) {
//			stop();
//		}
//	}
//
//	public void driveToHopper() {
//		drive(76);
//		pause(3.0);
//		turn(30);
//		pause(2.0);
//		drive(35);
//		pause(3.0);
//	}
//
//	public void autoShoot() {
//		if (previousModes.size() == 0) {
//			System.out.println("METHOD6");
//			switch (DriverStation.getInstance().getAlliance()) {
//			case Blue:
//				RobotOutput.getInstance().setHoodSpeed(true);
//				drive(-71);
//				pause(4.0);
//				turn(90);
//				pause(2.0);
//				drive(34);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				pause(2.0);
//				pause(2.0, false);
//				pause(1.0, true);
//				RobotOutput.getInstance().setTwinstersSpeed(0, 0);
//				RobotOutput.getInstance().setIntakeSpeed(0, 0);
//				drive(-36);
//				pause(2.0);
//				turn(-90);
//				pause(.5);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				drive(48);
//				pause(3.0);
//				Input.getInstance().setFW_leftSetpoint(3250);
//				Input.getInstance().setFW_rightSetpoint(3100);
//				turn(50);
//				pause(2.0);
//				drive(15);
//				pause(2.0);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				Input.getInstance().setFW_gateSpeed(.3);
//				break;
//			case Red:
//				RobotOutput.getInstance().setHoodSpeed(true);
//				drive(-74);
//				pause(4.0);
//				turn(-90);
//				pause(2.0);
//				drive(34);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				pause(2.0);
//				RobotOutput.getInstance().setTwinstersSpeed(0, 0);
//				RobotOutput.getInstance().setIntakeSpeed(0, 0);
//				Input.getInstance().setFW_leftSetpoint(3000);
//				Input.getInstance().setFW_rightSetpoint(2850);
//				pause(2.0, false);
//				drive(-20);
//				pause(1.5);
//				turn(90);
//				pause(1.0);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				drive(40);
//				pause(3.0);
//				turn(-45);
//				pause(1.0);
//				drive(16);
//				pause(1.0);
//				RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//				RobotOutput.getInstance().setIntakeSpeed(1, .3);
//				Input.getInstance().setFW_gateSpeed(.6);
//				break;
//			// drive(-74);
//			// pause(4.0);
//			// turn(-90);
//			// pause(2.0);
//			// drive(34);
//			// pause(2.0);
//			// Input.getInstance().setFW_leftSetpoint(3400);
//			// Input.getInstance().setFW_rightSetpoint(3150);
//			// pause(1.0, false);
//			// RobotOutput.getInstance().setTwinstersSpeed(1, 1, .3);
//			// RobotOutput.getInstance().setIntakeSpeed(1);
//			// pause(2.0, false);
//			// drive(-10);
//			// pause(.5);
//			// turn(72);
//			// pause(3.0);
//			// RobotOutput.getInstance().setTwinstersSpeed(1, 1, .3);
//			// RobotOutput.getInstance().setIntakeSpeed(1);
//			// Input.getInstance().setFW_gateSpeed(.5);
//			// break;
//			}
//		} else {
//			switch (previousModes.get(previousModes.size() - 1)) {
//			case 5:
//				switch (previousModes.get(previousModes.size() - 2)) {
//				case 2:
//					Input.getInstance().setFW_leftSetpoint(4200);
//					Input.getInstance().setFW_rightSetpoint(4050);
//					pause(3.0, false);
//					drive(-8);
//					pause(.5);
//					turn(-90);
//					pause(.75, false);
//					RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//					RobotOutput.getInstance().setIntakeSpeed(1, 1);
//					Input.getInstance().setFW_gateSpeed(.4);
//					break;
//				}
//				break;
//			case 2:
//				switch (DriverStation.getInstance().getAlliance()) {
//				case Blue:
//					pause(.5, false);
//					drive(20);
//					pause(1.5);
//					turn(-15);
//					pause(.5);
//					drive(96);
//					Input.getInstance().setFW_leftSetpoint(3000);
//					Input.getInstance().setFW_rightSetpoint(2850);
//					pause(3.0);
//					RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//					RobotOutput.getInstance().setIntakeSpeed(1, 1);
//					Input.getInstance().setFW_gateSpeed(.4);
//					break;
//				}
//				break;
//			case 4:
//				switch (DriverStation.getInstance().getAlliance()) {
//				case Red:
//					pause(.5, false);
//					drive(20);
//					pause(1.5);
//					turn(15);
//					pause(.5);
//					drive(96);
//					Input.getInstance().setFW_leftSetpoint(3000);
//					Input.getInstance().setFW_rightSetpoint(2850);
//					pause(3.0);
//					RobotOutput.getInstance().setTwinstersSpeed(1, 1);
//					RobotOutput.getInstance().setIntakeSpeed(1, 1);
//					Input.getInstance().setFW_gateSpeed(.4);
//					break;
//				}
//				break;
//			}
//		}
//	}
//
//	public void visionAlign() {
//		System.out.println("RUNNING VISION AUTO...");
//		do {
//			SmartDashboard.putString("GOODPACKET", "FALSE");
//		} while (!Feedback.getInstance().getPX_goodPacket());
//		SmartDashboard.putString("GOODPACKET", "TRUE");
//		turn(Feedback.getInstance().getPX_HorizontalDegreeOff());
//		pause(2.0);
//	}
//
//	public void ultrasonicDrive() {
//		DriveBase.getInstance().setType(DriveType.AUTOIRDRIVE);
//		pause(5.0, false);
//	}
//
//	private void cornerAuto2() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(-83);
//			pause(3.0);
//			turn(87);
//			pause(3.0);
//			drive(-69);
//			pause(3.0);
//			break;
//		case Blue:
//			drive(-61);
//			pause(5.0);
//			turn(60);
//			pause(1);
//			drive(-78);
//			pause(5.0);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void cornerAuto4() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(-61);
//			pause(5.0);
//			turn(-60);
//			pause(1);
//			drive(-78);
//			pause(5.0);
//			break;
//		case Blue:
//			drive(-83);
//			pause(3.0);
//			turn(-87);
//			pause(3.0);
//			drive(-69);
//			pause(3.0);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void auto2() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(-76);
//			pause(3.0);
//			turn(87);
//			pause(3.0);
//			drive(-66);
//			pause(3.0);
//			break;
//		case Blue:
//			drive(-88);
//			pause(4.0);
//			turn(111.25);
//			pause(3.0);
//			drive(-85);
//			pause(3.0);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void auto4() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(-88);
//			pause(4.0);
//			turn(-111);
//			pause(3.0);
//			drive(-85);
//			pause(3.0);
//			break;
//		case Blue:
//			drive(-76);
//			pause(3.0);
//			turn(-87);
//			pause(3.0);
//			drive(-64);
//			pause(3.0);
//			break;
//		default:
//			break;
//		}
//	}
//
//	private void scurry2() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(40);
//			pause(2.0);
//			turn(-60);
//			pause(1.0);
//			drive(-288);
//			pause(10.0);
//			break;
//		case Blue:
//			drive(40);
//			pause(2.0);
//			turn(-60);
//			pause(1.0);
//			drive(-144);
//			pause(6.0);
//			turn(45);
//			pause(2.0);
//			drive(-216);
//			pause(6.0);
//			break;
//		}
//
//	}
//
//	private void scurry4() {
//		switch (DriverStation.getInstance().getAlliance()) {
//		case Red:
//			drive(40);
//			pause(2.0);
//			turn(60);
//			pause(1.0);
//			drive(-144);
//			pause(6.0);
//			turn(-45);
//			pause(2.0);
//			drive(-216);
//			pause(6.0);
//			break;
//		case Blue:
//			drive(40);
//			pause(2.0);
//			turn(60);
//			pause(1.0);
//			drive(-288);
//			pause(10.0);
//			break;
//		}
//	}
//
//	/**
//	 * Drops the gear off at the lift.
//	 */
//	private void dropGear() {
//		RobotOutput.getInstance().extendGearHolder(true);
//		pause(.0625, false);
//		drive(14);
//		pause(2.0);
//	}
//
//	public static void addCommand(AutonomousCommand command) {
//		commandList.addLast(command);
//	}
//	
//	/**
//	 * This method sets a flag to let the auto mode know that the current step
//	 * has been completed.
//	 */
//	public void setActionDone() {
//		isActionDone = true;
//	}
//
//	public synchronized Thread getThread() {
//		return thread;
//	}
//
//	/**
//	 * Put all necessary values to SmartDashboard;
//	 */
//	public void smartDashboard() {
//		autoMode = (int) SmartDashboard.getNumber("AUTOMODE", 0);
//		SmartDashboard.putNumber("AUTOMODE", autoMode);
//	}
//
//	public static AutoManager getInstance() {
//		return instance == null ? instance = new AutoManager() : instance;
//	}
//
//}
