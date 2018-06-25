package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.AutoManager;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.Climber;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.FlyWheel;
import org.texastorque.subsystem.Gear;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Lights;
import org.texastorque.subsystem.Subsystem;
import org.texastorque.subsystem.Twinsters;
import org.texastorque.torquelib.base.TorqueIterative;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	private double time;
	private boolean hasAlreadyStarted = false;
	
	@SuppressWarnings("serial")
	@Override
	public void robotInit() {
		Input.getInstance();
		HumanInput.getInstance();
		RobotOutput.getInstance();
		Feedback.getInstance();
		subsystems = new ArrayList<Subsystem>(){{
			add(Climber.getInstance());
			add(DriveBase.getInstance());
			add(FlyWheel.getInstance());
			add(Intake.getInstance());
			add(Twinsters.getInstance());
			add(Gear.getInstance());
		}};
		RobotOutput.getInstance().setLight(true);
		Lights.getInstance();
		AutoManager.init();
//		String[] cameraList = { "10.14.77.30", "10.14.77.29", "10.14.77.28", "10.14.77.27", "10.14.77.26",
//				"10.14.77.25", "10.14.77.24", "10.14.77.23", "10.14.77.22", "10.14.77.21", "10.14.77.20", "10.14.77.19",
//				"10.14.77.18", "10.14.77.17", "10.14.77.16", "10.14.77.15", "10.14.77.14", "10.14.77.13", "10.14.77.12",
//				"10.14.77.11", "10.14.77.10", "10.14.77.9", "10.14.77.8", "10.14.77.7", "10.14.77.6", "10.14.77.5",
//				"10.14.77.4", "10.14.77.3", "10.14.77.2", "10.14.77.1" };
		CameraServer.getInstance().addAxisCamera("10.14.77.17");
	}

	@Override
	public void disabledInit() {
		for(Subsystem system : subsystems ) {
			system.disabledInit();
			system.setInput(HumanInput.getInstance());
		}
	}
	
	@Override
	public void disabledContinuous() {
		hasAlreadyStarted = false;
		for(Subsystem system : subsystems ) {
			system.disabledContinuous();
		}
	}
	
	@Override
	public void autonomousInit() {
		/*
		// Disabled for demo safety
		time = 0;
		RobotOutput.getInstance().setLight(false);
		for(Subsystem system : subsystems ) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		AutoManager.beginAuto();
		hasAlreadyStarted = true;
		//*/
	}

	@Override
	public void teleopInit() {
		time = 0;
		RobotOutput.getInstance().setLight(false);
		for(Subsystem system : subsystems ) {
			system.teleopInit();
			system.setInput(HumanInput.getInstance());
		}
	}

	@Override
	public void autonomousContinuous() {
		/*
		// Disabled for demo safety
		if(!hasAlreadyStarted && AutoManager.isDone()) {
			AutoManager.beginAuto();
			hasAlreadyStarted = true;
		}
		Feedback.getInstance().update();
		for(Subsystem system : subsystems ) {
			system.autoContinuous();
		}
		//*/
	}

	@Override
	public void teleopContinuous() {
		Feedback.getInstance().update();
		HumanInput.getInstance().update();
		for(Subsystem system : subsystems ) {
			system.teleopContinuous();
		}
	}

	@Override
	public void alwaysContinuous() {
		Feedback.getInstance().update();
		for(Subsystem system : subsystems) {
			system.smartDashboard();
		}
		if(!isDisabled())
			SmartDashboard.putNumber("Time", time++);
		HumanInput.getInstance().smartDashboard();
		Feedback.getInstance().smartDashboard();
		AutoManager.SmartDashboard();
		Lights.getInstance().update();
	}
	
}
