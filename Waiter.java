package Waiter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import Shared.ADT.ExampleOrders;
import Shared.ADT.Order;
import Shared.ADT.TableOrder;


public class Waiter {
	
	/**
	 * This .java file handles interface between the GUI and the Waiter
	 * author Samuel Baysting
	 **/
	
	public LinkedList<TableOrder> table;
	public LinkedList<String> names;
	public LinkedList<Float> prices;
	public LinkedList<Integer> TABLE_ID;
	public LinkedList<Integer> seatNumber;
	public LinkedList<Integer> quantity;
	
	/**
	 * This function runs the constructor for the waiter class
	 * @returns none
	 **/
	
	public Waiter(){
		
		retrieveCurrentTables();
	}
	
	/**
	 * This function retrieves the most current table order information from the database
	 * @returns none
	 **/
	
	public void retrieveCurrentTables()
	{
		ExampleOrders e = new ExampleOrders();
		table = new LinkedList<TableOrder>();
		names = new LinkedList<String>();
		prices = new LinkedList<Float>();
		TABLE_ID = new LinkedList<Integer>();
		seatNumber = new LinkedList<Integer>();
		quantity = new LinkedList<Integer>();
		table.add(e.table1);
		table.add(e.table2);
		table.add(e.table3);
		table.add(e.table4);
		table.add(e.table5);
		Iterator iterator = table.iterator();
		Queue<Order> q;
		TableOrder t;
		while(iterator.hasNext()){
			t = (TableOrder)iterator.next();
			q = t.FullTableOrder;
			Iterator<Order> iter = q.iterator();
			while(iter.hasNext()){
				Order o = (Order)iter.next();
				names.add(o.item.STRING_ID);
				prices.add(o.item.PRICE);
				TABLE_ID.add(t.TABLE_ID);
				seatNumber.add(o.seatNumber);
				quantity.add(o.Quantity);
			}
		}
		
	}
	
	/**
	 * This function takes the information retrieved by the retrieveCurrentTables() function
	 * and organizes it in a way that the Waiter GUI can understand
	 * @returns none
	 **/
	
	public void updateChanges()
	{
		names = new LinkedList<String>();
		prices = new LinkedList<Float>();
		TABLE_ID = new LinkedList<Integer>();
		seatNumber = new LinkedList<Integer>();
		quantity = new LinkedList<Integer>();
		Iterator iterator = table.iterator();
		Queue<Order> q;
		TableOrder t;
		while(iterator.hasNext()){
			t = (TableOrder)iterator.next();
			q = t.FullTableOrder;
			Iterator<Order> iter = q.iterator();
			while(iter.hasNext()){
				Order o = (Order)iter.next();
				names.add(o.item.STRING_ID);
				prices.add(o.item.PRICE);
				TABLE_ID.add(t.TABLE_ID);
				seatNumber.add(o.seatNumber);
				quantity.add(o.Quantity);
			}
			
			//This will reflect the changes in the names, prices, TABLE_ID, seatNumber and quantity lists
			//Update database - needs implementation
		}
	}
	
	/**
	 * This function was used for debugging only
	 * @returns none
	 **/
	
	public void print()
	{
		System.out.println("TABLE IDs:");
		Iterator i1 = TABLE_ID.iterator();
		while(i1.hasNext()){
			System.out.println(i1.next());
		}
		System.out.println("STRING IDs:");
		Iterator i2 = names.iterator();
		while(i2.hasNext()){
			System.out.println(i2.next());
		}
		System.out.println("PRICES:");
		Iterator i3 = prices.iterator();
		while(i3.hasNext()){
			System.out.println(i3.next());
		}
		System.out.println("SEAT NUMBERS:");
		Iterator i4 = seatNumber.iterator();
		while(i4.hasNext()){
			System.out.println(i4.next());
		}
	}
	
	/**
	 * Not yet implemented
	 * @returns false
	 **/
	
	public boolean viewOrderQueue(int tableNumber)
	{
		
		return false;
	}
	
	/**
	 * Implemented temporarily within the GUI
	 * @returns false
	 **/
	
	public boolean acceptPayment(float amount)
	{	
		
		return true;
	}
}
