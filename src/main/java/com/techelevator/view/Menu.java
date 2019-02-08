package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.*;

public class Menu {
	// We have to make a list of everything in our Vending machine
	static LinkedHashMap<String, Consumable> itemMap = new LinkedHashMap<String, Consumable>();
	static Scanner input = new Scanner(System.in);

	public static void stockMachine() {
		File input = new File("vendingmachine.csv");

		Scanner fileScanner;
		try {
			fileScanner = new Scanner(input);
			{
				while (fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					String[] lineSplit = line.split("[|]");
					String location = lineSplit[0];
					String product = lineSplit[1];
					double price = Double.parseDouble(lineSplit[2]);
					String type = lineSplit[3];
					int numberOfItems = 5;

					// Pretty sure one of those 'Case' things would work here but I can't figure out
					// how to do that

					if (type.equals("Gum")) {
						Consumable item = new Gum(product, price, numberOfItems);
						itemMap.put(location, item);
					}
					if (type.equals("Candy")) {
						Consumable item = new Candy(product, price, numberOfItems);
						itemMap.put(location, item);
					}
					if (type.equals("Chip")) {
						Consumable item = new Chip(product, price, numberOfItems);
						itemMap.put(location, item);
					}
					if (type.equals("Drink")) {
						Consumable item = new Drink(product, price, numberOfItems);
						itemMap.put(location, item);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void mainMenu() {
		boolean stop = false;

		while (!stop) {

			System.out.println("(1) Display Vending Machine Items\n(2) Purchase\n(3) Restock Machine");
			int selection = Integer.parseInt(input.nextLine());
			if (selection == 1) {
				displayItems();
				System.out.println();

			} else if (selection == 2) {
				purchase();
				System.out.println();

			} else if (selection == 3) {
				stop = true;
			}

		}

	}

	public static void displayItems() {
		Set<String> keys = itemMap.keySet();
		for (String key : keys) {
			Consumable item = itemMap.get(key);
			System.out.println(
					"Product Code: " + key + "\tProduct: " + item.getProduct() + "\tPrice: $" + item.getPrice());
			if (item.getNumberOfItems() == 0) {
				System.out.print("\tSOLD OUT");
			}
		}

	}

	public static void purchase() {
		double balance = 0.00;

		boolean stop = false;
		while (!stop) {
			System.out.printf(
					"(1) Feed Money\n(2) Select Product\n(3) Finish Transaction\nCurrent Money Provided: $%.2f\n",
					balance);

			int selection = Integer.parseInt(input.nextLine());
			if (selection == 1) {
				balance = feedMoney(balance);
				System.out.printf("Your balance is: $%.2f\n", balance);
			} else if (selection == 2) {
				System.out.println("Enter product location: ");
				String location = input.nextLine();
				Consumable item = itemMap.get(location);
				if (item.getPrice() <= balance && item.getNumberOfItems() > 0) {
					balance = purchaseProduct(item, balance);
					item.oneLessItem();
				} else {
					System.out.println("Sorry, we could not complete your purchase.");
				}

			} else if (selection == 3) {
				finishTransaction(balance);
				balance = 0.00;
				stop = true;
			}

		}

	}

	private static double feedMoney(double balance) {

		System.out.println("How much money would you like to insert?");

		balance += Integer.parseInt(input.nextLine());

		return balance;
	}

	private static double purchaseProduct(Consumable item, double balance) {
		double price = item.getPrice();
		balance -= price;
		return balance;

	}

	private static String finishTransaction(double balance) {

		return null;
	}

	public static void restockMachine() {

	}

}
