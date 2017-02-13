package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

//	human input
	public static final Constant HI_DBDT = new Constant("HI_DEADBANDDT", 0.1);
	public static final Constant HI_DOBOTHSHOOTERS = new Constant("HI_DOBOTHSHOOTERS", 0.0);
	
//	fly wheel
	public static final Constant FW_SS = new Constant("FW_SMALLSHIFT",.01);
	public static final Constant FW_LS = new Constant("FW_LARGESHIFT",.1);
	
//	drive base
	public static final Constant DB_MVELOCITY = new Constant("DB_MVELOCITY",0d);
	public static final Constant DB_MACCELERATION = new Constant("DB_MACCELERATION",0d);
	
}
