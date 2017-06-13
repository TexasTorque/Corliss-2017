package org.texastorque.constants;

public class Ports {

	public static boolean isAlpha = false;

	// Motors
	// ==================

	// drivebase ports
	public static final int DB_LEFTFORE = isAlpha ? 3 : 18;
	public static final int DB_LEFTREAR = isAlpha ? 2 : 7;
	public static final int DB_RIGHTFORE = isAlpha ? 1 : 17; //17
	public static final int DB_RIGHTREAR = isAlpha ? 0 : 16; //16
	public static final int DB_SHIFT_A = 0;
	public static final int DB_SHIFT_B = 1;
	// shooter ports
	public static final int FW_LEFT = isAlpha ? 16 : 2;
	public static final int FW_RIGHT = isAlpha ? 17 : 3;
	public static final int FW_GATER = isAlpha ? 18 : 1;
	public static final int FW_GATEL = isAlpha ? 7 : 0;
	public static final int FW_HOOD = 6;
	public static final int FW_LIGHT = 0;
	// intake ports
	public static final int IN_LOWER = 8;
	public static final int IN_UPPER = 9;
	// twinsters ports
	public static final int TW_LEFT = 6;
	public static final int TW_RIGHT = 19;
	// climber ports
	public static final int CL_LEFT = 5;
	public static final int CL_RIGHT = 4;

	// gear ports
	public static final int GH_SOLE = 4;
	public static final int GR_SOLE = 5;
	public static final int GC_ANGLE = 15;
	public static final int GC_INTAKE = 14;

	// Sensors
	// ==================
	public static final int DB_ULTRASONIC = 0;
	public static final int DB_LEFTENCODER_A = 0;
	public static final int DB_LEFTENCODER_B = 1;
	public static final int DB_RIGHTENCODER_A = 2;
	public static final int DB_RIGHTENCODER_B = 3;
	public static final int FW_LEFTENCODER_A = 6;
	public static final int FW_LEFTENCODER_B = 7;
	public static final int FW_RIGHTENCODER_A = 4;
	public static final int FW_RIGHTENCODER_B = 5;
	public static final int GC_A = 8;
	public static final int GC_B = 9;
	// MISC

	public static final int LI_ARDUINO = 0;

}
