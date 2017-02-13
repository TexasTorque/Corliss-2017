package org.texastorque.feedback;

public class Feedback {

	private static Feedback instance;
	
	public Feedback() {
		init();
	}
	
	private void init() {
		
	}
	
	public static Feedback getInstance() {
		return instance == null ? instance = new Feedback() : instance;
	}
	
}
