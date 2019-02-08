package com.techelevator.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Date;

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
						Consumable item = new Gum(product, price, numberOfItems, location);
						itemMap.put(location, item);
					}
					if (type.equals("Candy")) {
						Consumable item = new Candy(product, price, numberOfItems, location);
						itemMap.put(location, item);
					}
					if (type.equals("Chip")) {
						Consumable item = new Chip(product, price, numberOfItems, location);
						itemMap.put(location, item);
					}
					if (type.equals("Drink")) {
						Consumable item = new Drink(product, price, numberOfItems, location);
						itemMap.put(location, item);
					}
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//If the file doesn't exist, create it
		// if the file does exist, write over it

		File log = new File("log.txt");
		if (!log.exists()) {
			try {
				log.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			PrintWriter writer = new PrintWriter(log);
			writer.println("VENDING MACHINE LOG");
			writer.close();
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
				restockMachine();
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
		List<Consumable> purchasedItems = new ArrayList<>();
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
				try {
					Consumable item = itemMap.get(location.toUpperCase());
					if (item.getPrice() <= balance && item.getNumberOfItems() > 0) {
						balance = purchaseProduct(item, balance);
						item.oneLessItem();
						purchasedItems.add(item);
					} else {
						if (item.getNumberOfItems() <= 0) {
							System.out.println("SOLD OUT");
						}
						if (item.getPrice() > balance) {
							System.out.printf("Insert $%.2f\n", (item.getPrice() - balance));
						}
					}
				} catch (Exception e) {
					System.out.println("Code Error: The code you have entered does not exist. Please try again");
				}

			} else if (selection == 3) {
				for (Coin c : makeChange(balance)) {
					System.out.println(c.getName());
				}
				System.out.println(finishTransaction(purchasedItems));

				balance = 0.00;
				purchasedItems.clear();
				stop = true;
			}

		}

	}

	public static double feedMoney(double balance) {

		System.out.println("How much money would you like to insert?");
		
		double newBalance;
		
		int moneyIn;
		
		String stringInput = input.nextLine();

	try { moneyIn = Integer.parseInt(stringInput);}
	
	catch(Exception e) {
		moneyIn=0;
	}

		newBalance = balance + moneyIn;

		Date date = new Date();

		try {
			FileWriter fw = new FileWriter("log.txt", true);
			PrintWriter appendWriter = new PrintWriter(fw);
			appendWriter.print(date.toGMTString() + "\t\t");
			appendWriter.printf("FEED MONEY:\t $%.2f", balance);
			appendWriter.printf("\t$%.2f\n", newBalance);
			appendWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return newBalance;
	}

	static double purchaseProduct(Consumable item, double balance) {
		double price = item.getPrice();
		double newBalance = balance - price;
		Date date = new Date();
		try {
			FileWriter fw = new FileWriter("log.txt", true);
			PrintWriter appendWriter = new PrintWriter(fw);
			appendWriter.print(date.toGMTString() + "\t\t");
			appendWriter.printf(item.getProduct() + "\t" + item.getLocation() + "\t $%.2f", balance);
			appendWriter.printf("\t$%.2f\n", newBalance);
			appendWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newBalance;

	}

	static String finishTransaction(List<Consumable> purchasedItems) {
		String message = "";
		for (Consumable item : purchasedItems) {
			message = message + "\n" + item.getMessage();
		}

		return message;
	}

	static List<Coin> makeChange(double balance) {
		List<Coin> change = new ArrayList<>();
		int intBalance = (int) (balance * 100);
		int numberOfQuarters;
		int numberOfDimes;
		int numberOfNickels;
		numberOfQuarters = intBalance / 25;
		intBalance -= (numberOfQuarters * 25);
		numberOfDimes = intBalance / 10;
		intBalance -= (numberOfDimes * 10);
		numberOfNickels = intBalance / 5;
		intBalance -= (numberOfNickels * 5);
		while (numberOfQuarters > 0) {
			Coin quarter = new Quarter();
			change.add(quarter);
			numberOfQuarters--;
		}
		while (numberOfDimes > 0) {
			Coin dime = new Dime();
			change.add(dime);
			numberOfDimes--;
		}
		while (numberOfNickels > 0) {
			Coin nickel = new Nickel();
			change.add(nickel);
			numberOfNickels--;
		}
		Date date = new Date();
		try {
			FileWriter fw = new FileWriter("log.txt", true);
			PrintWriter appendWriter = new PrintWriter(fw);
			appendWriter.print(date.toGMTString() + "\t\t");
			appendWriter.printf("GIVE CHANGE\t $%.2f", balance);
			appendWriter.print("\t$0.00\n");
			appendWriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return change;

	}

	public static void restockMachine() {

		File salesReport = new File("salesreport.txt");
		LinkedHashMap<String, Integer> salesReportMap = new LinkedHashMap<>();
		double totalSales = 0;

		{
			Set<String> keys = itemMap.keySet();
			for (String key : keys) {
				Consumable item = itemMap.get(key);
				salesReportMap.put(item.getProduct(), 0);
			}
		}

		if (salesReport.exists()) {
			Scanner fileScanner;
			try {
				fileScanner = new Scanner(salesReport);
				{
					while (fileScanner.hasNextLine()) {
						String line = fileScanner.nextLine();
						if (line.contains("|")) {
							String[] lineSplit = line.split("[|]");
							String product = lineSplit[0];
							int numberSold = Integer.parseInt(lineSplit[1]);
							salesReportMap.put(product, numberSold);
						}
						if (line.contains("TOTAL SALES")) {
							String stringTotalSales = line.substring(17);
							totalSales = Double.parseDouble(stringTotalSales);
						}
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
		}
		if (!salesReport.exists()) {
			try {
				salesReport.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Set<String> keys = itemMap.keySet();
			for (String key : keys) {
				Consumable item = itemMap.get(key);
				int initial = salesReportMap.get(item.getProduct());
				int result = (5 - item.getNumberOfItems()) + initial;
				totalSales += (5 - item.getNumberOfItems()) * item.getPrice();
				salesReportMap.put(item.getProduct(), result);
			}
		}

		try (PrintWriter writer = new PrintWriter(salesReport)) {
			Set<String> keys = salesReportMap.keySet();
			for (String key : keys) {
				writer.println(key + "|" + salesReportMap.get(key));
			}
			writer.println();
			writer.printf("**TOTAL SALES** $%.2f\n", totalSales);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
