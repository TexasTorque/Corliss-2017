package org.texastorque.auto;

import java.util.LinkedList;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoManager {

	private static Thread thread;
	private static LinkedList<AutonomousCommand> commandList;

	public static void init() {
		try {
			thread.join(10);
		} catch (Exception e) {
		}
		;
		SmartDashboard.putNumber("AUTOMODE", 0);
	}

	public static void beginAuto() {
		thread = new Thread(() -> run());
		thread.start();
	}

	private static void run() {
		analyzeAutoMode();
	}

	public static void pause(double time) {
		double startTime = Timer.getFPGATimestamp();
		while (Timer.getFPGATimestamp() - startTime < time)
			try {
				Thread.sleep(5);
			} catch (Exception e) {
			}
		System.out.println("FINISHED, TIME LEFT: " + (Timer.getFPGATimestamp() - startTime));
	}

	public static void interruptThread() {
		thread.interrupt();
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