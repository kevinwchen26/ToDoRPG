package com.CS429.todorpg;

public class AlarmPair<E, V>{

	private E first;
	private V second;
	
	public AlarmPair(E comp1, V comp2){
		first = comp1;
		second = comp2;
	}

	public E getFirst(){
		return first;
	}
	
	public V getSecond(){
		return second;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AlarmPair other = (AlarmPair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	
	
	
}
