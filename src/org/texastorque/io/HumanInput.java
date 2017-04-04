package org.texastorque.io;

import org.texastorque.constants.Constants;
import org.texastorque.torquelib.util.GenericController;
import org.texastorque.torquelib.util.TorqueToggle;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class HumanInput extends Input {

	private static HumanInput instance;

	private GenericController driver;
	private GenericController operator;

	private TorqueToggle switchShooter;
	private TorqueToggle doShooter;
	private TorqueToggle climber;
	private TorqueToggle hood;
	private TorqueToggle driverFineControl;

	private TorqueToggle doLongShot;
	private TorqueToggle doLayupShot;

	private double dT;
	private double lT;

	private boolean intaking;
	private boolean shouldDoRumble = true;

	public HumanInput() {
		init();
	}

	private void init() {
		FW_leftSetpoint = 0;
		FW_rightSetpoint = 0;
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		switchShooter = new TorqueToggle(false);
		climber = new TorqueToggle(false);
		hood = new TorqueToggle(false);
		doLayupShot = new TorqueToggle(false);
		doLongShot = new TorqueToggle(false);
		driverFineControl = new TorqueToggle(false);
		System.out.println("INITED HUMAN INPUT");
		intaking = false;
	}

	public void update() {

		// operator hood control
		updateHood();

		// driver drive control
		updateDrive();

		// operator shooter control
		updateShooter();

		// operator intake control
		updateIntake();

		// operator twinsters / conveyor control
		updateTwinsters();

		// operator climber control
		updateClimber();

		// operator gear manipulation control
		updateGear();

		// operator gate speed control
		updateGates();
	}// update

	public void updateDrive() {
		DB_rightSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
		DB_leftSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();

		driverFineControl.calc(driver.getRightStickClick());
		if (driverFineControl.get()) {
			DB_rightSpeed *= .5;
			DB_leftSpeed *= .5;
		}

		if (driver.getLeftBumper()) {
			DB_shiftSole = false;
		}
		if (driver.getRightBumper()) {
			DB_shiftSole = true;
		}
	}

	public void updateShooter() {
		dT = Timer.getFPGATimestamp() - lT;
		doLayupShot.calc(operator.getDPADDown());
		doLongShot.calc(operator.getDPADUp());

		if (doLongShot.get()) {
			FW_setpointShift = Constants.FW_LONGSHOT.getDouble();
			doLongShot.set(false);
			hood.set(false);
		} else if (doLayupShot.get()) {
			FW_setpointShift = Constants.FW_LAYUPSHOT.getDouble();
			doLayupShot.set(false);
			hood.set(true);
		} else if (dT >= Constants.HI_DBDT.getDouble()) {
			if (operator.getDPADRight()) {
				FW_setpointShift += Constants.FW_LS.getDouble();
			} else if (operator.getDPADLeft()) {
				FW_setpointShift -= Constants.FW_LS.getDouble();
			}
			lT = Timer.getFPGATimestamp();
		}
		if (operator.getAButton()) {
			FW_setpointShift = 0;
			hood.set(false);
		}
		if (FW_setpointShift < 0) {
			FW_setpointShift = 0;
		}
		FW_leftSetpoint = FW_setpointShift;
		FW_rightSetpoint = FW_setpointShift-150;
		if (FW_rightSetpoint < 0) {
			FW_rightSetpoint = 0;
		}
		if (FW_leftSetpoint < 0) {
			FW_leftSetpoint = 0;
		}
		if (operator.getLeftStickClick()) {
			RobotOutput.getInstance().setLight(true);
		} else {
			RobotOutput.getInstance().setLight(false);
		}
	}

	public void updateShooterOverride() {
		dT = Timer.getFPGATimestamp() - lT;
		doLayupShot.calc(operator.getDPADDown());
		doLongShot.calc(operator.getDPADUp());

		if (doLongShot.get()) {
			FW_setpointShift = .75;
			doLongShot.set(false);
			hood.set(true);
		} else if (doLayupShot.get()) {
			FW_setpointShift = .5;
			doLayupShot.set(false);
			hood.set(false);
		} else if (dT >= Constants.HI_DBDT.getDouble()) {
			if (operator.getDPADRight()) {
				FW_setpointShift += .05;
			} else if (operator.getDPADLeft()) {
				FW_setpointShift -= .05;
			}
			lT = Timer.getFPGATimestamp();
		}
		if (operator.getAButton()) {
			FW_setpointShift = 0;
		}
		if (FW_setpointShift < 0) {
			FW_setpointShift = 0;
		}
		RobotOutput.getInstance().setFlyWheelSpeed(FW_setpointShift, FW_setpointShift);
	}

	public void updateShooterDeprecated() {
		dT = Timer.getFPGATimestamp() - lT;
		if (dT >= Constants.HI_DBDT.getDouble()) {
			if (operator.getDPADUp()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSetpoint += Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSetpoint += Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADDown()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSetpoint -= Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSetpoint -= Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADRight()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSetpoint += Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSetpoint += Constants.FW_SS.getDouble();
				}
			} else if (operator.getDPADLeft()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSetpoint -= Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSetpoint -= Constants.FW_SS.getDouble();
				}
			}
			if (FW_leftSetpoint < 0) {
				FW_leftSetpoint = 0;
			}
			if (FW_rightSetpoint < 0) {
				FW_rightSetpoint = 0;
			}
			lT = Timer.getFPGATimestamp();
		}
		if (shouldDoRumble && doRumble) {
			operator.setRumble(true);
		} else {
			operator.setRumble(false);
		}
	}

	public void updateHood() {
		switchShooter.calc(operator.getXButton());
//		hood.calc(operator.getBButton());

		if (hood.get()) {
			FW_hood = true;
		} else {
			FW_hood = false;
		}
	}

	public void updateIntake() {
		if (operator.getLeftBumper() && operator.getRightStickClick()) {
			IN_upperSpeed = 1d; // .5
			IN_lowerSpeed = .3d;
			intaking = true;
		} else if (operator.getLeftBumper()) {
			IN_upperSpeed = .5d;
			IN_lowerSpeed = -1;
			intaking = true;
		} else if (operator.getRightBumper()) {
			IN_upperSpeed = -1d;
			IN_lowerSpeed = 1d;
			intaking = true;
		} else {
			intaking = false;
			IN_upperSpeed = 0d;
			IN_lowerSpeed = 0d;
		}
	}

	public void updateGates() {
		if (operator.getXButton()) {
			FW_gateSpeed = 1; // + (getFW_leftSetpoint() -
								// Feedback.getInstance().getFW_leftRate()) *
								// .01;
		} else {
			FW_gateSpeed = 0d;
		}
	}

	public void updateTwinsters() {
		if (operator.getLeftTrigger()) {
			TW_leftSpeed = 1d;
			TW_rightSpeed = 1d;
		} else if (operator.getRightTrigger()) {
			TW_leftSpeed = -1d;
			TW_rightSpeed = -1d;
		} else {
			TW_leftSpeed = 0d;
			TW_rightSpeed = 0d;
			if (!intaking)
				IN_lowerSpeed = 0d;
		}
	}

	public void updateClimber() {
		climber.calc(driver.getXButton());
		if (climber.get()) {
			CL_speed = 1d;
		} else {
			CL_speed = 0d;
		}
	}

	public void updateGear() {
		if (operator.getYButton()) {
			GR_open = true;
		} else {
			GR_open = false;
		}
		if (driver.getBButton()) {
			GH_extended = true;
			RobotOutput.getInstance().setGearCollectorSpeed(-1);
			GC_override = true;
		} else {
			GH_extended = false;
			GC_override = false;
		}
		if (operator.getBButton()) {
			GC_down = true;
		} else {
			GC_down = false;
		}
	}// update gear

	public void smartDashboard() {
		SmartDashboard.putNumber("HI_DT", dT);
		SmartDashboard.putNumber("FW_GATE", FW_gateSpeed);
	}

	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}

}
