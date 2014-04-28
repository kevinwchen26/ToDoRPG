package battlelogic;

import java.io.Serializable;

public class AttackMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4982970380548348215L;
	
	public int damage;
	
	public AttackMessage(int damage) {
		this.damage = damage;
	}


}
