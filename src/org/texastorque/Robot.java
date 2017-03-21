package org.texastorque;

import java.util.ArrayList;

import org.texastorque.auto.Auto;
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

public class Robot extends TorqueIterative {
	
	/* IO, Feedback */
	
	private final Input autoInput = Input.getInstance();
	private final HumanInput humanInput = HumanInput.getInstance();
	private final RobotOutput robotOut = RobotOutput.getInstance();
	private final Feedback feedback = Feedback.getInstance();
	private final Lights lights = Lights.getInstance();
	
	
	/* Subsystems */

	private final ArrayList<Subsystem> subsystems = new ArrayList<Subsystem>();
	private final Climber climber = Climber.getInstance();
	private final DriveBase driveBase = DriveBase.getInstance();
	private final FlyWheel flywheel = FlyWheel.getInstance();
	private final Intake intake = Intake.getInstance();
	private final Twinsters twinsters = Twinsters.getInstance();
	private final Gear gearMechanism = Gear.getInstance();
	
	
	/* Autonomous */
	
	private final Auto autonomous = Auto.getInstance();

	
	/* Initialization */
	
	@Override
	public void robotInit() {
		subsystems.add(driveBase);
		subsystems.add(intake);
		subsystems.add(twinsters);
		subsystems.add(gearMechanism);
		subsystems.add(flywheel);
		subsystems.add(climber);
		
		robotOut.setLight(true);
	}

	@Override
	public void disabledInit() {
		robotOut.setLight(true);
	}
	
	@Override
	public void autonomousInit() {
		robotOut.setLight(false);

		subsystems.stream().forEach(system -> {
			system.autoInit();
			system.setInput(autoInput);
		});
		
		autonomous.init();
	}

	@Override
	public void teleopInit() {
		robotOut.setLight(false);

		subsystems.stream().forEach(system -> {
			system.teleopInit();
			system.setInput(humanInput);
		});
	}
	
	
	/* Continuous */

	@Override
	public void autonomousContinuous() {
		feedback.update();
		
		subsystems.stream().forEach(system -> system.autoContinuous());
	}

	@Override
	public void teleopContinuous() {
		feedback.update();
		humanInput.update();
		
		subsystems.stream().forEach(system -> system.teleopContinuous());
	}

	@Override
	public void alwaysContinuous() {
		feedback.update();
		lights.update();

		subsystems.stream().forEach(system -> system.smartDashboard());
		
		humanInput.smartDashboard();
		feedback.smartDashboard();
		autonomous.smartDashboard();
	}
}
