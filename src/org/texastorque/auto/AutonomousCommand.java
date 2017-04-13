package org.texastorque.auto;

import org.texastorque.feedback.Feedback;
import org.texastorque.io.Input;
import org.texastorque.io.RobotOutput;
import org.texastorque.subsystem.DriveBase;
import org.texastorque.subsystem.FlyWheel;
import org.texastorque.subsystem.Gear;
import org.texastorque.subsystem.Intake;
import org.texastorque.subsystem.Twinsters;

public abstract class AutonomousCommand {

	protected RobotOutput output;
	protected Input input;
	
	protected FlyWheel flyWheel;
	protected DriveBase driveBase;
	protected Gear gearSystem;
	protected Intake intake;
	protected Twinsters twinsters;
	protected Feedback feedback;
	
	public AutonomousCommand() {
		init();
	}
	
	public void init() {
		output = RobotOutput.getInstance();
		input = Input.getInstance();
		feedback = Feedback.getInstance();
		
		flyWheel = FlyWheel.getInstance();
		driveBase = DriveBase.getInstance();
		gearSystem = Gear.getInstance();
		intake = Intake.getInstance();
		twinsters = Twinsters.getInstance();
	}
	
	public abstract void run();
	
	public abstract void reset();
	
}
