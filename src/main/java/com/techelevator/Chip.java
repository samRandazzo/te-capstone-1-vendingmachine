package com.techelevator;

public class Chip extends Consumable {

	public Chip(String product, double price, int numberOfItems) {
		super(product, price, numberOfItems);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return "Crunch Crunch, Yum!";
	}

}
