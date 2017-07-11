package org.texastorque.torquelib.auto;

import java.util.ArrayList;

public abstract class TorqueInput {

	protected ArrayList<Double> leftStickX;
	protected ArrayList<Double> leftStickY;
	protected ArrayList<Double> leftStickClick;
	protected ArrayList<Double> rightStickX;
	protected ArrayList<Double> rightStickY;
	protected ArrayList<Double> rightStickClick;
	protected ArrayList<Boolean> xButton;
	protected ArrayList<Boolean> yButton;
	protected ArrayList<Boolean> bButton;
	protected ArrayList<Boolean> aButton;
	protected ArrayList<Boolean> rBumper;
	protected ArrayList<Boolean> rTrig;
	protected ArrayList<Boolean> lBumper;
	protected ArrayList<Boolean> lTrig;
	protected ArrayList<Boolean> dPadW;
	protected ArrayList<Boolean> dPadN;
	protected ArrayList<Boolean> dPadE;
	protected ArrayList<Boolean> dPadS;
	
	private StickTracker driverLeft;
	private StickTracker driverRight;
	private ButtonTracker driverX;
	private ButtonTracker driverY;
	private ButtonTracker driverB;
	private ButtonTracker driverA;
	private ButtonTracker driverDPadW;
	private ButtonTracker driverDPadN;
	private ButtonTracker driverDPadE;
	private ButtonTracker driverDPadS;

	private StickTracker operatorLeft;
	private StickTracker operatorRight;
	private ButtonTracker operatorX;
	private ButtonTracker operatorY;
	private ButtonTracker operatorB;
	private ButtonTracker operatorA;
	private ButtonTracker operatorDPadW;
	private ButtonTracker operatorDPadN;
	private ButtonTracker operatorDPadE;
	private ButtonTracker operatorDPadS;
	
	protected abstract void updateValues();
	
	public void push() {
		
		
	}
	
}
