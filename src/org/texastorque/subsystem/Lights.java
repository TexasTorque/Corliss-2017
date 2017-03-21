package org.texastorque.subsystem;

import org.texastorque.constants.Ports;

import edu.wpi.first.wpilibj.AnalogOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights {

	private static Lights instance;
	
	public enum State {
		DISABLED_RED(.625), DISABLED_BLUE(1.350), TELEOP_RED(1.875), TELEOP_BLUE(2.5), CLIMB_RED(3.125), CLIMB_BLUE(3.75), SHOOT_NO(4.375), SHOOT_YES(5); 

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

	public void set(boolean climbing, double value, double setpoint) {
		if(!climbing) {
			if (value > setpoint && setpoint != 0.0) {
				state = State.SHOOT_YES;
			} else {
				if (ds.getAlliance() == Alliance.Red) {
					state = State.TELEOP_RED;
				} else if (ds.getAlliance() == Alliance.Blue) {
					state = State.TELEOP_BLUE;
				} else {
					state = State.DISABLED_BLUE;
				}
			}
		} else {
			if(ds.getAlliance() == Alliance.Red) {
				state = State.CLIMB_RED;
			} else if(ds.getAlliance() == Alliance.Blue) {
				state = State.CLIMB_BLUE;
			} else {
				state = State.DISABLED_BLUE;
			}
		}
	}

	public void off() {
		switch(DriverStation.getInstance().getAlliance()) {
		case Red:
			state = State.DISABLED_RED;
			break;
		case Blue:
			state = State.DISABLED_BLUE;
			break;
		default:
			state = State.DISABLED_RED;
			break;
		}
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