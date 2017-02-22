package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

//	human input
	public static final Constant HI_DBDT = new Constant("HI_DEADBANDDT", 0.2);
	public static final Constant HI_DOBOTHSHOOTERS = new Constant("HI_DOBOTHSHOOTERS", 1.0);
	
//	fly wheel
	public static final Constant FW_SS = new Constant("FW_SMALLSHIFT",10);
	public static final Constant FW_LS = new Constant("FW_LARGESHIFT",100);
	public static final Constant FW_LIMIT = new Constant("FW_LIMIT",.5);
	
	public static final Constant FW_R_P = new Constant("FW_R_P",0);
	
//	intake
	public static final Constant IN_LIMIT = new Constant("IN_LIMIT", .75);
	
//	drive base
	public static final Constant DB_MVELOCITY = new Constant("DB_MVELOCITY",0d);
	public static final Constant DB_MACCELERATION = new Constant("DB_MACCELERATION",0d);
	
}
