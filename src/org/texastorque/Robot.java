package org.texastorque;

import java.util.ArrayList;

import org.texastorque.io.HumanInput;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.Bin;
import org.texastorque.subsystem.Climber;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.FlyWheel;
import org.texastorque.subsystem.Gear;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Subsystem;
import org.texastorque.subsystem.Twinsters;
import org.texastorque.torquelib.base.TorqueIterative;

public class Robot extends TorqueIterative {

	private ArrayList<Subsystem> subsystems;
	
	@SuppressWarnings("serial")
	@Override
	public void robotInit() {
		Input.getInstance();
		HumanInput.getInstance();
		RobotOutput.getInstance();
		subsystems = new ArrayList<Subsystem>(){{
			add(Climber.getInstance());
			add(DriveBase.getInstance());
			add(FlyWheel.getInstance());
			add(Intake.getInstance());
			add(Twinsters.getInstance());
			add(Bin.getInstance());
			add(Gear.getInstance());
		}};
	}

	@Override
	public void autonomousInit() {
		for(Subsystem system : subsystems ) {
			system.autoInit();
			system.setInput(HumanInput.getInstance());
		}
	}

	@Override
	public void teleopInit() {
		for(Subsystem system : subsystems ) {
			system.teleopInit();
			system.setInput(HumanInput.getInstance());
		}
	}

	@Override
	public void autonomousContinuous() {
		for(Subsystem system : subsystems ) {
			system.autoContinuous();
		}
	}

	@Override
	public void teleopContinuous() {
		for(Subsystem system : subsystems ) {
			system.teleopContinuous();
		}
	}

	@Override
	public void alwaysContinuous() {
		for(Subsystem system : subsystems) {
			system.smartDashboard();
		}
	}

}
