package com.techelevator;

public abstract class Consumable {
	private double price;
	private String product;
	private int numberOfItems;

	public Consumable(String product, double price, int numberOfItems) {
		this.product = product;
		this.price = price;
		this.numberOfItems=numberOfItems;
	}

	public double getPrice() {
		return price;
	}

	public String getProduct() {
		return product;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}

}
