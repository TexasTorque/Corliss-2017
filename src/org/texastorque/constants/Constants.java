package org.texastorque.constants;

import org.texastorque.torquelib.util.Parameters.Constant;

public class Constants {

//	human input
	public static final Constant HI_DBDT = new Constant("HI_DEADBANDDT", 0.2);
	public static final Constant HI_DOBOTHSHOOTERS = new Constant("HI_DOBOTHSHOOTERS", 1.0);
	
//	fly wheel
	public static final Constant FW_SS = new Constant("FW_SMALLSHIFT",.01);
	public static final Constant FW_LS = new Constant("FW_LARGESHIFT",.1);
	public static final Constant FW_LIMIT = new Constant("FW_LIMIT",.5);
	
//	intake
	public static final Constant IN_LIMIT = new Constant("IN_LIMIT", .75);
	
}
