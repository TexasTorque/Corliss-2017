package org.texastorque.torquelib.auto;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.Timer;

public abstract class TorqueInput {

	protected ArrayList<Double> leftStickX;
	protected ArrayList<Double> leftStickY;
	protected ArrayList<Double> rightStickX;
	protected ArrayList<Double> rightStickY;
	protected ArrayList<Boolean> leftStickClick;
	protected ArrayList<Boolean> rightStickClick;
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

	private StickTracker trackerLeft = new StickTracker();
	private StickTracker trackerRight = new StickTracker();
	private ButtonTracker trackerX = new ButtonTracker();
	private ButtonTracker trackerY = new ButtonTracker();
	private ButtonTracker trackerB = new ButtonTracker();
	private ButtonTracker trackerA = new ButtonTracker();
	private ButtonTracker trackerDPadW = new ButtonTracker();
	private ButtonTracker trackerDPadN = new ButtonTracker();
	private ButtonTracker trackerDPadE = new ButtonTracker();
	private ButtonTracker trackerDPadS = new ButtonTracker();

	protected abstract void updateValues();

	public void push() {
		double timeStamp = Timer.getFPGATimestamp();
		trackerLeft.update(leftStickX.get(0), leftStickX.get(1),
				leftStickY.get(0), leftStickY.get(0), rightStickX.get(0),
				rightStickX.get(1), rightStickY.get(0), rightStickY.get(1),
				leftStickClick.get(0), leftStickClick.get(1),
				rightStickClick.get(0), rightStickClick.get(1), timeStamp);
		trackerRight.update(rightStickX.get(0), rightStickX.get(1),
				rightStickY.get(0), rightStickY.get(0), rightStickX.get(0),
				rightStickX.get(1), rightStickY.get(0), rightStickY.get(1),
				rightStickClick.get(0), rightStickClick.get(1),
				rightStickClick.get(0), rightStickClick.get(1), timeStamp);
		trackerX.update(xButton.get(0), xButton.get(1), timeStamp);
		trackerY.update(yButton.get(0), yButton.get(1), timeStamp);
		trackerB.update(bButton.get(0), bButton.get(1), timeStamp);
		trackerA.update(aButton.get(0), aButton.get(1), timeStamp);
		trackerDPadW.update(dPadW.get(0), dPadW.get(1), timeStamp);
		trackerDPadE.update(dPadN.get(0), dPadN.get(1), timeStamp);
		trackerDPadN.update(dPadE.get(0), dPadE.get(1), timeStamp);
		trackerDPadS.update(dPadS.get(0), dPadS.get(1), timeStamp);
	}
	
	public void finish() {
		trackerLeft.print();
		trackerRight.print();
		trackerX.print();
		trackerY.print();
		trackerB.print();
		trackerA.print();
		trackerDPadW.print();
		trackerDPadN.print();
		trackerDPadE.print();
		trackerDPadS.print();
	}

}
