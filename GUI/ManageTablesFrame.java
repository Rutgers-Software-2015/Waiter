package Waiter.GUI;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Shared.ADT.Employee;
import Shared.ADT.Order;
import Shared.ADT.TableOrder;
import Shared.Gradients.GradientPanel;
import Waiter.WaiterMainGUI;
import Waiter.Interface.Waiter;



public class ManageTablesFrame extends JFrame implements ActionListener{

	/**
	 * This .java file creates a sub-GUI for the Waiter class.
	 * This sub-GUI handles Table Management
	 * author Samuel Baysting
	 **/
	
		//Parent Windows
		public final WaiterMainGUI parent;
		public final ManageTablesFrame currentFrame = this;
		//Waiter Interface
		public Waiter waiter;
		//Parent Panels
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel;
		//Subpanels
		private GradientPanel statusPanel,paymentPanel;
		private JPanel paymentDisplay;
		//Popups
		private JFrame numberpad;
		//Swing Objects
		private JButton backButton,statusButton,orderQueueButton,acceptPaymentButton,refundButton;
		private JButton payWithCash,payWithCard;
		private JLabel titleLabel,dateAndTime,selectedTable,acceptPaymentTitle;
		private JTextPane scrollTest;
		private JScrollPane scroll,orderDisplayScroll;
		private JTable orderDisplay;
		private JComboBox assignedTables;
		//Other Variables
		private Timer timer;
		private float balance = 0;
		private LinkedList<String> seatList,nameList,priceList,quantityList;
		private Vector<Vector<String>> data;
		private Vector<String> columnnames;
		private DefaultTableModel tablemodel;
		private Float payment;
		private int paymentButtonPressed = 0;
		
		/**
		 * This function initializes the GUI and runs the constructor for JFrame and the Waiter class
		 * @returns none
		 **/
		
		public ManageTablesFrame(WaiterMainGUI gui)
		{
			super();
			parent = gui;
			waiter = new Waiter();
			init();
		}

		/**
		 * This function sets the parameters for the JFrame window
		 * @returns none
		 **/

		public void init()
		{
			this.setTitle("Manage Tables");
			this.setResizable(true);
			this.setSize(900,600);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			getContentPane().add(rootPanel);
			this.setVisible(true);
		}
		
		/**
		 * This function initializes the JPanels associated with this GUI
		 * @returns none
		 **/

		public void frameManipulation()
		{
			setBackgroundPanel();
			setTitlePanel();
			setButtonPanel();
			setStatusPanel();
			setPaymentPanel();
			setNumberPad();
			setRootPanel();
		}
		
		/**
		 * This function initializes the rootPanel, which contains all of the other panels
		 * @returns none
		 **/
		
		private void setRootPanel()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			rootPanel.add(titlePanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(statusPanel);
			rootPanel.add(paymentPanel);
			rootPanel.add(backgroundPanel);
		}
		
		/**
		 * This function initializes the status panel, which is a sub-option for the Table Management
		 * interface
		 * @returns none
		 **/
		
		private void setStatusPanel()
		{
			statusPanel = new GradientPanel();
			statusPanel.setLayout(null);
			statusPanel.setBounds(new Rectangle(235,60,655,508));
			statusPanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			//Create scroll bar test
			scrollTest = new JTextPane();
		    scroll = new JScrollPane(scrollTest);
		    scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    scroll.getVerticalScrollBar().setUnitIncrement(16);
		    scroll.setBounds(new Rectangle(0,0,900-245,545-37));
		    scroll.setBackground(Color.white);
		    
		    //Add components to statusPanel
		    statusPanel.add(scroll);
		    
		    //Set default panel visibility
		    statusPanel.setVisible(false);
		}
		
		/**
		 * This function initializes the Payment Panel, which is used for the
		 * Accept Payment function of the Waiter
		 * @returns none
		 **/
		
		private void setPaymentPanel()
		{
			// Panel setup
			paymentPanel = new GradientPanel();
			paymentPanel.setLayout(null);
			paymentPanel.setBounds(new Rectangle(235,60,655,508));
			paymentPanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			paymentPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			
			// Component setup
			
			// Title setup
			acceptPaymentTitle = new JLabel("Accept Payment");
			acceptPaymentTitle.setHorizontalAlignment(JLabel.CENTER);
			acceptPaymentTitle.setFont(acceptPaymentTitle.getFont().deriveFont(20.0f));
			acceptPaymentTitle.setBorder(BorderFactory.createLineBorder(Color.black));
			acceptPaymentTitle.setBounds(new Rectangle(0,0,655,40));
			// Table Selection Box setup
			assignedTables = new JComboBox(getAssignedTables());
			assignedTables.addActionListener(this);
			assignedTables.setBounds(new Rectangle(285,60,200,30));
			// "Select Table" Text Box setup
			selectedTable = new JLabel("Select a Table:");
			selectedTable.setBounds(new Rectangle(60,60,200,30));
			selectedTable.setHorizontalAlignment(JLabel.RIGHT);
			selectedTable.setFont(selectedTable.getFont().deriveFont(14.0f));

			// Order Display Text Box setup
			paymentDisplay = new JPanel();
			paymentDisplay.setLayout(null);
			paymentDisplay.setOpaque(true);
			paymentDisplay.setBackground(Color.white);
			paymentDisplay.setBounds(new Rectangle(10,140,635,220));
			paymentDisplay.setBorder(BorderFactory.createLineBorder(Color.black));
			
			//orderDisplay Table
			tableSetup();
		    orderDisplayScroll = new JScrollPane(orderDisplay);
		    orderDisplayScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    orderDisplayScroll.getVerticalScrollBar().setUnitIncrement(16);
		    orderDisplayScroll.setBounds(new Rectangle(10,120,635,240));
		    orderDisplayScroll.setBackground(Color.white);
		    orderDisplayScroll.setBorder(BorderFactory.createLineBorder(Color.black));
		    
			// Pay with Card button setup
			payWithCard = new JButton("Pay with Credit/Debit");
			payWithCard.addActionListener(this);
			payWithCard.setBounds(364,390,220,80);
			payWithCard.setFont(payWithCard.getFont().deriveFont(16.0f));
			payWithCard.setFocusPainted(false);
			payWithCard.setEnabled(false);
			
			// Pay with Cash button setup
			payWithCash = new JButton("Pay with Cash");
			payWithCash.addActionListener(this);
			payWithCash.setBounds(72,390,220,80);
			payWithCash.setFont(payWithCash.getFont().deriveFont(16.0f));
			payWithCash.setFocusPainted(false);
			payWithCash.setEnabled(false);
			
			// Add components to panel
			paymentPanel.add(acceptPaymentTitle);
			paymentPanel.add(assignedTables);
			paymentPanel.add(selectedTable);
			paymentPanel.add(orderDisplayScroll);
			paymentPanel.add(payWithCard);
			paymentPanel.add(payWithCash);
			
			// Set default panel visibility
			paymentPanel.setVisible(false);
		}
		
		/**
		 * This function initializes the GUI background and sets the color
		 * @returns none
		 **/
		
		private void setBackgroundPanel()
		{
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,900,600);
		}
		
		/**
		 * This function initializes the pop-up number pad associated with the Accept
		 * Payment function
		 * @returns none
		 **/
		
		 private void setNumberPad()
		 {
			numberpad = new JFrame();
			numberpad.setLayout(null);
			numberpad.setSize(300, 375);
			numberpad.setLocation(250, 300);
			numberpad.setTitle("Number Pad");
			numberpad.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			numberpad.setAlwaysOnTop(true);
			numberpad.setVisible(false);
			numberpad.setResizable(false);
			// Input field setup
			JPanel padTitle = new JPanel();
			padTitle.setLayout(null);
			padTitle.setBounds(0,0,316,40);
			padTitle.setBorder(BorderFactory.createLineBorder(Color.black));
			padTitle.setBackground(Color.white);
			final JLabel padInput = new JLabel();
			padInput.setBounds(60,0,200,40);
			padInput.setOpaque(false);
			padInput.setHorizontalAlignment(JLabel.RIGHT);
			padInput.setFont(padInput.getFont().deriveFont(16.0f));
			padTitle.add(padInput);
			numberpad.add(padTitle);
			// Button setup
			JPanel padButtons = new JPanel();
			padButtons.setLayout(new GridLayout(4,3));
			padButtons.setBounds(0,40,300,260);
			JButton one = new JButton("1");
			one.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"1");
			    }
			});
			JButton two = new JButton("2");
			two.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"2");
			    }
			});
			JButton three = new JButton("3");
			three.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"3");
			    }
			});
			JButton four = new JButton("4");
			four.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"4");
			    }
			});
			JButton five = new JButton("5");
			five.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"5");
			    }
			});
			JButton six = new JButton("6");
			six.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"6");
			    }
			});
			JButton seven = new JButton("7");
			seven.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"7");
			    }
			});
			JButton eight = new JButton("8");
			eight.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"8");
			    }
			});
			JButton nine = new JButton("9");
			nine.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"9");
			    }
			});
			JButton zero = new JButton("0");
			zero.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			        padInput.setText(padInput.getText()+"0");
			    }
			});
			JButton period = new JButton(".");
			period.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			    	if(padInput.getText().isEmpty()){
			    		padInput.setText("0.");
			    	}
			    	else{
			    		padInput.setText(padInput.getText()+".");
			    	}
			    }
			});
			JButton back = new JButton("Erase");
			back.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			    	if(padInput.getText() == "0."){
			    		padInput.setText("");
			    	}
			    	else{
			    		String temp = "";
			    		String text = padInput.getText();
			    		int size = text.length();
			    		for(int i = 0;i < size-1;i++){
			    			temp = temp + text.charAt(i);
			    		}
			    		padInput.setText(temp);
			    	}
			    }
			});
			padButtons.add(one);
			padButtons.add(two);
			padButtons.add(three);
			padButtons.add(four);
			padButtons.add(five);
			padButtons.add(six);
			padButtons.add(seven);
			padButtons.add(eight);
			padButtons.add(nine);
			padButtons.add(back);
			padButtons.add(zero);
			padButtons.add(period);
			numberpad.add(padButtons);
			// Submit button
			JButton submit = new JButton("Submit");
			submit.setBounds(0,300,300,50);
			submit.addActionListener( new ActionListener()
			{
			    public void actionPerformed(ActionEvent e)
			    {
			    	DecimalFormat df = new DecimalFormat();
					df.setMaximumFractionDigits(2);
					df.setMinimumFractionDigits(2);
			    	try{
			    		payment = (float)Float.parseFloat(padInput.getText());
			    	}
			    	catch(Exception n){
			    		payment = (float)0;
			    		};
			    	if(!(padInput.getText().isEmpty()) && (payment >= (float)0.01) ){
			    		numberpad.dispose();
			    		int success = makePayment(payment);
			    		System.out.println(success);
			    		if(success == 0){ //Successful payment
			    			JOptionPane.showMessageDialog(null, "Payment was successful! The new balance will be reflected on the order.","InfoBox", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		else if(success == 1){ //Payment > Balance
			    			JOptionPane.showMessageDialog(null, "Please enter a number that is less than or equal to the current balance.","InfoBox", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		else{ //Success == 2 (Payment was declined)
			    			JOptionPane.showMessageDialog(null, "Payment was declined. Please try another payment method.","InfoBox", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    	}
			    	else{
			    		numberpad.dispose();
			    		JOptionPane.showMessageDialog(null, "Payment must be at least 0.01 and a valid number. Please try again.","InfoBox", JOptionPane.INFORMATION_MESSAGE);
			    	}
			    }
			});
			numberpad.add(submit);
			
			
			
		 }
		 
		 	/**
			 * This function initializes the title, date and time
			 * @returns none
			 **/
		
		private void setTitlePanel()
		{
			// Create Title Panel
			titlePanel = new JPanel();
			titlePanel.setLayout(null);
			titlePanel.setOpaque(false);
			titlePanel.setBounds(new Rectangle(0,0,900,55));
			// Set Title
			titleLabel = new JLabel("Table Management Interface");
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setFont(titleLabel.getFont().deriveFont(32.0f));
			titleLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			titleLabel.setBounds(new Rectangle(0,0,600,55));
			// Set Date and Time
			dateAndTime = new JLabel();
			dateAndTime.setHorizontalAlignment(JLabel.CENTER);
			dateAndTime.setFont(dateAndTime.getFont().deriveFont(22.0f));
			dateAndTime.setBorder(BorderFactory.createLineBorder(Color.black));
			dateAndTime.setBounds(new Rectangle(600,0,300,55));
			updateClock();
			// Create a timer to update the clock
			timer = new Timer(500,this);
            timer.setRepeats(true);
            timer.setCoalesce(true);
            timer.setInitialDelay(0);
            timer.start();

			// Add components to Title Panel
			titlePanel.add(titleLabel);
			titlePanel.add(dateAndTime);
		}
		
		/**
		 * This function initializes the button panel and all of the JButtons
		 * associated with the panel
		 * @returns none
		 **/
		
		private void setButtonPanel()
		{
			// Create Button Panel
			buttonPanel = new JPanel();
			buttonPanel.setLayout(new GridLayout(5,0,0,5));
			buttonPanel.setBounds(new Rectangle(0,59,230,511));
			buttonPanel.setOpaque(false);
			buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			// Set Back Button
			backButton = new JButton("BACK");;
			backButton.addActionListener(this);
			backButton.setFont(backButton.getFont().deriveFont(16.0f));
			backButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			backButton.setFocusPainted(false);
			
			// Set Request Table Status Change Button
			statusButton = new JButton("<html>Request Table<br />Status Change</html>");
			statusButton.addActionListener(this);
			statusButton.setFont(statusButton.getFont().deriveFont(16.0f));
			statusButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			statusButton.setFocusPainted(false);
			
			// Set Manage Order Queue Button
			orderQueueButton = new JButton("Manage Order Queue");
			orderQueueButton.addActionListener(this);
			orderQueueButton.setFont(orderQueueButton.getFont().deriveFont(16.0f));
			orderQueueButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			orderQueueButton.setFocusPainted(false);
			
			// Set Accept Payment Button
			acceptPaymentButton = new JButton("Accept Payment");
			acceptPaymentButton.addActionListener(this);
			acceptPaymentButton.setFont(acceptPaymentButton.getFont().deriveFont(16.0f));
			acceptPaymentButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			acceptPaymentButton.setFocusPainted(false);
			
			// Set Request Refund Button
			refundButton = new JButton("Request Refund");
			refundButton.addActionListener(this);
			refundButton.setFont(refundButton.getFont().deriveFont(16.0f));
			refundButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			refundButton.setFocusPainted(false);
			
			//Add components to Button Panel
			buttonPanel.add(statusButton);
			buttonPanel.add(orderQueueButton);
			buttonPanel.add(acceptPaymentButton);
			buttonPanel.add(refundButton);
			buttonPanel.add(backButton);
		}
		
		/**
		 * This is the action listener for all of the JComponents. This decides what function will be run
		 * upon some event related to the given JComponents
		 * @returns none
		 **/
		
		// Action Listener
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == backButton)
				{
					parent.setVisible(true);
					dispose();
				}
			if(a == statusButton)
				{
					if(statusPanel.isVisible()==false){
						statusPanel.setVisible(true);
					}
					else{
						statusPanel.setVisible(false);
					}
				}
			if(a == acceptPaymentButton)
				{
					if(paymentPanel.isVisible()==false){
						paymentPanel.setVisible(true);
					}
					else{
						paymentPanel.setVisible(false);
					}
				}
			if(a == refundButton)
				{
				
				
				}
			if(a == orderQueueButton)
				{
				
				
				}
			if(a == assignedTables)
				{
				if(assignedTables.getSelectedItem() != ""){
					payWithCash.setEnabled(true);
					payWithCard.setEnabled(true);
					refreshOrderTable();
					}
				else{
					payWithCash.setEnabled(false);
					payWithCard.setEnabled(false);
					tablemodel.setDataVector(new Vector<Vector<String>>(), columnnames);
					tablemodel.fireTableDataChanged();
					}
				}
			if(a == payWithCash)
				{
					paymentButtonPressed = 0;
					launchNumberPad();
				}
			if(a == payWithCard)
				{
					paymentButtonPressed = 1;
					launchNumberPad();
				}
			if(a == timer)
				{
					updateClock();
				}
		}
		
		/**
		 * This is the function that updates the clock. Is called every half of a second by
		 * the action listener
		 * @returns none
		 **/
		
		private void updateClock() {
            dateAndTime.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }
		
		/**
		 * This function gets all of the tables assigned to the signed-in waiter
		 * @returns a Vector of the tables
		 **/
		
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
		
		/**
		 * This function will retrieve all order data from the server.
		 * This function currently receives information from a fake database.
		 * Function also organizes the information
		 * @returns none
		 **/

		private void retrieveOrderData(){
			//Find correct table
			balance = 0;
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			df.setMinimumFractionDigits(2);
			int table_id = returnTableID();
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
		 
		/**
		 * This function gets the most up-to-date table data from the server
		 * @returns none
		 **/
		
		 private void refreshOrderTable()
		 {
			// Get most recent table data
			 retrieveOrderData();
			// Setup Table Data
			data = new Vector<Vector<String>>();
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
			while(tablemodel.getRowCount()<9){ //For formatting purposes
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
		 
		 /**
			 * This function sets up the JTable used to display orders for the first time.
			 * This function is only called during GUI initialization.
			 * @returns none
			 **/
		 
		 private void tableSetup(){
			// Table Parameters
			data = new Vector<Vector<String>>();
			columnnames = new Vector<String>();
			columnnames.add("Seat Number");
			columnnames.add("Menu Item");
			columnnames.add("Quantity");
			columnnames.add("Price");
			tablemodel = new DefaultTableModel();
			tablemodel.setDataVector(data,columnnames);
			orderDisplay = new JTable(tablemodel);
			orderDisplay.setOpaque(true);
			orderDisplay.setBackground(Color.white);
			orderDisplay.setBorder(BorderFactory.createEmptyBorder());
			orderDisplay.setShowGrid(false);
			orderDisplay.setRowHeight(25);
			orderDisplay.setFont(orderDisplay.getFont().deriveFont(16.0f));
			orderDisplay.getTableHeader().setFont(orderDisplay.getFont().deriveFont(16.0f));
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			centerRenderer.setFont(orderDisplay.getFont().deriveFont(16.0f));
			for(int x=0;x<tablemodel.getColumnCount();x++){
				orderDisplay.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		       }
		 }
		 
		 /**
			 * This function gets and finds a table ID associated with an TableOrder object
			 * @returns the integer table ID
			 **/
		 
		 private int returnTableID()
		 {
			 Iterator i = waiter.table.iterator();
				TableOrder t = new TableOrder(null,null,0);
				int table_id = 0;
				while(i.hasNext()){
					t = (TableOrder)i.next();
					String temp = (String)assignedTables.getSelectedItem();
					int temp1 = new Scanner(temp).useDelimiter("\\D+").nextInt();
					if(temp1==t.TABLE_ID){
						table_id = t.TABLE_ID;
						break;
					}
				}
			
			return table_id;
		 }
		 
		 /**
			 * This updates the database with the updated order after adjusting the balance
			 * @returns the integer exit code (0,1,2)
			 **/
		 
		 private int makePayment(float payment)
		 {
			 DecimalFormat df = new DecimalFormat();
			 df.setMaximumFractionDigits(2);
			 df.setMinimumFractionDigits(2);
			 if(payment > balance){
				 return 1;
			 }
			 // else if(payment not accepted), return 2;
			 else{
			 int table_id = returnTableID();
			 Iterator i = waiter.table.iterator();
			 int counter = 0;
			 while(i.hasNext()){
				 TableOrder temp = (TableOrder)i.next();
				 if(temp.TABLE_ID == table_id){
					 TableOrder temp2 = waiter.table.remove(counter);
					 temp2.add(new Order(-1,payment,paymentButtonPressed));
					 waiter.table.add(temp2);
					 break;
				 }
				 counter++;
			 }
			 waiter.updateChanges();
			 refreshOrderTable();
			 return 0;
			 }
		 }
		 
		 /**
			 * This launches the number pad when necessary
			 * @returns none
			 **/
		 
		 private void launchNumberPad()
		 {
			setNumberPad();
			numberpad.setVisible(true);
			
		 }
}
