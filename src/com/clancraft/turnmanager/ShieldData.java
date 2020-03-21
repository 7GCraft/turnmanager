package com.clancraft.turnmanager;

import java.util.HashSet;

public class ShieldData {
	private boolean isToggled;
	private HashSet<String> shieldList;
	
	public ShieldData(boolean isToggled, HashSet<String> shieldList) {
		this.isToggled = isToggled;
		this.shieldList = shieldList;
	}
	
	public boolean isToggled() {
		return isToggled;
	}
	
	public void setIsToggled(boolean isToggled) {
		this.isToggled = isToggled;
	}
	
	public HashSet<String> getShieldList() {
		return shieldList;
	}
}
