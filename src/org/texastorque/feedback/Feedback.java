package org.texastorque.feedback;

import org.texastorque.constants.Ports;
import org.texastorque.torquelib.component.TorqueEncoder;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;

public class Feedback {

	private static Feedback instance;
	
//	sensors
	private TorqueEncoder DB_leftEncoder;
	private TorqueEncoder DB_rightEncoder;
	
//	related values
	private double DB_leftDistance;
	private double DB_rightDistance;
	
	private double DB_leftRate;
	private double DB_rightRate;

	private double DB_leftAcceleration;
	private double DB_rightAcceleration;
	
	public Feedback() {
		init();
	}
	
	private void init() {
		DB_leftEncoder = new TorqueEncoder(Ports.DB_LEFTENCODER_A, Ports.DB_LEFTENCODER_B, false, EncodingType.k4X);
		DB_rightEncoder = new TorqueEncoder(Ports.DB_RIGHTENCODER_A, Ports.DB_RIGHTENCODER_B, false, EncodingType.k4X);
	}
	
	public void update() {
		
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
	
}
