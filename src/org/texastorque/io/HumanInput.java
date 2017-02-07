package org.texastorque.io;

import org.texastorque.torquelib.util.GenericController;

public class HumanInput extends Input {

	private static HumanInput instance;
	
	private GenericController driver;
	private GenericController operator;
	
	
	public HumanInput() {
		init();
	}
	
	private void init() {
		driver = new GenericController(-1, .1);
		operator = new GenericController(-1, .1);
	}
	
	public void update() {
		Input.getInstance().setDB_leftSpeed(-driver.getLeftYAxis() - driver.getRightXAxis());
		Input.getInstance().setDB_rightSpeed(-driver.getLeftYAxis() + driver.getRightXAxis());
	}
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}
	
}
