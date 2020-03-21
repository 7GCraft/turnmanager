package com.clancraft.turnmanager;

import java.util.ArrayList;

public class ShieldData {
	private boolean isToggled;
	private ArrayList<String> shieldList;
	
	public ShieldData(boolean isToggled, ArrayList<String> shieldList) {
		this.isToggled = isToggled;
		this.shieldList = shieldList;
	}
	
	public boolean isToggled() {
		return isToggled;
	}
	
	public void setIsToggled(boolean isToggled) {
		this.isToggled = isToggled;
	}
	
	public ArrayList<String> getShieldList() {
		return shieldList;
	}
}
