package com.techelevator;

public class Drink extends Consumable {

	public Drink(String product, double price, int numberOfItems, String location) {
		super(product, price, numberOfItems, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Glug Glug, Yum!";
	}

}
