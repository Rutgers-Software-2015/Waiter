package Waiter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import Shared.ADT.ExampleOrders;
import Shared.ADT.Order;
import Shared.ADT.TableOrder;


public class WaiterHandler {
	
	/**
	 * This .java file handles interface between the GUI and the Waiter
	 * 
	 * @author Samuel Baysting
	 * 
	 **/
	
	private LinkedList<Integer> assignedTables = new LinkedList<Integer>();
	private HashMap<String,LinkedList> tableOrder = new HashMap<String,LinkedList>();
	
	public LinkedList<String> items = new LinkedList<String>();
	public LinkedList<Float> prices = new LinkedList<Float>();
	public LinkedList<Integer> seatNumbers = new LinkedList<Integer>();
	public LinkedList<Integer> quantities = new LinkedList<Integer>();
	public LinkedList<String> currentStatus = new LinkedList<String>();
	public LinkedList<Float> payments = new LinkedList<Float>();
	public LinkedList<String> paymentTypes = new LinkedList<String>();
	public Float currentOrderBalance = (float)0;
	
	private WaiterCommunicator comm = null;
	
	/**
	 * This function runs the constructor for the waiter class
	 * @returns none
	 **/
	
	public WaiterHandler(){
		
		comm = new WaiterCommunicator();
		comm.connect("admin","gradMay17");
		assignedTables = comm.getAssignedTables();
		
	}
	
	/**
	 * This function will access the communicator and retrieve all tables
	 * assigned to the current waiter
	 * 
	 * @return LinkedList<Integer> of table numbers
	 * 
	 */
	
	public LinkedList<Integer> getAssignedTables()
	{
		assignedTables = new LinkedList<Integer>();
		assignedTables = comm.getAssignedTables();
		return assignedTables;
	}
	
	/**
	 * This function will set up the data within this class for other classes
	 * to use based on the parameter given
	 * 
	 * @param tableNumber
	 * @return 0 - successful update
	 * @return 1 - failed update
	 * 
	 */
	
	public int getInfoForTable(int tableNumber)
	{
		currentOrderBalance = (float)0;
		
		tableOrder = comm.getTableOrders(tableNumber);
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		if(tableOrder != null){
			
			items = tableOrder.get("Orders");
			prices = tableOrder.get("Prices");
			seatNumbers = tableOrder.get("Seat Numbers");
			quantities = tableOrder.get("Quantities");
			currentStatus = tableOrder.get("Current Statuses");
			payments = tableOrder.get("Payments");
			paymentTypes = tableOrder.get("Payment Types");
			
			Iterator i = prices.iterator();
			while(i.hasNext()){
				currentOrderBalance += (Float)i.next();
			}
			
			i = payments.iterator();
			while(i.hasNext()){
				currentOrderBalance -= (Float)i.next();
			}
			
			return 0;
		}
		else{
			return 1;
		}
	}
	
	/**
	 * Gets the most recent table names assigned to the current waiter
	 * using the communicator
	 * 
	 * @return Vector<String> of table names
	 * 
	 */

	public Vector<String> assignedTableNames() {
		
		Vector<String> names = new Vector<String>();
		names.add("");
		Iterator i = assignedTables.iterator();
		
		while(i.hasNext()){
			names.add("Table " + i.next());
		}
		
		return names;
	}
	
	public int makePayment(int tableNumber, float payment, String cashOrCard)
	{
		int temp = comm.pushPayment(tableNumber, payment, cashOrCard);
		comm.getTableOrders(tableNumber);
		return temp;
	}
	
	/**
	 * 
	 * @param tableNumber
	 * @return
	 */
	
	public Vector<Vector> getAcceptPaymentDataVector(int tableNumber)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		if(tableNumber == 0){
			return new Vector<Vector>();
		}
		if(getInfoForTable(tableNumber)==1){
			return null;
		}
		
		Vector<Vector> acceptPaymentDataVector = new Vector<Vector>();
		/* Contains:
		 * - Seat Number
		 * - Menu Item
		 * - Quantity
		 * - Price
		 */
		Vector v = new Vector();
		Iterator i1 = seatNumbers.iterator();
		Iterator i2 = items.iterator();
		Iterator i3 = quantities.iterator();
		Iterator i4 = prices.iterator();
		
		while(i1.hasNext()){
			v.add(i1.next());
			v.add(i2.next());
			v.add(i3.next());
			v.add(df.format((Float)i4.next()));
			acceptPaymentDataVector.add(v);
			v = new Vector();
		}
		
		Collections.sort(acceptPaymentDataVector,new SortBySeatNumber()); //Add space between seat numbers
		for(int i = 0;i < acceptPaymentDataVector.size()-1;i++){
			int cur = i;
			int next = i+1;
			if((Integer)acceptPaymentDataVector.get(cur).get(0) < (Integer)acceptPaymentDataVector.get(next).get(0)){
				Vector blank = new Vector();
				blank.add("");blank.add("");blank.add("");blank.add("");
				acceptPaymentDataVector.insertElementAt(blank, next);
				i++;
			}
		}
		
		if(payments.size() != 0){ //Add payments
			v = new Vector();
			v.add("");v.add("");v.add("");v.add("");
			acceptPaymentDataVector.add(v);
			v = new Vector();
			i1 = payments.iterator();
			i2 = paymentTypes.iterator();
			while(i1.hasNext()){
				v.add("");v.add(i2.next());v.add("");v.add("-"+df.format(i1.next()));
				acceptPaymentDataVector.add(v);
				v = new Vector();
			}
		}
		
		v.add("");v.add("");v.add("");v.add(""); //Add balance at the bottom of the list
		acceptPaymentDataVector.add(v);
		v = new Vector();
		v.add("");v.add("BALANCE");v.add("");v.add(df.format(currentOrderBalance));
		acceptPaymentDataVector.add(v);
		v = new Vector();
		v.add("");v.add("");v.add("");v.add("");
		acceptPaymentDataVector.add(v);
		
		
		while(acceptPaymentDataVector.size()<11){ //For formatting purposes
			v = new Vector<String>();
			v.add("");
			v.add("");
			v.add("");
			v.add("");
			acceptPaymentDataVector.add(v);
		}
		return acceptPaymentDataVector;
	}
	
	/**
	 * 
	 * @param tableNumber
	 * @return
	 */
	
	public Vector<Vector> getManageOrderQueueDataVector(int tableNumber)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);
		
		if(tableNumber == 0){
			return new Vector<Vector>();
		}
		if(getInfoForTable(tableNumber)==1){
			return null;
		}
		
		Vector<Vector> manageOrderQueueDataVector = new Vector<Vector>();
		/* Contains:
		 * - Seat Number
		 * - Menu Item
		 * - Quantity
		 * - Price
		 * - Current Status
		 * - JCheckBox
		 */
		Vector v = new Vector();
		Iterator i1 = seatNumbers.iterator();
		Iterator i2 = items.iterator();
		Iterator i3 = quantities.iterator();
		Iterator i4 = prices.iterator();
		Iterator i5 = currentStatus.iterator();
		
		while(i1.hasNext()){
			v.add(i1.next());
			v.add(i2.next());
			v.add(i3.next());
			v.add(df.format((Float)i4.next()));
			v.add(i5.next());
			v.add(new JCheckBox());
			manageOrderQueueDataVector.add(v);
			v = new Vector();
		}
		
		Collections.sort(manageOrderQueueDataVector,new SortBySeatNumber()); //Add space between seat numbers
		for(int i = 0;i < manageOrderQueueDataVector.size()-1;i++){
			int cur = i;
			int next = i+1;
			if((Integer)manageOrderQueueDataVector.get(cur).get(0) < (Integer)manageOrderQueueDataVector.get(next).get(0)){
				Vector blank = new Vector();
				blank.add("");blank.add("");blank.add("");blank.add("");
				manageOrderQueueDataVector.insertElementAt(blank, next);
				i++;
			}
		}
		
		if(payments.size() != 0){ //Add payments
			v = new Vector();
			v.add("");v.add("");v.add("");v.add("");
			manageOrderQueueDataVector.add(v);
			v = new Vector();
			i1 = payments.iterator();
			i2 = paymentTypes.iterator();
			while(i1.hasNext()){
				v.add("");v.add(i2.next());v.add("");v.add("-"+df.format(i1.next()));
				manageOrderQueueDataVector.add(v);
				v = new Vector();
			}
		}
		
		v.add("");v.add("");v.add("");v.add(""); //Add balance at the bottom of the list
		manageOrderQueueDataVector.add(v);
		v = new Vector();
		v.add("");v.add("BALANCE");v.add("");v.add(df.format(currentOrderBalance));
		manageOrderQueueDataVector.add(v);
		v = new Vector();
		v.add("");v.add("");v.add("");v.add("");
		manageOrderQueueDataVector.add(v);
		
		while(manageOrderQueueDataVector.size()<16){ //For formatting purposes
			v = new Vector<String>();
			v.add("");
			v.add("");
			v.add("");
			v.add("");
			manageOrderQueueDataVector.add(v);
		}
		return manageOrderQueueDataVector;
	}
	
	/**
	 * 
	 * @param tableNumber
	 * @return
	 */
	
	public Vector<Vector> getRefundTableDataVector(int tableNumber)
	{
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		df.setMinimumFractionDigits(2);

		if(tableNumber == 0){
			return new Vector<Vector>();
		}
		if(getInfoForTable(tableNumber)==1){
			return null;
		}
		
		Vector<Vector> refundTableDataVector = new Vector<Vector>();
		/* Contains:
		 * - Seat Number
		 * - Menu Item
		 * - Quantity
		 * - Price
		 * - JCheckBox
		 */
		Vector v = new Vector();
		Iterator i1 = seatNumbers.iterator();
		Iterator i2 = items.iterator();
		Iterator i3 = quantities.iterator();
		Iterator i4 = prices.iterator();
		
		while(i1.hasNext()){
			v.add(i1.next());
			v.add(i2.next());
			v.add(i3.next());
			v.add(df.format((Float)i4.next()));
			v.add(new JCheckBox());
			refundTableDataVector.add(v);
			v = new Vector();
		}
		
		Collections.sort(refundTableDataVector,new SortBySeatNumber()); //Add space between seat numbers
		for(int i = 0;i < refundTableDataVector.size()-1;i++){
			int cur = i;
			int next = i+1;
			if((Integer)refundTableDataVector.get(cur).get(0) < (Integer)refundTableDataVector.get(next).get(0)){
				Vector blank = new Vector();
				blank.add("");blank.add("");blank.add("");blank.add("");
				refundTableDataVector.insertElementAt(blank, next);
				i++;
			}
		}
		
		if(payments.size() != 0){ //Add payments
			v = new Vector();
			v.add("");v.add("");v.add("");v.add("");
			refundTableDataVector.add(v);
			v = new Vector();
			i1 = payments.iterator();
			i2 = paymentTypes.iterator();
			while(i1.hasNext()){
				v.add("");v.add(i2.next());v.add("");v.add("-"+df.format(i1.next()));
				refundTableDataVector.add(v);
				v = new Vector();
			}
		}
		
		v.add("");v.add("");v.add("");v.add(""); //Add balance at the bottom of the list
		refundTableDataVector.add(v);
		v = new Vector();
		v.add("");v.add("BALANCE");v.add("");v.add(df.format(currentOrderBalance));
		refundTableDataVector.add(v);
		v = new Vector();
		v.add("");v.add("");v.add("");v.add("");
		refundTableDataVector.add(v);
		
		while(refundTableDataVector.size()<16){ //For formatting purposes
			v = new Vector<String>();
			v.add("");
			v.add("");
			v.add("");
			v.add("");
			refundTableDataVector.add(v);
		}
		return refundTableDataVector;
	}
	
	public int parseTableName(String tableName)
	{
		if(tableName == ""){
			return 0;
		}
		tableName = tableName.replaceAll("\\D+","");
		return Integer.parseInt(tableName);
	}
	
	
	/*
	 * 
	 * 	
		 * This function gets all of the tables assigned to the signed-in waiter
		 * @returns a Vector of the tables
		 *
		
		private Vector<String> getAssignedTables()
		{
			Vector<String> v = new Vector<String>();
			v.add("");
			Iterator i = waiter.table.iterator();
			while(i.hasNext()){
				v.add("Table " + ((TableOrder)i.next()).TABLE_ID);
			}
			Collections.sort(v);
			return v;
		}
		
		
		 * This function will retrieve all order data from the server.
		 * This function currently receives information from a fake database.
		 * Function also organizes the information
		 * @returns none
		 
		 
		private void retrieveOrderData(){
			//Find correct table
			balance = 0;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			int table_id = 0;//returnTableID();
			Iterator i2 = waiter.names.iterator();
			Iterator i3 = waiter.prices.iterator();
			Iterator i4 = waiter.seatNumber.iterator();
			Iterator i5 = waiter.TABLE_ID.iterator();
			Iterator i6 = waiter.quantity.iterator();
			int max = 0;
			while(i4.hasNext()){
				int temp = (Integer) i4.next();
				int temp2 = (Integer) i5.next();
				if(temp > max && temp2 == table_id){
					max = temp;
				}
			}
			i4 = waiter.seatNumber.iterator();
			i5 = waiter.TABLE_ID.iterator();
			LinkedList<String> nameList = new LinkedList<String>();
			LinkedList<String> priceList = new LinkedList<String>();
			LinkedList<String> seatList = new LinkedList<String>();
			LinkedList<String> quantityList = new LinkedList<String>();
			for(int n = 1;n <= max;n++){ // Get prices, names, quantities and seat numbers
				i2 = waiter.names.iterator();
				i3 = waiter.prices.iterator();
				i4 = waiter.seatNumber.iterator();
				i5 = waiter.TABLE_ID.iterator();
				i6 = waiter.quantity.iterator();
				boolean flag = false;
				while(i2.hasNext()){
					String tempname = (String)i2.next();
					Float tempprice = (Float)i3.next();
					Integer tempseat = (Integer) i4.next();
					Integer tempid = (Integer) i5.next();
					Integer tempquan = (Integer) i6.next();
					if(tempid == table_id && tempseat == n && flag == true){
						nameList.add(tempname);
						priceList.add(""+ tempprice*tempquan);
						seatList.add("");
						quantityList.add(""+ tempquan);
						balance = balance + tempprice*tempquan;
					}
					if(tempid == table_id && tempseat == n && flag == false){
						nameList.add(tempname);
						priceList.add(""+ df.format(tempprice*tempquan));
						seatList.add("Seat "+n+":");
						quantityList.add(""+ tempquan);
						balance = balance + tempprice*tempquan;
						flag = true;
					}
				}
				nameList.add("");
				priceList.add("");
				seatList.add("");
				quantityList.add("");
			}
			i2 = waiter.names.iterator();
			i3 = waiter.prices.iterator();
			i4 = waiter.seatNumber.iterator();
			i5 = waiter.TABLE_ID.iterator();
			i6 = waiter.quantity.iterator();
			while(i2.hasNext()){ // Find any payments made
				String tempname = (String)i2.next();
				Float tempprice = (Float)i3.next();
				Integer tempseat = (Integer) i4.next();
				Integer tempid = (Integer) i5.next();
				Integer tempquan = (Integer) i6.next();
				if(tempid == table_id && tempseat == 0){
					nameList.add(tempname);
					priceList.add(""+ df.format(tempprice));
					seatList.add("");
					if(tempquan == 0){
						quantityList.add("Cash");
						}
					else{
						quantityList.add("Card");
					}
					balance = balance + tempprice;
				}
			}
			balance = Math.round(balance*100);
			balance = balance/100;
			if(!nameList.isEmpty() && balance > 0){ // Print latest balance
				quantityList.add("");
				quantityList.add("BALANCE: ");
				priceList.add("");
				priceList.add(""+df.format(balance));
				nameList.add("");
				nameList.add("");
				seatList.add("");
				seatList.add("");
				nameList.add("");
				priceList.add("");
				seatList.add("");
				quantityList.add("");
			}
			if(!nameList.isEmpty() && balance <= 0){
				quantityList.add("");
				quantityList.add("BALANCE: ");
				priceList.add("");
				priceList.add("0.00");
				nameList.add("");
				nameList.add("");
				seatList.add("");
				seatList.add("");
				nameList.add("");
				priceList.add("");
				seatList.add("");
				quantityList.add("");
			}
			this.seatList = seatList;
			this.nameList = nameList;
			this.quantityList = quantityList;
			this.priceList = priceList;
		}
		 
		**
		 * This function gets the most up-to-date table data from the server
		 * @returns none
		 **
		
		 private void refreshOrderTable()
		 {
			// Get most recent table data
			 retrieveOrderData();
			// Setup Table Data
			data = new Vector<Vector>();
			Iterator<String> i1 = seatList.iterator();
			Iterator<String> i2 = nameList.iterator();
			Iterator<String> i3 = quantityList.iterator();
			Iterator<String> i4 = priceList.iterator();
			while(i1.hasNext()){
				Vector<String> v = new Vector<String>();
				v.add((String)i1.next());
				v.add((String)i2.next());
				v.add((String)i3.next());
				v.add((String)i4.next());
				data.add(v);
			}
			tablemodel.setDataVector(data,columnnames);
			tablemodel.fireTableDataChanged();
			while(tablemodel.getRowCount()<11){ //For formatting purposes
				Vector<String> v = new Vector<String>();
				v.add("");
				v.add("");
				v.add("");
				v.add("");
				data.add(v);
				tablemodel.setDataVector(data,columnnames);
			}
			tablemodel.fireTableDataChanged();
		 }
		 
		 
	 */
	
}

class SortBySeatNumber implements Comparator<Vector>{
	 
    @Override
    public int compare(Vector e1, Vector e2) {
        if((Integer)e1.get(0) > (Integer)e2.get(0)){
            return 1;
        } else {
            return -1;
        }
    }
}
