package Interface;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import ADT.*;


public class Waiter {

	private LinkedList<Table> tables;
	
	public Waiter(){
		
		retrieveCurrentTables();
	}
	
	public void retrieveCurrentTables()
	{
		tables = new LinkedList<Table>();
		// Will retrieve assigned tables from database
		Table table1 = new Table(1,3); //new Table(tableNumber,numOfCustomers)
		Table table2 = new Table(2,2);
		Table table3 = new Table(3,3);
		Table table4 = new Table(4,1);
		Table table5 = new Table(5,4);
		table1.addOrder(new OrderItem(1,new MenuItem("Chicken"))); //table.addOrder(OrderItem(seatNumber,MenuItem(String)));
		table1.addOrder(new OrderItem(1,new MenuItem("Pasta")));
		table1.addOrder(new OrderItem(2,new MenuItem("Chicken")));
		table1.addOrder(new OrderItem(3,new MenuItem("Canoli")));
		table2.addOrder(new OrderItem(1,new MenuItem("Pasta")));
		table2.addOrder(new OrderItem(2,new MenuItem("Chicken")));
		table2.addOrder(new OrderItem(2,new MenuItem("Canoli")));
		table3.addOrder(new OrderItem(1,new MenuItem("Pasta")));
		table3.addOrder(new OrderItem(3,new MenuItem("Sausage")));
		table3.addOrder(new OrderItem(3,new MenuItem("Canoli")));
		table4.addOrder(new OrderItem(1,new MenuItem("Pasta")));
		table4.addOrder(new OrderItem(1,new MenuItem("Chicken")));
		table5.addOrder(new OrderItem(1,new MenuItem("Pasta")));
		table5.addOrder(new OrderItem(4,new MenuItem("Sandwich")));
		table5.addOrder(new OrderItem(3,new MenuItem("Canoli")));
		tables.add(table1);
		tables.add(table2);
		tables.add(table3);
		tables.add(table4);
		tables.add(table5);	
		
	}
	
	public boolean View_Order_Queue(int tableNumber)
	{
		retrieveCurrentTables();
		Table temp = null;
		Iterator iterator = tables.iterator();
		for(int i = 0;i < tables.size();i++){
			temp = (Table)iterator.next();
			if(temp.getTableNumber() == tableNumber){
				break;
			}
		}
		if(temp != null){
			temp.getOrderList();
			
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean Accept_Payment(float amount)
	{	
		
		return true;
	}
}
