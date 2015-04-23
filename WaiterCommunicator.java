package Waiter;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

import Shared.Communicator.DatabaseCommunicator;

public class WaiterCommunicator extends DatabaseCommunicator{

	public WaiterCommunicator()
	{
		super();
		connect("admin","gradMay17");
	}
	
	public LinkedList<Integer> getAssignedTables()
	{
		LinkedList<Integer> list = new LinkedList<Integer>();
		
		try{
			
			ResultSet rs = tell("SELECT TABLE_ID FROM MAINDB.TABLE_ORDER");
			
			if(rs != null){
				
				rs.first();
				
				do{
					boolean flag = false;
					int temp = Integer.parseInt(rs.getString(1));
					for(int i = 0;i < list.size();i++){
						if(temp == list.get(i)){
							flag = true;
						}
					}
					if(flag == false){
						list.add(temp);
					}
					
				}while(rs.next());
				
			}
			else{
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace(System.out);
			return null;
		}
		
		Collections.sort(list);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	public HashMap<String,LinkedList> getTableOrders(int tableNumber)
	{
		//Hashmap Keys: Orders, Prices, Seat Numbers, Quantities, Current Status
		HashMap<String,LinkedList> tableOrder = new HashMap<String,LinkedList>();
		LinkedList<String> items = new LinkedList<String>();
		LinkedList<Float> prices = new LinkedList<Float>();
		LinkedList<Integer> seatNumbers = new LinkedList<Integer>();
		LinkedList<Integer> quantities = new LinkedList<Integer>();
		LinkedList<String> currentStatus = new LinkedList<String>();
		LinkedList<Float> payments = new LinkedList<Float>();
		LinkedList<String> paymentTypes = new LinkedList<String>();
		
		try{
				
			ResultSet rs = tell("SELECT ITEM_NAME,PRICE,QUANTITY,CURRENT_STATUS,SEAT_NUMBER FROM MAINDB.TABLE_ORDER "
					+ "WHERE TABLE_ID="+tableNumber);
			
			if(rs != null){
				
				rs.first();
				
				ResultSetMetaData rsd = rs.getMetaData();
				int colsize = rsd.getColumnCount();
		    
				do{
					//Column 1: ITEM_NAME (STRING)
					//Column 2: PRICE (FLOAT)
					//Column 3: QUANTITY (INT)
					//Column 4: CURRENT_STATUS (STRING)
			    	//Column 5: SEAT NUMBER (INT)
					if(rs.getString(5)!=null){
						items.add(rs.getString(1));
						prices.add(Float.parseFloat(rs.getString(2)));
						quantities.add(Integer.parseInt(rs.getString(3)));
						currentStatus.add(rs.getString(4));
						seatNumbers.add(Integer.parseInt(rs.getString(5)));
					}
					else{
						payments.add(Float.parseFloat(rs.getString(2)));
						paymentTypes.add(rs.getString(1));
					}
				}while(rs.next());
				
			}
			else{
				System.out.println("RS = NULL");
				return null;
			}
		
		}catch(Exception e){
			e.printStackTrace(System.out);
				System.out.println("EXCEPTION THROWN");
				return null;
		}
			
			tableOrder.put("Orders", items);
			tableOrder.put("Prices", prices);
			tableOrder.put("Seat Numbers",seatNumbers);
			tableOrder.put("Quantities",quantities);
			tableOrder.put("Current Statuses",currentStatus);
			tableOrder.put("Payments",payments);
			tableOrder.put("Payment Types",paymentTypes);
			
			return tableOrder;	
	}
	
	public int pushPayment(int tableNumber, float payment, String cashOrCard)
	{
		try{
			
			update("INSERT INTO MAINDB.TABLE_ORDER (ORDER_ID,TABLE_ID,ITEM_NAME,PRICE,QUANTITY,SPEC_INSTR,CURRENT_STATUS,MENU_ITEM_ID,"
					+ "EMPLOYEE_ID,SEAT_NUMBER) VALUES (0,"
					+ tableNumber
					+ ",\"Payment - "
					+ cashOrCard
					+ "\","
					+ payment
					+ ",1,\"\",null,null,null,null);");
			
			return 0;
			
		}catch(Exception e){
			e.printStackTrace(System.out);
			return 1;
		}
		
	}


}
