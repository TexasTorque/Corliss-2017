package org.texastorque.subsystem;

import org.texastorque.constants.Constants;
import org.texastorque.feedback.Feedback;
import org.texastorque.io.HumanInput;
import org.texastorque.io.RobotOutput;
import org.texastorque.torquelib.controlLoop.TorquePV;
import org.texastorque.torquelib.controlLoop.TorqueTMP;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Gear extends Subsystem {

	private static Gear instance;

	private boolean open = false;
	private boolean extended = false;
	private boolean scoopDown = false;

	private TorqueTMP collectorTMP;
	private TorquePV solePV;
	private double setpoint;
	private double previousSetpoint;
	private double collectorSpeed;
	private double prevTime;
	private double intakeSpeed;

	private double targetPosition;
	private double targetVelocity;
	private double targetAcceleration;

//	will be cleaned up later. . . 
	private boolean outakeAngleGood;
	private boolean collectingGear;
	private boolean gearJammed;
	private boolean gearOutake;
	private double cachedTime;
	
	@Override
	public void autoInit() {
		init();
	}

	@Override
	public void teleopInit() {
		init();
	}
	
	public void disabledInit() {
		open = false;
		extended = false;
		scoopDown = false;
	}

	public void init() {
		collectorTMP = new TorqueTMP(Constants.GC_MVELOCITY.getDouble(), Constants.GC_MACCELERATION.getDouble());
		solePV = new TorquePV();

		solePV.setGains(Constants.GC_LEFT_PV_P.getDouble(), Constants.GC_LEFT_PV_V.getDouble(),
				Constants.GC_LEFT_PV_ffV.getDouble(), Constants.GC_LEFT_PV_ffA.getDouble());
		solePV.setTunedVoltage(Constants.TUNED_VOLTAGE.getDouble());
	}

	@Override
	public void autoContinuous() {
		run();
	}

	@Override
	public void teleopContinuous() {
		run();
	}
	
	@Override
	public void disabledContinuous() {
		output();
	}

	public void run() {
		open = i.getGR_open();
		extended = i.getGH_extended();
		setpoint = i.getGC_setpoint();
		if (previousSetpoint != setpoint) {
			System.out.println(setpoint);
			previousSetpoint = setpoint;
			collectorTMP.generateTrapezoid(setpoint, 0d, 0d);
			prevTime = Timer.getFPGATimestamp();
		} else {
			double dt = Timer.getFPGATimestamp() - prevTime;
			prevTime = Timer.getFPGATimestamp();
			collectorTMP.calculateNextSituation(dt);

			targetPosition = collectorTMP.getCurrentPosition();
			targetVelocity = collectorTMP.getCurrentVelocity();
			targetAcceleration = collectorTMP.getCurrentAcceleration();

			collectorSpeed = solePV.calculate(collectorTMP, Feedback.getInstance().getGC_distance(),
					Feedback.getInstance().getGC_rate());
			if (Feedback.getInstance().getGC_distance() >= 70) {
				intakeSpeed = 1;
				extended = true;
				collectingGear = true;
			} else if (collectingGear && Feedback.getInstance().getGC_distance() <= 69 && Feedback.getInstance().getGC_distance() >= 8) {
				System.out.println("TEST");
				collectingGear = false;
				outakeAngleGood = true;
				cachedTime = Timer.getFPGATimestamp();
				intakeSpeed = 1;
			} else {
				if(!outakeAngleGood)
					intakeSpeed = 0;
			}
//			if(Feedback.getInstance().getGC_distance() <= 10 && Feedback.getInstance().getGC_distance() >= 8)
//				intakeSpeed = -1;
			if(outakeAngleGood)
				extended = true;
			if(outakeAngleGood && !collectingGear && Timer.getFPGATimestamp() - cachedTime > 2) {
				extended = false;
				outakeAngleGood = false;
				gearJammed = true;
				cachedTime = Timer.getFPGATimestamp();
			}
			if(gearJammed && Timer.getFPGATimestamp() - cachedTime > 3) {
				gearJammed = false;
				gearOutake = true;
				cachedTime = Timer.getFPGATimestamp();
			}
			if(gearJammed)
				intakeSpeed = 1;
			if(gearOutake && Timer.getFPGATimestamp() - cachedTime > 2) {
				intakeSpeed = 0;
				gearOutake = false;
			}
			if(gearOutake)
				intakeSpeed = -1;
			
			
		}
		output();
	}

	public void output() {
		if(i instanceof HumanInput) {
			RobotOutput.getInstance().openGearRamp(open);
			RobotOutput.getInstance().extendGearHolder(extended);
			if(collectorSpeed != 0)
				collectorSpeed -= .1;
			RobotOutput.getInstance().setGearCollectorSpeed(collectorSpeed, intakeSpeed);
		}
	}

	public static Gear getInstance() {
		return instance == null ? instance = new Gear() : instance;
	}

	@Override
	public void smartDashboard() {
		SmartDashboard.putBoolean("INTAKE_OPEN", open);
		SmartDashboard.putBoolean("ARM_EXTENDED", extended);
		SmartDashboard.putNumber("GC_TARGETPOSITION", targetPosition);
		SmartDashboard.putNumber("GC_TARGETVELOCITY", targetVelocity);
		SmartDashboard.putNumber("GC_TARGETACCELERATION", targetAcceleration);
	}

}
