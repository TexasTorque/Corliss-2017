package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Feedback {

	private static Feedback instance;

	private final double C_FLYWHEEL = .24;
	private final double DB_DISTANCE_CONVERSION = 0.04927988;
	
//	sensors
	private TorqueEncoder DB_leftEncoder;
	private TorqueEncoder DB_rightEncoder;

	private AHRS DB_gyro;
	
	private TorqueEncoder FW_leftEncoder;
	private TorqueEncoder FW_rightEncoder;
	
//	related values
	private double DB_leftDistance;
	private double DB_rightDistance;
	
	private double DB_leftRate;
	private double DB_rightRate;

	private double DB_leftAcceleration;
	private double DB_rightAcceleration;
	
	private double DB_angle;
	private double DB_angleRate;
	
	private double FW_leftDistance;
	private double FW_rightDistance;
	
	private double FW_leftRate;
	private double FW_rightRate;
	
	private double FW_leftAcceleration;
	private double FW_rightAcceleration;
	
	public Feedback() {
		init();
	}
	
	private void init() {
		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
		DB_gyro = new AHRS(SPI.Port.kMXP);

		FW_leftEncoder = new TorqueEncoder(Ports.FW_LEFTENCODER_A, Ports.FW_LEFTENCODER_B, false, EncodingType.k4X);
		FW_rightEncoder = new TorqueEncoder(Ports.FW_RIGHTENCODER_A, Ports.FW_RIGHTENCODER_B, false, EncodingType.k4X);
	}
	
	public void update() {
		DB_leftEncoder.calc();
		DB_rightEncoder.calc();
		
		FW_leftEncoder.calc();
		FW_rightEncoder.calc();
		
		DB_leftDistance = DB_leftEncoder.getDistance() * DB_DISTANCE_CONVERSION;
		DB_rightDistance = DB_rightEncoder.getDistance() * DB_DISTANCE_CONVERSION;
		DB_leftRate = DB_leftEncoder.getRate() * DB_DISTANCE_CONVERSION;
		DB_rightRate = DB_rightEncoder.getRate() * DB_DISTANCE_CONVERSION;
		
		DB_angle = DB_gyro.getAngle();
		DB_angleRate = DB_gyro.getRate();
		
		FW_leftDistance = FW_leftEncoder.getDistance();
		FW_rightDistance = FW_rightEncoder.getDistance();
		FW_leftRate = FW_leftEncoder.getRate() * C_FLYWHEEL;
		FW_rightRate = FW_rightEncoder.getRate() * C_FLYWHEEL;
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
	
	public void resetDB_encoders() {
		DB_leftEncoder.reset();
		DB_rightEncoder.reset();
	}
	
	public void resetDB_gyro() {
		DB_gyro.reset();
	}
	
	public void smartDashboard() {
		SmartDashboard.putNumber("DB_LEFTPOSITION", DB_leftDistance);
		SmartDashboard.putNumber("DB_RIGHTPOSITION", DB_rightDistance);
		SmartDashboard.putNumber("FW_LEFTPOSITION", FW_leftDistance);
		SmartDashboard.putNumber("FW_RIGHTPOSITION", FW_rightDistance);
		SmartDashboard.putNumber("FW_LEFTRATE", FW_leftRate);
		SmartDashboard.putNumber("FW_RIGHTRATE", FW_rightRate);
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
	
}
