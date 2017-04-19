package org.texastorque.auto;

import java.util.ArrayList;
import java.util.LinkedList;

import org.texastorque.auto.drive.AirShipCenter;
import org.texastorque.auto.drive.AirShipSide;
import org.texastorque.auto.shooter.DriveToHopper;
import org.texastorque.auto.shooter.VisionTest;
import org.texastorque.auto.util.Side;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.Climber;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.FlyWheel;
import org.texastorque.subsystem.Gear;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Subsystem;
import org.texastorque.subsystem.Twinsters;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static LinkedList<AutonomousCommand> commandList;
	private static volatile boolean isFinished = false;
	private static double aggregateTime;
	private static ArrayList<Subsystem> subsystems;
	
	private static boolean isDone = false;
	
	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
		subsystems = new ArrayList<Subsystem>(){{
			add(Climber.getInstance());
			add(DriveBase.getInstance());
			add(FlyWheel.getInstance());
			add(Intake.getInstance());
			add(Twinsters.getInstance());
			add(Gear.getInstance());
		}};
	}

	public static void beginAuto() {
		isFinished = false;
		isDone = false;
		analyzeAutoMode();
	}

	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);
		while (DriverStation.getInstance().isAutonomous() && !isFinished && Timer.getFPGATimestamp() - startTime < time) {
			Feedback.getInstance().update();
			HumanInput.getInstance().smartDashboard();
			Feedback.getInstance().smartDashboard();
			AutoManager.SmartDashboard();
			for(Subsystem system : subsystems) {
				system.autoContinuous();
				system.smartDashboard();
			}
		}
		isFinished = false;
		aggregateTime = Timer.getFPGATimestamp() - startTime;
	}
	
	public static void pauseTeleop(double time) {
		double startTime = Timer.getFPGATimestamp();
		time = Math.abs(time);
		while (DriverStation.getInstance().isOperatorControl() && !isFinished && Timer.getFPGATimestamp() - startTime < time) {
			Feedback.getInstance().update();
			HumanInput.getInstance().smartDashboard();
			Feedback.getInstance().smartDashboard();
			AutoManager.SmartDashboard();
			for(Subsystem system : subsystems) {
				system.autoContinuous();
				system.smartDashboard();
			}
		}
		isFinished = false;
		aggregateTime = Timer.getFPGATimestamp() - startTime;
	}

	public static void interruptThread() {
		isFinished = true;
	}

	private static void analyzeAutoMode() {
		int autoMode = invertSeries((int) (SmartDashboard.getNumber("AUTOMODE", 0)));

		LinkedList<Integer> prevCommandNum = new LinkedList<>();
		commandList = new LinkedList<>();
		RobotOutput.getInstance().setGearCollectorSpeed(0);
		while (autoMode > 0) {
			switch (autoMode % 10) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				commandList.addAll(new AirShipSide(true, true, Side.LEFT).getCommands());
				break;
			case 3:
				commandList.addAll(new AirShipCenter(true).getCommands());
				break;
			case 4:
				commandList.addAll(new AirShipSide(true, true, Side.RIGHT).getCommands());
				break;
			case 5:
				commandList.addAll(new DriveToHopper().getCommands());
				break;
			case 6:
				break;
			case 7:
				commandList.addAll(new VisionTest().getCommands());
				break;
			}
			prevCommandNum.addLast(autoMode % 10);
			autoMode /= 10;
		}
		while(DriverStation.getInstance().isAutonomous() && !commandList.isEmpty()) {
			commandList.remove(0).run();
		}
		isDone = true;
		for(Subsystem system : subsystems) {
			system.disabledContinuous();
		}
	}

	public static boolean isDone() {
		return isDone;
	}
	
	private static int invertSeries(int number) {
		int tempNumber = number;
		int place = 1;
		while (tempNumber > 0) {
			place *= 10;
			tempNumber /= 10;
		}
		place /= 10;
		tempNumber = 0;
		while (number > 0) {
			tempNumber += (number % 10) * place;
			place /= 10;
			place = (place == 0 ? place++ : place);
			number /= 10;
		}
		return tempNumber;
	}

	public static void SmartDashboard() {
		SmartDashboard.putNumber("A_AGGREGATETIME", aggregateTime);
	}
}