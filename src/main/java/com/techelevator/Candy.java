package com.techelevator;

public class Candy extends Consumable {

	public Candy(String product, double price, int numberOfItems, String location) {
		super(product, price, numberOfItems, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Munch Munch, Yum!";
	}

}
