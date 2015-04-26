package Waiter;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.JOptionPane;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class WaiterHandler {
	
	/**
	 * This class facilitates interaction between the Waiter GUI
	 * and the Waiter communicator
	 * 
	 * @author Samuel Baysting
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
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
	 * 
	 * @returns none
	 * 
	 **/
	
	public WaiterHandler(){
		
		comm = new WaiterCommunicator();
		comm.connect("admin","gradMay17");
		if(comm.getConnectionStatus()==0){
			assignedTables = comm.getAssignedTables();
		}
		else{
			JOptionPane.showMessageDialog(null, "Warning: No Database Connection","Connection", JOptionPane.WARNING_MESSAGE);
		}
		
	}
	
	/**
	 * Disconnects active connections to DB
	 * 
	 * @return void
	 * 
	 */
	
	public void disconnect()
	{
		comm.disconnect();
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
	 * This function will set up the table order data within this class for other classes
	 * to use based on the parameter given
	 * 
	 * @param tableNumber
	 * @return 0 - successful update
	 * @return 1 - failed update
	 * 
	 */
	
	@SuppressWarnings({ })
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
			
			System.out.println(currentOrderBalance);
			currentOrderBalance = Float.parseFloat(new String(""+((Math.round(currentOrderBalance*100)))))/100;
			System.out.println(currentOrderBalance);
			
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
	
	/**
	 * This function checks for possible errors and then requests a payment to be
	 * sent to the Database
	 * 
	 * @param tableNumber
	 * @param payment
	 * @param cashOrCard - "Cash" or "Card"
	 * @return 0 - successful
	 * @return 1 - failed
	 * 
	 */
	
	public int makePayment(int tableNumber, float payment, String cashOrCard)
	{
		getInfoForTable(tableNumber);
		//If payment < 0.01
		if(payment < 0.01){
			JOptionPane.showMessageDialog(null, "Payment must be greater than 0.01!","Payment", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		//If payment > balance
		if(payment > currentOrderBalance){
			JOptionPane.showMessageDialog(null, "Payment must be smaller than the order balance!","Payment", JOptionPane.ERROR_MESSAGE);
			return 1;
		}
		int temp = comm.pushPayment(tableNumber, payment, cashOrCard);
		comm.getTableOrders(tableNumber);
		getInfoForTable(tableNumber);
		return temp;
	}
	
	/**
	 * Returns the 2-dimensional data vector used with the Accept Payment JTable
	 * 
	 * @param tableNumber
	 * @return Vector<Vector>
	 * 
	 */
	
	@SuppressWarnings({ })
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
	 * Returns the 2-dimensional data vector used with the Manage Order Queue JTable
	 * 
	 * @param tableNumber
	 * @return Vector<Vector>
	 * 
	 */
	
	@SuppressWarnings({ })
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
			v.add(false);//v.add(new JCheckBox());
			manageOrderQueueDataVector.add(v);
			v = new Vector();
		}
		
		Collections.sort(manageOrderQueueDataVector,new SortBySeatNumber()); //Add space between seat numbers
		for(int i = 0;i < manageOrderQueueDataVector.size()-1;i++){
			int cur = i;
			int next = i+1;
			if((Integer)manageOrderQueueDataVector.get(cur).get(0) < (Integer)manageOrderQueueDataVector.get(next).get(0)){
				Vector blank = new Vector();
				blank.add("");blank.add("");blank.add("");blank.add("");blank.add("");blank.add(false);
				manageOrderQueueDataVector.insertElementAt(blank, next);
				i++;
			}
		}
		
		if(payments.size() != 0){ //Add payments
			v = new Vector();
			v.add("");v.add("");v.add("");v.add("");v.add("");v.add(false);
			manageOrderQueueDataVector.add(v);
			v = new Vector();
			i1 = payments.iterator();
			i2 = paymentTypes.iterator();
			while(i1.hasNext()){
				v.add("");v.add(i2.next());v.add("");v.add("-"+df.format(i1.next()));v.add("");v.add(false);
				manageOrderQueueDataVector.add(v);
				v = new Vector();
			}
		}
		
		v.add("");v.add("");v.add("");v.add("");v.add("");v.add(false); //Add balance at the bottom of the list
		manageOrderQueueDataVector.add(v);
		v = new Vector();
		v.add("");v.add("BALANCE");v.add("");v.add(df.format(currentOrderBalance));v.add("");v.add(false);
		manageOrderQueueDataVector.add(v);
		v = new Vector();
		v.add("");v.add("");v.add("");v.add("");v.add("");v.add(false);
		manageOrderQueueDataVector.add(v);
		
		while(manageOrderQueueDataVector.size()<16){ //For formatting purposes
			v = new Vector<String>();
			v.add("");
			v.add("");
			v.add("");
			v.add("");
			v.add("");
			v.add(false);
			manageOrderQueueDataVector.add(v);
		}
		return manageOrderQueueDataVector;
	}
	
	/**
	 * Returns the 2-dimensional data vector used with the Request Refund JTable
	 * 
	 * @param tableNumber
	 * @return Vector<Vector>
	 * 
	 */
	
	@SuppressWarnings({ })
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
			v.add(false);
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
	
	/**
	 * This function takes a string table name and converts it to an integer
	 * 
	 * @param tableName
	 * @return tableID
	 * 
	 */
	
	public int parseTableName(String tableName)
	{
		if(tableName == ""){
			return 0;
		}
		tableName = tableName.replaceAll("\\D+","");
		return Integer.parseInt(tableName);
	}
	
	/**
	 * This function requests a status change for an item in the database
	 * 
	 * @param data
	 * @param tableID
	 * @param newStatus
	 * @return 0 - successful
	 * @return 1 - failed
	 * 
	 */
	
	public int changeItemStatus(Vector<Vector> data, Integer tableID,String newStatus)
	{
		for(int i = 0;i < data.size();i++){
			comm.changeItemStatus(data.get(i),tableID,newStatus);
		}
		
		return 0;
	}
	
}

/**
 * Class used for comparison of two vectors
 * 
 * @author Samuel Baysting
 * @tester Samuel Baysting
 * @debugger Samuel Baysting
 *
 */

@SuppressWarnings("rawtypes")
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
