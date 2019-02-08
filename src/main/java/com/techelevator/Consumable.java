package com.techelevator;

public abstract class Consumable {
	private double price;
	private String product;
	private int numberOfItems;
	private String location;

	public Consumable(String product, double price, int numberOfItems, String location) {
		this.product = product;
		this.price = price;
		this.numberOfItems=numberOfItems;
		this.location=location;
	}
	
	public abstract String getMessage();

	public double getPrice() {
		return price;
	}

	public String getProduct() {
		return product;
	}

	public int getNumberOfItems() {
		return numberOfItems;
	}
	
	public void oneLessItem() {
		this.numberOfItems-=1;
	}

	public String getLocation() {
		return location;
	}

}
