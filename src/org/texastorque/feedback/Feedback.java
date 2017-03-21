package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	/* Singleton */
	
	private static Feedback instance;
	
	
	/* Constants */

	private static final double CM_PER_INCH = 2.54;
	private static final double C_FLYWHEEL = .24;
	private static final double CONVERSION_DRIVEBASE_DISTANCE = 0.04927988;
	
	private static final double CONVERSION_PX_H = .234;
	private static final double CONVERSION_PX_V = .235;
	private static final int CLEAR_PIXY_XY = -999;
	private static final int CORRECTION_PIXY_PACKET_X = -160;
	private static final int CORRECTION_PIXY_PACKET_Y = -100;
	
	
	/* Sensors */
	
	private final TorqueEncoder DB_leftEncoder;
	private final TorqueEncoder DB_rightEncoder;

	private final AHRS DB_gyro;
	
	private final TorqueEncoder FW_leftEncoder;
	private final TorqueEncoder FW_rightEncoder;

	private final AnalogInput DB_ultrasonic;
	
	
	/* Drivebase */
	
	private double DB_leftDistance;
	private double DB_rightDistance;
	
	private double DB_leftRate;
	private double DB_rightRate;

	private double DB_leftAcceleration;
	private double DB_rightAcceleration;
	
	private double DB_angle;
	private double DB_angleRate;
	
	private double DB_distance;
	
	
	/* Flywheel */
	
	private double FW_leftDistance;
	private double FW_rightDistance;
	
	private double FW_leftRate;
	private double FW_rightRate;
	
	private double FW_leftAcceleration;
	private double FW_rightAcceleration;
	
	
	/* Pixy */
	
	private Pixy pixy;
	
	private double PX_x1;
	private double PX_y1;
	private double PX_surfaceArea1;
	private double PX_x2;
	private double PX_y2;
	private double PX_surfaceArea2;
	
	private boolean PX_goodPacket = false;
	
	public Feedback() {
		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);

		DB_ultrasonic = new AnalogInput(Ports.DB_ULTRASONIC);
		
		FW_leftEncoder = new TorqueEncoder(Ports.FW_LEFTENCODER_A, Ports.FW_LEFTENCODER_B, false, EncodingType.k4X);
		FW_rightEncoder = new TorqueEncoder(Ports.FW_RIGHTENCODER_A, Ports.FW_RIGHTENCODER_B, false, EncodingType.k4X);
		
		pixy = new Pixy();
	}
	
	private void updateDriveBase() {
		DB_leftEncoder.calc();
		DB_rightEncoder.calc();
		
		DB_leftDistance = DB_leftEncoder.getDistance() * CONVERSION_DRIVEBASE_DISTANCE;
		DB_rightDistance = DB_rightEncoder.getDistance() * CONVERSION_DRIVEBASE_DISTANCE;
		DB_leftRate = DB_leftEncoder.getRate() * CONVERSION_DRIVEBASE_DISTANCE;
		DB_rightRate = DB_rightEncoder.getRate() * CONVERSION_DRIVEBASE_DISTANCE;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getVelocityX();
		
		if(DB_ultrasonic.getAverageVoltage() >= .5) {
			DB_distance = (26 / (DB_ultrasonic.getAverageVoltage() - .15)) / CM_PER_INCH;
			if(DB_distance >= 28 || DB_distance <= 4) {
				DB_distance = -1;
			}
		} else {
			DB_distance = -1;
		}
		
		SmartDashboard.putNumber("DB_DISTANCE", DB_distance);
		SmartDashboard.putNumber("DB_ULTRASONIC", DB_ultrasonic.getAverageVoltage());
	}
	
	private void updateFlywheel() {		
		FW_leftEncoder.calc();
		FW_rightEncoder.calc();
		FW_leftDistance = FW_leftEncoder.getDistance();
		FW_rightDistance = FW_rightEncoder.getDistance();
		FW_leftRate = FW_leftEncoder.getRate() * C_FLYWHEEL;
		FW_rightRate = FW_rightEncoder.getRate() * C_FLYWHEEL;
	}
	
	private void updatePixy() {
		try {
			PixyPacket one = pixy.readPacket(1);
			PixyPacket two = pixy.readPacket(1);
			
			PX_x1 = one.X + CORRECTION_PIXY_PACKET_X;
			PX_y1 = one.Y + CORRECTION_PIXY_PACKET_Y;
			
			PX_x2 = two.X + CORRECTION_PIXY_PACKET_X;
			PX_y2 = two.Y + CORRECTION_PIXY_PACKET_Y;
			
			PX_goodPacket = true;
		} catch (Exception e) {
			PX_goodPacket = false;
		}
	}
	
	public void update() {
		updateDriveBase();
		updateFlywheel();
		updatePixy();
	}
	
	public double getDB_distance() {
		return DB_distance;
	}
	
	public double getDB_leftDistance() {
		return DB_leftDistance;
	}
	
	public double getDB_rightDistance() {
		return DB_rightDistance;
	}
	
	public double getDB_leftRate() {
		return DB_leftRate;
	}
	
	public double getDB_rightRate() {
		return DB_rightRate;
	}
	
	public double getFW_leftRate() {
		return FW_leftRate;
	}
	
	public double getFW_rightRate() {
		return FW_rightRate;
	}

	public double getDB_angle() {
		return DB_angle;
	}
	
	public double getDB_angleRate() {
		return DB_angleRate;
	}
	
	public boolean getPX_goodPacket() {
		return PX_goodPacket;
	}
	
	public double getPX_HorizontalDegreeOff() {
		return ((PX_x1 + PX_x2) / 2) * CONVERSION_PX_H;
	}
	
	public void resetDB_encoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
	}
	
	public void resetDB_gyro() {
		DB_gyro.reset();
	}
	
	private void clearPixyData() {
		PX_x1 = CLEAR_PIXY_XY;
		PX_x2 = CLEAR_PIXY_XY;
		PX_y1 = CLEAR_PIXY_XY;
		PX_y2 = CLEAR_PIXY_XY;
	}
	
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTPOSITION", DB_leftDistance);
		SmartDashboard.putNumber("DB_RIGHTPOSITION", DB_rightDistance);
		SmartDashboard.putNumber("FW_LEFTPOSITION", FW_leftDistance);
		SmartDashboard.putNumber("FW_RIGHTPOSITION", FW_rightDistance);
		SmartDashboard.putNumber("FW_LEFTRATE", FW_leftRate);
		SmartDashboard.putNumber("FW_RIGHTRATE", FW_rightRate);
		SmartDashboard.putNumber("DB_GYRO", DB_angle);
		SmartDashboard.putNumber("DB_GYRORATE", DB_angleRate);
		SmartDashboard.putNumber("GYROX", DB_gyro.getAngle());
		
		SmartDashboard.putNumber("PIXYX_1", PX_x1);
		SmartDashboard.putNumber("PIXYY_1", PX_y1);

		SmartDashboard.putNumber("PIXYX_2", PX_x2);
		SmartDashboard.putNumber("PIXYY_2", PX_y2);
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
	
}
