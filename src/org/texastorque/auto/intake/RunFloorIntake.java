package org.texastorque.auto.intake;

import org.texastorque.auto.AutonomousCommand;

public class FloorIntakeSet extends AutonomousCommand {

	private IntakeState state;
	
	public enum IntakeState {
		INTAKE, OUTAKE, IDLE;
	}
	
	public FloorIntakeSet(IntakeState state) {
		this.state = state;
	}
	
	@Override
	public void run() {
		switch(state) {
			case INTAKE:
				output.setIntakeSpeed(1, 1);
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
