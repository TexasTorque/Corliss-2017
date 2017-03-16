package org.texastorque.subsystem;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights {

	private static Lights instance;

	public enum State {
		DISABLED(0), RED_IDLE(0.833), BLUE_IDLE(1.667), RAMPING_UP(2.5), READY(3.33), PARTY(4.167), PANIC(5);

		public final double value;

		State(double _value) {
			value = _value;
		}
	}

	private AnalogOutput arduino;
	private DriverStation ds;
	private State state;

	public Lights(double x) {
		arduino = new AnalogOutput(Ports.LI_ARDUINO);
		ds = DriverStation.getInstance();
		off();
	}

	public void set(double value, double setpoint) {
		if (value > setpoint && setpoint != 0.0) {
			state = State.READY;
		} else {
			if (ds.getAlliance() == Alliance.Red) {
				state = State.RED_IDLE;
			} else if (ds.getAlliance() == Alliance.Blue) {
				state = State.BLUE_IDLE;
			} else {
				state = State.DISABLED;
			}
		}
	}

	public void off() {
		state = State.DISABLED;
	}

	public void party() {
		state = State.PARTY;
	}

	boolean first = true;

	public void update() {
		arduino.setVoltage(state.value);
		SmartDashboard.putString("LightState", state.toString());
		SmartDashboard.putNumber("LightWrite", state.value);
	}

	// singleton
	public static Lights getInstance() {
		return instance == null ? instance = new Lights(0.0) : instance;
	}
}