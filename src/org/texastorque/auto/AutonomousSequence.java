package org.texastorque.auto;

import java.util.LinkedList;

public abstract class AutonomousSequence {

	protected LinkedList<AutonomousCommand> commandList = new LinkedList<>();
	
	public abstract void init();
	
	public LinkedList<AutonomousCommand> getCommands() {
		return commandList;
	}
}
