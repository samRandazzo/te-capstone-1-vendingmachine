package com.techelevator.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

import com.techelevator.Candy;
import com.techelevator.Chip;
import com.techelevator.Coin;
import com.techelevator.Consumable;
import com.techelevator.Drink;
import com.techelevator.Gum;
import com.techelevator.view.Menu;

public class MenuTest {

	Menu test = null;

	@Before
	public void setup() {
		test = new Menu();
	}

	@After
	public void teardown() {

	}
	
	@Test
	public void can_you_add_a_dollar() {
		/*
		 * FOR THIS TEST TO PASS YOU MUST ENTER "1" INTO THE CONSOLE.
		 * IF YOU ENTER ANOTHER NUMBER, THE TEST SHOULD FAIL.
		 */
		double input = 0.00;
		double output = Menu.feedMoney(input);
		Assert.assertEquals(1.00, output, 0.05);
	}
	
	@Test
	public void can_you_add_another_dollar() {
		/*
		 * FOR THIS TEST TO PASS YOU MUST ENTER "1" INTO THE CONSOLE.
		 * IF YOU ENTER ANOTHER NUMBER, THE TEST SHOULD FAIL.
		 */
		double input = 1.00;
		double output = Menu.feedMoney(input);
		Assert.assertEquals(2.00, output, 0.05);
	}

	@Test
	public void purchase_drink() {
		Consumable testItem = new Drink("Miller Lite", 8.50, 5, "C5");
		double input = 10.00;
		double output = Menu.purchaseProduct(testItem, input);
		Assert.assertEquals(1.50, output, 0.05);
	}

	@Test
	public void purchase_gum() {
		Consumable testItem = new Gum("Big Red", 1.05, 5, "D5");
		double input = 10.00;
		double output = Menu.purchaseProduct(testItem, input);
		Assert.assertEquals(8.95, output, 0.05);
	}
	
	@Test
	public void purchase_candy() {
		Consumable testItem = new Candy("Payday", 2.25, 5, "B5");
		double input = 10.00;
		double output = Menu.purchaseProduct(testItem, input);
		Assert.assertEquals(7.75, output,0.05);
	}
	
	@Test
	public void purchase_chip() {
		Consumable testItem = new Chip("Rap Snacks", 4.20, 5, "A5");
		double input = 10.00;
		double output = Menu.purchaseProduct(testItem, input);
		Assert.assertEquals(5.80, output,0.05);
	}

	@Test
	public void finish_drink_transaction() {
		Consumable testItem = new Drink("Miller Lite", 8.50, 5, "D5");
		List<Consumable> testList = new ArrayList<>();
		testList.add(testItem);
		String output = Menu.finishTransaction(testList);
		Assert.assertEquals("\nGlug Glug, Yum!", output);
	}
	
	@Test
	public void finish_gum_transaction() {
		Consumable testItem = new Gum("Big Red", 1.05, 5, "D5");
		List<Consumable> testList = new ArrayList<>();
		testList.add(testItem);
		String output = Menu.finishTransaction(testList);
		Assert.assertEquals("\nChew Chew, Yum!", output);
	}
	
	@Test
	public void finish_candy_transaction() {
		Consumable testItem = new Candy("Payday", 2.25, 5, "B5");
		List<Consumable> testList = new ArrayList<>();
		testList.add(testItem);
		String output = Menu.finishTransaction(testList);
		Assert.assertEquals("\nMunch Munch, Yum!", output);
	}
	
	@Test
	public void finish_chip_transaction() {
		Consumable testItem = new Chip("Rap Snacks", 4.20, 5, "A5");
		List<Consumable> testList = new ArrayList<>();
		testList.add(testItem);
		String output = Menu.finishTransaction(testList);
		Assert.assertEquals("\nCrunch Crunch, Yum!", output);
	}

	@Test
	public void make_change() {
		double input = 0.40;
		List<Coin> makeChange = Menu.makeChange(input);
		Assert.assertEquals("Quarter", makeChange.get(0).getName());
		Assert.assertEquals("Dime", makeChange.get(1).getName());
		Assert.assertEquals("Nickel", makeChange.get(2).getName());
	}

}
