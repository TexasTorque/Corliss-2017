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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	private double time;
	
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
	}

	@Override
	public void disabledInit() {
		RobotOutput.getInstance().setLight(true);
	}
	
	@Override
	public void disabledContinuous() {
		AutoManager.getInstance().stop();
	}
	
	@Override
	public void autonomousInit() {
		time = 0;
		RobotOutput.getInstance().setLight(false);
		for(Subsystem system : subsystems ) {
			system.autoInit();
			system.setInput(Input.getInstance());
		}
		AutoManager.getInstance().init();
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
		Feedback.getInstance().update();
		for(Subsystem system : subsystems ) {
			system.autoContinuous();
		}
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
		AutoManager.getInstance().smartDashboard();
		Lights.getInstance().update();
	}
	
}
