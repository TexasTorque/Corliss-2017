package org.texastorque.auto.intake;

import org.texastorque.auto.AutonomousCommand;

public class RunFloorIntake extends AutonomousCommand {

	private IntakeState state;
	
	public enum IntakeState {
		INTAKE, OUTAKE, IDLE, INTAKEHOPPER;
	}
	
	public RunFloorIntake(IntakeState state) {
		this.state = state;
	}
	
	@Override
	public void run() {
		switch(state) {
			case INTAKE:
				output.setIntakeSpeed(1, -1);
				break;
			case INTAKEHOPPER:
				output.setIntakeSpeed(1, .3);
				break;
			case OUTAKE:
				output.setIntakeSpeed(-1, -1);
				break;
			case IDLE:
				output.setIntakeSpeed(0, 0);
				break;
		}
	}
	
	@Override
	public void reset() {
	}
}
