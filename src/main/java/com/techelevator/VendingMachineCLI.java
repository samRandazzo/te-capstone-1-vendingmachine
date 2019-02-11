package com.techelevator;

import com.techelevator.view.Menu;

public class VendingMachineCLI {

	public static void main(String[] args) {
		/*
		 * Stock Machine loads the input file and gets the machine ready to be used.
		 * Stock Machine runs automatically when the program starts.
		 */
		Menu.stockMachine();
		/*
		 * Main Menu runs immediately after Stock Machine is done.
		 * Main Menu loops so users can continually use the program.
		 * Main Menu can be manually exited.
		 * Exiting Main Menu ends the program and creates the sales report.
		 */
		Menu.mainMenu();
	}
}
