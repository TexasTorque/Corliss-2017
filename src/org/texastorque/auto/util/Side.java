package org.texastorque.auto.util;

import edu.wpi.first.wpilibj.DriverStation.Alliance;

public enum Side {
	LEFT, RIGHT;
	
	public static Alliance manageAlliance(Alliance alliance, Side side) {
		switch(side) {
			case RIGHT:
				switch(alliance) {
				case Red:
					return Alliance.Blue;
				case Blue:
					return Alliance.Red;
				default:
					return alliance;
				}
			default:
				return alliance;
		}
	}
}
