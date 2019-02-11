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
		/*
		 * This is the method that sets up the machine
		 */
		File input = new File("vendingmachine.csv");

		Scanner fileScanner;
		try {
			fileScanner = new Scanner(input);
			{
				/*
				 * We use a while loop to look at every line of the input file. Each line is
				 * split into the four bits of information we have about each item. We split
				 * around the "|" symbol. This gives us an array where index 0 is the code you
				 * enter to select an item index 1 is the name of the product index 2 is the
				 * price of the product index 3 is the type of product We also automatically set
				 * number of items to 5 since each product always starts with five items per the
				 * instructions.
				 */
				while (fileScanner.hasNextLine()) {
					String line = fileScanner.nextLine();
					String[] lineSplit = line.split("[|]");
					String location = lineSplit[0];
					String product = lineSplit[1];
					double price = Double.parseDouble(lineSplit[2]);
					String type = lineSplit[3];
					int numberOfItems = 5;

					/*
					 * Each line of the input file became an array and now will be an object. Every
					 * object created will be named "item" but they all have a unique "location" in
					 * our map of items so we have them all under control.
					 * 
					 * Pretty sure one of those 'Case' things would work here but I can't figure out
					 * how to do that
					 */

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
		/*
		 * If the log file doesn't exist, create it. If the log file does exist, write
		 * over it
		 */
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

	/*
	 * HERE IT IS. THE FABLED 'MAIN MENU' THIS IS WHERE WE CAN ACCESS ALL OF OUR
	 * FUNCTIONS
	 */

	public static void mainMenu() {
		boolean stop = false;

		while (!stop) {

			/*
			 * OPTION ONE DISPLAYS ALL OF THE INFORMATION ABOUT THE ITEMS IN THE VENDING
			 * MACHINE
			 */

			System.out.println("(1) Display Vending Machine Items\n(2) Purchase\n(3) Restock Machine");
			try{int selection = Integer.parseInt(input.nextLine());
			if (selection == 1) {
				displayItems();
				System.out.println();

				/*
				 * OPTION TWO ALLOWS YOU PURCHASE ITEMS FROM THE VENDING MACHINE.
				 */

			} else if (selection == 2) {
				purchase();
				System.out.println();

				/*
				 * OPTION THREE EXITS THE VENDING MACHINE PROGRAM. THIS METHOD IS PASSWORD
				 * PROTECTED SO ONLY VENDING MACHINE COMPANY EMPLOYEES CAN ACCESS IT.
				 * UNFORTUNATELY, THE PASSWORD IS 'password' SO IT IS NOT AS SECURE AS IT COULD
				 * BE. EXITING THE VENDING MACHINE ENDS THE PROGRAM AND CREATES THE SALES REPORT
				 * FILE.
				 */

			} else if (selection == 3) {
				System.out.println("Please Enter your username");
				String username = input.nextLine();
				System.out.println("Please Enter the password");
				String password = input.nextLine();
				if (password.equals("password")) {
					restockMachine();
					Date date = new Date();
					try {
						FileWriter fw = new FileWriter("log.txt", true);
						PrintWriter appendWriter = new PrintWriter(fw);
						appendWriter.print(date.toGMTString() + "\t\t");
						appendWriter.print("Restocked by " + username);
						appendWriter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					stop = true;
				} else {
					System.out.println("The password you have entered is incorrect");
				}
			}

		}
			catch(Exception e) {
				}
			}

	}

	/*
	 * THIS METHOD CAN BE CALLED BY PRESSING ONE ON THE MAIN MENU. IT ITERATES
	 * THROUGH EVERY ITEM IN THE MACHINE AND PRINTS THE INFORMATION TO THE CONSOLE.
	 */

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

	/*
	 * PURCHASE IS CALLED FROM THE FABLED 'MAIN MENU,' BUT IT IS IT'S OWN WHOLE
	 * MENU. WOW. THE PURCHASE MENU CONTAINS EVERYTHING YOU WOULD NEED TO PURCHASE
	 * ITEMS FROM THE VENDING MACHINE. THIS MENU ALSO LOOPS. YOU WILL NEED TO EXIT
	 * IT MANUALLY. YOU COULD THINK OF THIS AS 'CHECKING OUT.' EVERY SUCCESSFUL
	 * SELECTION IN THE PURCHASE MENU IS LOGGED BY THE LOG CALLED 'LOG.'
	 */

	public static void purchase() {
		double balance = 0.00;
		List<Consumable> purchasedItems = new ArrayList<>();
		boolean stop = false;
		while (!stop) {
			System.out.printf(
					"(1) Feed Money\n(2) Select Product\n(3) Finish Transaction\nCurrent Money Provided: $%.2f\n",
					balance);
			/*
			 * THE FIRST OPTION LET'S YOU ADD MONEY TO THE VENDING MACHINE. OBVIOUSLY YOU
			 * NEED TO ADD MONEY BEFORE YOU CAN COMPLETE A PURCHASE.
			 */
			try {int selection = Integer.parseInt(input.nextLine());
			if (selection == 1) {
				balance = feedMoney(balance);
				System.out.printf("Your balance is: $%.2f\n", balance);
			}

			/*
			 * THE SECOND OPTION LETS YOU PUT THE PRODUCT CODE IN THE MACHINE TO PURCHASE
			 * THE ITEM. THE PURCHASE FUNCTION CHECKS THAT THE PRODUCT IS IN STOCK AND THAT
			 * YOU HAVE ENOUGH MONEY TO MAKE THE PURCHASE. IF YOUR PURCHASE IS SUCCESSFUL IT
			 * ADDS YOUR PURCHASE TO THE LIST OF ITEMS YOU HAVE ALREADY PURCHASED.
			 */

			else if (selection == 2) {
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

				/*
				 * OPTION THREE IS BASICALLY 'CHECKING OUT.' THE MACHINE RETURNS YOUR CHANGE,
				 * ONE COIN AT A TIME. IT PRINTS OUT THE REQUISITE MESSAGES AND RESETS THE
				 * BALANCE AND PURCHASE LIST. THEN IT EXITS OUT OF THE PURCHASE MENU BACK TO THE
				 * FABLED 'MAIN MENU.'
				 */

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
			catch(Exception e) {
				}
			}

	}

	/*
	 * THIS IS HOW USERS ADD MONEY TO THE MACHINE. THE MACHINE ASKS THEM TO INSERT
	 * MONEY. THE MONEY IS PROCESSED AS AN INTEGER SO GET THAT SPARE CHANGE OUT OF
	 * HERE. IF YOU TRY TO ENTER SOMETHING THAT ISN'T AN INTEGER, I'M NOT HAVING ANY
	 * OF IT. IT'S AS GOOD AS ZERO TO ME AND I WILL IGNORE IT COMPLETELY. IF YOU
	 * TYPE IN AN INTEGER LIKE A GOOD CHILD THE MACHINE ADDS IT TO YOUR BALANCE AND
	 * SANTA CLAUS WILL BRING YOU MANY GIFTS.
	 */

	public static double feedMoney(double balance) {

		System.out.println("How much money would you like to insert?");

		double newBalance;

		int moneyIn;

		String stringInput = input.nextLine();

		try {
			moneyIn = Integer.parseInt(stringInput);
		}

		catch (Exception e) {
			moneyIn = 0;
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

	/*
	 * THIS METHOD UPDATES THE BALANCE TO REFLECT A PURCHASE.
	 */

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

	/*
	 * THIS METHOD GETS THE REQUISITE SILLY MESSAGE FOR EACH PURCHASE. THE STRING IT
	 * RETURNS DEPENDS ON WHICH SUBCLASS OF ITEM WAS PURCHASED.
	 */

	static String finishTransaction(List<Consumable> purchasedItems) {
		String message = "";
		for (Consumable item : purchasedItems) {
			message = message + "\n" + item.getMessage();
		}

		return message;
	}

	/*
	 * THIS METHOD CREATES A LIST OF COINS TO BE RETURNED. THE COINS ARE RETURNED IN
	 * THE LIST AND THEN PRINTED INDIVIDUALLY.
	 */

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

	/*
	 * THIS METHOD ENDS IT ALL! AT LEAST UNTIL THE PROGRAM IS RUN AGAIN. IT CREATES
	 * THE FINAL SALES REPORT AND ENDS THE PROGRAM.
	 */

	public static void restockMachine() {

		File salesReport = new File("salesreport.txt");
		LinkedHashMap<String, Integer> salesReportMap = new LinkedHashMap<>();
		double totalSales = 0;

		/*
		 * FIRST THINGS FIRST WE MAKE A MAP FOR THE SALES REPORT. WE START BY ITERATING
		 * THROUGH OUR INITIAL ITEM MAP TO LIST EVERYTHING THAT WAS PUT IN THE MACHINE
		 * WHEN THE PROGRAM WAS LAUNCHED. THE NUMBER SOLD FOR ALL OF THESE ITEMS IS
		 * ZERO, BECAUSE INITIALLY, NONE HAD BEEN SOLD. WE WILL ACCOUNT FOR OUR SALES
		 * LATER.
		 */

		{
			Set<String> keys = itemMap.keySet();
			for (String key : keys) {
				Consumable item = itemMap.get(key);
				salesReportMap.put(item.getProduct(), 0);
			}
		}

		/*
		 * IF THE SALES REPORT ALREADY EXISTS, WE HAVE TO READ THROUGH IT AND PRESERVE
		 * IT IN ADDITION TO ADDING OUR OWN SALES. WE USE THE SPLIT FUNCTION JUST LIKE
		 * WE DID EARLIER TO BREAK EACH LINE INTO ITS OWN ARRAY. WE WILL USE EACH ARRAY
		 * TO ADD AN ITEM TO THE SALES MAP. BECAUSE MAP KEYS ARE UNIQUE, IF A PRODUCT IN
		 * THE INITIAL SALES REPORT HAS THE SAME NAME AS A PRODUCT IN OUR VENDING
		 * MACHINE, THE INITIAL VALUE IS ERASED AND REPLACED WITH THE VALUE FROM THE
		 * SALES REPORT. THIS NEW MAP WILL CONTAIN EVERY PRODUCT IN BOTH THE INITIAL
		 * SALES MAP AND THE VENDING MACHINE. THIS IS COMPLICATED, BUT DOING THIS MEANS
		 * THAT OUR PROGRAM CAN HANDLE CHANGES TO THE VENDING MACHINE INVENTORY. WE ALSO
		 * EXTRACT THE TOTAL SALES HERE SO THAT WE CAN UPDATE THAT WITH OUR SALES.
		 */

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

		/*
		 * IF THE SALES REPORT DOES NOT ALREADY EXIST WE HAVE TO MAKE IT. SIMPLE.
		 */

		if (!salesReport.exists()) {
			try {
				salesReport.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{

			/*
			 * WE ITERATE THROUGH OUR MAP OF ITEMS. EVERY ITEM IN THE VENDING MACHINE HAS
			 * ITS SALES TOTAL UPDATED. WE DERIVE THE TOTAL SOLD IN OUR MACHINE FROM THE
			 * TOTAL NUMBER OF ITEMS REMAINING IN OUR MACHINE. WE ALSO ADD THESE SALES TO
			 * OUR TOTAL SALES.
			 */

			Set<String> keys = itemMap.keySet();
			for (String key : keys) {
				Consumable item = itemMap.get(key);
				int initial = salesReportMap.get(item.getProduct());
				int result = (5 - item.getNumberOfItems()) + initial;
				totalSales += (5 - item.getNumberOfItems()) * item.getPrice();
				salesReportMap.put(item.getProduct(), result);
			}
		}

		/*
		 * FINALLY WE PRINT OUR BRAND NEW SALES REPORT TO THE FILE. AND WE ARE ALL DONE
		 * UNTIL NEXT TIME.
		 */

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
