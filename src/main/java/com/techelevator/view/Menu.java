package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import com.techelevator.*;

public class Menu {
	static HashMap<String, Consumable> itemMap = new HashMap<String, Consumable>();

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
		Scanner input = new Scanner(System.in);
		//while (!stop) 
		{

			System.out.println("(1) Display Vending Machine Items\n(2) Purchase\n(3) Restock Machine");
			String selection = input.nextLine();
			if (selection.equals("1")) {
				displayItems();
				stop = true;
			} else if (selection.equals("2")) {
				purchase();
				stop = true;
			} else if (selection.equals("3")) {
				stop = true;
			}

		}
		input.close();

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
		mainMenu();

	}
	
	public static void purchase() {
		double balance = 0.00;
		System.out.println("(1) Feed Money\n(2) Select Product\n(3) Finish Transaction\nCurrent Money Provided: $"+balance);
		Scanner input = new Scanner(System.in);
		String selection = input.nextLine();
		if (selection.equals("1")) {
			balance=feedMoney(balance);
		} else if (selection.equals("2")) {
			selectProduct(balance);
		} else if (selection.equals("3")) {
			finishTransaction(balance);
			balance=0.00;
		}
		input.close();
		mainMenu();
	}
	
	private static double feedMoney(double balance) {
		
		System.out.println("How much money would you like to insert?");
		Scanner input = new Scanner(System.in);
		balance+=Integer.parseInt(input.nextLine());
		input.close();
		return balance;
	}
	private static Consumable selectProduct(double balance) {
		
		return null;
	}
	
	private static String finishTransaction(double balance) {

		return null;
	}
	
	public static void restockMachine() {
		
	}
}
