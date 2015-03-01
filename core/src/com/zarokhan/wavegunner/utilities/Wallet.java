package com.zarokhan.wavegunner.utilities;

public class Wallet {
	
	private int money;
	
	public Wallet(){
		money = 500;
	}
	
	public void AddMoney(int amount){
		money += amount;
	}
	
	public boolean purchase(int cost){
		if(money - cost < 0)
			return false;
		money -= cost;
		return true;
	}
	
	public int getBalance() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
