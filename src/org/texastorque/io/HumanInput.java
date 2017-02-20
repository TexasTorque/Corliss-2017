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

	private double dT;
	private double lT;

	private boolean intaking;

	public HumanInput() {
		init();
	}

	private void init() {
		driver = new GenericController(0, .1);
		operator = new GenericController(1, .1);

		switchShooter = new TorqueToggle(false);
		climber = new TorqueToggle(false);
		System.out.println("INITED HUMAN INPUT");
		intaking = false;
	}

	public void update() {
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
		
		//operator gear manipulation control
		updateGear();
		
		//operator gate speed control
		updateGates();
	}//update
	
	public void updateDrive() {
		DB_leftSpeed = -driver.getLeftYAxis() - driver.getRightXAxis();
		DB_rightSpeed = -driver.getLeftYAxis() + driver.getRightXAxis();
<<<<<<< HEAD
=======
		
		if(driver.getLeftBumper()){
			DB_gear=false;
		}
		if(driver.getRightBumper()){
			DB_gear=true;
		}
		
		shooter=operator.getLeftStickClick();
>>>>>>> 05728e65f26b972a314cba83a4c9cc7c273dfdee
	}
	
	public void updateShooter() {
		switchShooter.calc(operator.getXButton());
		dT = Timer.getFPGATimestamp() - lT;
		if (dT >= Constants.HI_DBDT.getDouble()) {
			if (operator.getDPADUp()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSpeed += Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSpeed += Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADDown()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSpeed -= Constants.FW_LS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSpeed -= Constants.FW_LS.getDouble();
				}
			} else if (operator.getDPADRight()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSpeed += Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSpeed += Constants.FW_SS.getDouble();
				}
			} else if (operator.getDPADLeft()) {
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || switchShooter.get()) {
					FW_leftSpeed -= Constants.FW_SS.getDouble();
				}
				if (Constants.HI_DOBOTHSHOOTERS.getBoolean() || !switchShooter.get()) {
					FW_rightSpeed -= Constants.FW_SS.getDouble();
				}
			}
			if(FW_leftSpeed < 0) {
				FW_leftSpeed = 0;
			}
			if(FW_rightSpeed < 0) {
				FW_rightSpeed = 0;
			}
			lT = Timer.getFPGATimestamp();
		}
	}
	
	public void updateIntake() {
		if (operator.getLeftBumper()) {
			IN_speed = 1d;
			TW_feederSpeed = 1d;
			intaking = true;
		} else if (operator.getRightBumper()) {
			IN_speed = -1d;
			intaking = true;
		} else {
			IN_speed = 0d;
			TW_feederSpeed = 0d;
		}
	}
	
	public void updateGates() {
		if (operator.getXButton()) {
			FW_gateSpeed = Constants.FW_LIMIT.getDouble();
		} else {
			FW_gateSpeed = 0d;
		}
	}
	
	public void updateTwinsters() {
		if (operator.getLeftTrigger()) {
			TW_leftSpeed = 1d;
			TW_rightSpeed = 1d;
			TW_feederSpeed = 1d;
		} else if(operator.getRightTrigger()) {
			TW_leftSpeed = -1d;
			TW_rightSpeed = -1d;
			TW_feederSpeed = -1d;
		} else {
			TW_leftSpeed = 0d;
			TW_rightSpeed = 0d;
			if(!intaking)
				TW_feederSpeed = 0d;
		}
	}
	
	public void updateClimber() {
		climber.calc(operator.getAButton());
		if(climber.get()) {
			CL_speed = 1d;
		} else {
			CL_speed = 0d;
		}
	}
	
	public void updateGear(){
		if(operator.getYButton()){
			GR_open=true;
		}
		else {
			GR_open=false;
		}
		if(operator.getBButton()){
			GH_extended=true;
		} else{
			GH_extended=false;
		}
		
	}//update gear

	public void SmartDashboard() {
		SmartDashboard.putNumber("HI_DT", dT);
	}
	
	public static HumanInput getInstance() {
		return instance == null ? instance = new HumanInput() : instance;
	}

}
