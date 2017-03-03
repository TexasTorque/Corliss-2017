package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

//	human input
	public static double HI_DBDBT = 1d;
	public static final Constant HI_DBDT = new Constant("HI_DEADBANDDT", 0.2);
	public static final Constant HI_DOBOTHSHOOTERS = new Constant("HI_DOBOTHSHOOTERS", 1.0);
	
//	fly wheel
	public static final Constant FW_SS = new Constant("FW_SMALLSHIFT",10);
	public static final Constant FW_LS = new Constant("FW_LARGESHIFT",100);
	public static final Constant FW_LIMIT = new Constant("FW_LIMIT",1);
	public static final Constant FW_LONGSHOT = new Constant("FW_LONGSHOT",4100);
	public static final Constant FW_LAYUPSHOT = new Constant("FW_LAYUP",3500);
	
	public static final Constant FW_R_P = new Constant("FW_R_P",0);
	public static final Constant FW_ACCEPTABLE = new Constant("FW_ACCEPTABLE", 50);
	
//	intake
	public static final Constant IN_LIMIT = new Constant("IN_LIMIT", .75);
	
//	drive base
	public static final Constant DB_MVELOCITY = new Constant("DB_MVELOCITY",50d);
	public static final Constant DB_MACCELERATION = new Constant("DB_MACCELERATION",200d);
	public static final Constant DB_MAVELOCITY = new Constant("DB_MAVELOCITY",50d);
	public static final Constant DB_MAACCELERATION = new Constant("DB_MAACCELERATION",200d);
	
	public static final Constant DB_RIGHT_PV_P = new Constant("DB_RIGHT_PV_P", .08);
	public static final Constant DB_RIGHT_PV_V = new Constant("DB_RIGHT_PV_V", .008);
	public static final Constant DB_RIGHT_PV_ffV = new Constant("DB_RIGHT_PV_ffV", 0.002);
	public static final Constant DB_RIGHT_PV_ffA = new Constant("DB_RIGHT_PV_ffA", 0.0);

	public static final Constant DB_LEFT_PV_P = new Constant("DB_LEFT_PV_P", .08);
	public static final Constant DB_LEFT_PV_V = new Constant("DB_LEFT_PV_V", .008);
	public static final Constant DB_LEFT_PV_ffV = new Constant("DB_LEFT_PV_ffV", 0.002);
	public static final Constant DB_LEFT_PV_ffA = new Constant("DB_LEFT_PV_ffA", 0.0);
	
	public static final Constant DB_TURN_PV_P = new Constant("DB_TURN_PV_P", .08);
	public static final Constant DB_TURN_PV_V = new Constant("DB_TURN_PV_V", .008);
	public static final Constant DB_TURN_PV_ffV = new Constant("DB_TURN_PV_ffV", .002);
	public static final Constant DB_TURN_PV_ffA = new Constant("DB_TURN_PV_ffA", 0.0);
	
	public static final Constant TUNED_VOLTAGE = new Constant("TUNED_VOLTAGE", 12.5);
}
