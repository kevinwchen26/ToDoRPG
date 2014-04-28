package com.cs429.todorpg.battlelogic;

import java.io.Serializable;

public class BtPackage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7047686311902715272L;
	public static final int SHARE_INFO_PACKAGE = 110;
	public static final int ATTACK_PACKAGE = 111;
	
	private Object obj;
	private int identifier;
	
	public BtPackage(int identifier,Object obj) {
		this.setIdentifier(identifier);
		this.obj = obj;
	}
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}

	public int getIdentifier() {
		return identifier;
	}

	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	
	
}
