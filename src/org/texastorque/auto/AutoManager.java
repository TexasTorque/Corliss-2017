package org.texastorque.auto;

import java.util.Collection;
import java.util.LinkedList;

import org.texastorque.auto.drive.AirShipCenter;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static LinkedList<AutonomousCommand> commandList;
	private static volatile boolean isFinished = false;
	
	public static void init() {
		SmartDashboard.putNumber("AUTOMODE", 0);
	}

	public static void beginAuto() {
		analyzeAutoMode();
	}

	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		while (isFinished || Timer.getFPGATimestamp() - startTime < time)
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		isFinished = false;
		System.out.println("FINISHED, TIME LEFT: " + (Timer.getFPGATimestamp() - startTime));
	}

	public static void interruptThread() {
		isFinished = true;
	}

	private static void analyzeAutoMode() {
		int autoMode = invertSeries((int) (SmartDashboard.getNumber("AUTOMODE", 0)));

		LinkedList<Integer> prevCommandNum = new LinkedList<>();
		commandList = new LinkedList<>();

		while (autoMode > 0) {
			switch (autoMode % 10) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				
				break;
			case 3:
				commandList.addAll(new AirShipCenter(true).getCommands());
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			}
			prevCommandNum.addLast(autoMode % 10);
			;
			autoMode /= 10;
		}
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

}