package ADT;

import java.util.LinkedList;

public class Table {

		private LinkedList<OrderItem> order;
		private int tableNumber;
		private int numberOfCustomers;
		
		public Table(int num, int customers)
		{
			tableNumber = num;
			numberOfCustomers = customers;
		}
		
		public void setCustomers(int number)
		{
			numberOfCustomers = number;
		}
		
		public int getCustomers()
		{
			return numberOfCustomers;
		}
		
		public int getTableNumber()
		{
			return tableNumber;
		}
		
		public void addOrder(OrderItem item)
		{
			order.add(item);
		}
		
		public LinkedList<OrderItem> getOrderList()
		{
			return order;
		}
}
