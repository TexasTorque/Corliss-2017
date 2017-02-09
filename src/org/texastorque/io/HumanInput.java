package org.texastorque.io;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.Timer;

public class HumanInput extends Input {

	private static HumanInput instance;

	private GenericController driver;
	private GenericController operator;

	private TorqueToggle switchShooter;

	private boolean intakeRunning = false;

	private double dT;
	private double lT;

	public HumanInput() {
		init();
	}

	private void init() {
		driver = new GenericController(-1, .1);
		operator = new GenericController(-1, .1);

		switchShooter = new TorqueToggle(false);
	}

	public void update() {
		// driver drive control

		DB_leftSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();

		// operator shooter control

		switchShooter.calc(operator.getXButton());

		dT = lT - Timer.getFPGATimestamp();

		if (dT >= Constants.HI_DBDT.getDouble()) {
			if (operator.getDPADUp()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && switchShooter.get()) {
					FW_leftSpeed += Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && !switchShooter.get()) {
					FW_rightSpeed += Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADDown()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && switchShooter.get()) {
					FW_leftSpeed -= Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && !switchShooter.get()) {
					FW_rightSpeed -= Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADRight()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && switchShooter.get()) {
					FW_leftSpeed += Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && !switchShooter.get()) {
					FW_rightSpeed += Constants.FW_SS.getDouble();
				}
			} else if (operator.getDPADLeft()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && switchShooter.get()) {
					FW_leftSpeed -= Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() && !switchShooter.get()) {
					FW_rightSpeed -= Constants.FW_SS.getDouble();
				}
			}
		}

		// operator intake control

		if (operator.getYButton()) {
			IN_lowerSpeed = 1d;
			IN_upperSpeed = 1d;
			intakeRunning = true;
		}
		if (operator.getAButton()) {
			IN_lowerSpeed = 1d;
			IN_upperSpeed = 1d;
			intakeRunning = true;
		}
		if (!intakeRunning) {
			IN_lowerSpeed = 0d;
			IN_upperSpeed = 0d;
		}
		intakeRunning = false;

	}

	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}

}
