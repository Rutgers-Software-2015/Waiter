package Waiter;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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

import Login.LoginWindow;
import Shared.Numberpad;
import Shared.ADT.Order;
import Shared.ADT.TableOrder;
import Shared.Communicator.NotificationGUI;
import Shared.Gradients.*;

import javax.swing.ButtonGroup;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JTextArea;



public class WaiterGUI extends JFrame implements ActionListener{

	
	/**
	 * This class facilitates interaction between the employee
	 * and the Waiter restaurant system
	 * 
	 * @author Samuel Baysting
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
		//Parent Panels
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel,buttonPanelBackground,cardPanel;
		private GradientPanel card1,card2,card3;
		//Swing Objects
		private GradientButton backButton,statusButton,orderQueueButton,acceptPaymentButton,refundButton;
		private GradientButton payWithCash,payWithCard;
		private JLabel titleLabel,dateAndTime;
		//Swing Layouts
		private CardLayout c;
		//Other Variables
		private Timer timer;
		
		//Parent Windows
				//Waiter Interface
				public Waiter waiter;
				//Parent Panels
				//Subpanels
				private GradientPanel statusPanel,paymentPanel;
				private JPanel paymentDisplay;
				//Popups
				private JFrame numberpad;
				//Swing Objects
				private JLabel selectedTable,acceptPaymentTitle;
				private JTextPane scrollTest;
				private JScrollPane scroll,orderDisplayScroll;
				private JTable orderDisplay,manageDisplay;
				private JComboBox assignedTables;
				//Other Variables
				private float balance = 0;
				private LinkedList<String> seatList,nameList,priceList,quantityList,requestList,statusList;
				private Vector<Vector> data;
				private Vector<String> columnnames;
				private DefaultTableModel tablemodel;
				private int paymentButtonPressed = 0;
				private Numberpad numberPad = null;
				private GradientPanel tableStatusPanel;
				private JLabel tableStatusTitle;
				private GradientPanel orderQueuePanel;
				private JLabel orderQueueTitle;
				private GradientPanel refundPanel;
				private JLabel refundTitle;
				private JComboBox tableStatusBox;
				private GradientButton sendChangeRequestButton;
				private Component newStatusLabel;
				private JComboBox assignedTablesBox;
				private Component tableToChangeLabel;
				private Vector<String> tableStatuses = null;
				private JComboBox assignedTablesQueue;
				private JComboBox assignedTablesRefund;
				private DefaultTableModel tablemodel2;
				private JTable orderDisplay2;
				private Vector<String> columnnames2;
				private Vector<Vector> data2;
				private JScrollPane orderDisplayScroll2;
				private JButton orderQueueHelp;
				private GradientButton statusChangeButton;
				private JComboBox statusChangeBox;
				private JLabel lblChangeStatusTo;
				private JButton statusChangeHelp;
				private JButton paymentHelp;
				private JButton refundHelp;
				private JLabel label;
				private GradientButton removeSelectedButton;
				private Vector<Vector> data3;
				private Vector columnnames3;
				private DefaultTableModel tablemodel3;
				private JTable orderDisplay3;
				private JScrollPane orderDisplayScroll3;
				private JLabel refundLabel;
				private JTextArea refundInput;
				private GradientButton submitRefundRequest;
				private NotificationGUI notification;
		
		
		public WaiterGUI()
		{
			super();
			waiter = new Waiter();
			init();
		}


		public void init()
		{
			this.setTitle("Wait Staff Interface");
			this.setResizable(true);
			this.setSize(1200,700);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			getContentPane().add(rootPanel);
			
			addWindowListener(new WindowAdapter() // To open main window again if you hit the corner "X"
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	                new LoginWindow();
	                notification.close();
	                dispose();
	            }
	        });
			
			c = (CardLayout)(cardPanel.getLayout());
			
			this.setVisible(true);
		}

		public void frameManipulation()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			setBackgroundPanel();
			setTitlePanel();
			setTableStatusChangeCard();
			setManageOrderQueueCard();
			setAcceptPaymentCard();
			setRefundCard();
			setCardPanel();
			setButtonPanel();
			setRootPanel();
		}
		
		private void setRootPanel()
		{
			// Create Notification GUI
			notification = new NotificationGUI();
			rootPanel.add(notification);
			// Create all other panels
			rootPanel.add(titlePanel);
			rootPanel.add(cardPanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(buttonPanelBackground);
			rootPanel.add(backgroundPanel);
		}
		
		private void setBackgroundPanel()
		{
			// Create Button Background Panel
			buttonPanelBackground = new GradientPanel();
			buttonPanelBackground.setGradient(new Color(255,220,220), new Color(255,110,110));
			buttonPanelBackground.setBounds(0, 55, 250, 617);
			buttonPanelBackground.setBorder(new LineBorder(new Color(0, 0, 0)));
			
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
			backgroundPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,1194,672);
		}
		
		//************************************************************
		//DO NOT edit the following function except for the title name
		//************************************************************
		
		private void setTitlePanel()
		{
			// Create Title Panel
			titlePanel = new JPanel();
			titlePanel.setBounds(0, 0, 1194, 56);
			titlePanel.setLayout(null);
			titlePanel.setOpaque(false);
			// Set Title
			titleLabel = new JLabel("Wait Staff Interface");
			titleLabel.setHorizontalAlignment(JLabel.CENTER);
			titleLabel.setFont(titleLabel.getFont().deriveFont(38f));
			titleLabel.setBorder(BorderFactory.createLineBorder(Color.black));
			titleLabel.setBounds(new Rectangle(0, 0, 793, 56));
			titlePanel.add(titleLabel);
			// Set Date and Time
			dateAndTime = new JLabel();
			dateAndTime.setBounds(792, 0, 402, 56);
			titlePanel.add(dateAndTime);
			dateAndTime.setHorizontalAlignment(JLabel.CENTER);
			dateAndTime.setFont(dateAndTime.getFont().deriveFont(28f));
			dateAndTime.setBorder(BorderFactory.createLineBorder(Color.black));
			updateClock();
			// Create a timer to update the clock
			timer = new Timer(500,this);
			timer.setRepeats(true);
			timer.setCoalesce(true);
			timer.setInitialDelay(0);
			timer.start();
		}
		
		//*********************************************************
		//DO NOT change the location of the following panel
		//*********************************************************
		
		private void setButtonPanel()
		{
			// Create Button Panel
			buttonPanel = new JPanel();
			buttonPanel.setBounds(7, 61, 236, 604);
			buttonPanel.setOpaque(false);
			buttonPanel.setBorder(null);
			buttonPanel.setLayout(new GridLayout(5, 0, 5, 5));
			
			// Set Request Table Status Change Button
			statusButton = new GradientButton("<html>Request Table<br />Status Change</html>");
			statusButton.addActionListener(this);
			statusButton.setFont(statusButton.getFont().deriveFont(16.0f));
			statusButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			statusButton.setFocusPainted(false);
			
			// Set Manage Order Queue Button
			orderQueueButton = new GradientButton("Manage Order Queue");
			orderQueueButton.addActionListener(this);
			orderQueueButton.setFont(orderQueueButton.getFont().deriveFont(16.0f));
			orderQueueButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			orderQueueButton.setFocusPainted(false);
			
			// Set Accept Payment Button
			acceptPaymentButton = new GradientButton("Accept Payment");
			acceptPaymentButton.addActionListener(this);
			acceptPaymentButton.setFont(acceptPaymentButton.getFont().deriveFont(16.0f));
			acceptPaymentButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			acceptPaymentButton.setFocusPainted(false);
			
			// Set Request Refund Button
			refundButton = new GradientButton("Request Refund");
			refundButton.addActionListener(this);
			refundButton.setFont(refundButton.getFont().deriveFont(16.0f));
			refundButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			refundButton.setFocusPainted(false);
			// Set Back Button
			backButton = new GradientButton("BACK");
			backButton.addActionListener(this);												
			backButton.setFont(backButton.getFont().deriveFont(16.0f));
			backButton.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
			backButton.setFocusPainted(false);
			
			buttonPanel.add(statusButton);
			buttonPanel.add(orderQueueButton);
			buttonPanel.add(acceptPaymentButton);
			buttonPanel.add(refundButton);
			buttonPanel.add(backButton);
		}
		
		//********************************************************************************
		//DO NOT deviate from the card layout or change the size/location of the cardPanel.
		//Creating and adding cards is OK
		//********************************************************************************
		
		private void setCardPanel()
		{
			cardPanel = new GradientPanel();
			cardPanel.setLayout(new CardLayout()); // How to define a Card Layout
			cardPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
			cardPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			cardPanel.setBounds(273, 79, 896, 569);
			
			card1 = new GradientPanel(); // Create card with a button YES
			card1.add(new JButton("YES"));
			card1.setLayout(new GridLayout(1,0));
			
			card2 = new GradientPanel(); // Create card with a button NO
			card2.setLayout(new GridLayout(1,0));
			card2.add(new JButton("NO"));
			
			card3 = new GradientPanel(); // Create blank card
			
			JPanel temp = new JPanel();
			temp.setOpaque(false);
			cardPanel.add(temp,"BLANK");
			cardPanel.add(paymentPanel,"Accept Payment"); // How to add cards to a Card Layout
			cardPanel.add(tableStatusPanel,"Request Table Status Change");
			cardPanel.add(orderQueuePanel,"Manage Order Queue");
			cardPanel.add(refundPanel,"Request Refund");
			
			cardPanel.setVisible(true);
		}
		
		private void setTableStatusChangeCard()
		{
			// Panel setup
			tableStatusPanel = new GradientPanel();
			tableStatusPanel.setLayout(null);
			tableStatusPanel.setBounds(new Rectangle(235,60,655,508));
			tableStatusPanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			tableStatusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
						
			// Component setup
						
			// Title setup
			tableStatusTitle = new JLabel("Request Table Status Change");
			tableStatusTitle.setHorizontalAlignment(JLabel.CENTER);
			tableStatusTitle.setFont(tableStatusTitle.getFont().deriveFont(20.0f));
			tableStatusTitle.setBorder(BorderFactory.createLineBorder(Color.black));
			tableStatusTitle.setBounds(new Rectangle(0, 0, 846, 48));
			tableStatusPanel.add(tableStatusTitle);
			
			tableToChangeLabel = new JLabel("Table to Change:");
			tableToChangeLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
			tableToChangeLabel.setBounds(214, 120, 195, 40);
			tableStatusPanel.add(tableToChangeLabel);
			
			assignedTablesBox = new JComboBox(getAssignedTables());
			assignedTablesBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			assignedTablesBox.setBounds(443, 125, 237, 40);
			tableStatusPanel.add(assignedTablesBox);
			
			newStatusLabel = new JLabel("New Table Status: ");
			newStatusLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
			newStatusLabel.setBounds(205, 206, 204, 40);
			tableStatusPanel.add(newStatusLabel);
			
			tableStatuses = new Vector<String>();
			tableStatuses.add("");
			tableStatuses.add("Dirty");
			tableStatuses.add("Occupied");
			tableStatuses.add("Reserved");
			tableStatuses.add("Available");
			
			tableStatusBox = new JComboBox(tableStatuses);
			tableStatusBox.setFont(new Font("Tahoma", Font.PLAIN, 18));
			tableStatusBox.setBounds(443, 206, 237, 40);
			tableStatusPanel.add(tableStatusBox);
			
			sendChangeRequestButton = new GradientButton("Send Request to Host");
			sendChangeRequestButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
			sendChangeRequestButton.setBounds(205, 378, 475, 68);
			sendChangeRequestButton.addActionListener(this);
			sendChangeRequestButton.setFocusPainted(false);
			tableStatusPanel.add(sendChangeRequestButton);
			
			statusChangeHelp = new JButton();
			statusChangeHelp.setIcon(new ImageIcon("src\\Waiter\\questionmark.png"));
			statusChangeHelp.setBounds(844, 0, 50, 48);
			statusChangeHelp.addActionListener(this);
			tableStatusPanel.add(statusChangeHelp);
		}
		
		private void setManageOrderQueueCard()
		{
			// Panel setup
			orderQueuePanel = new GradientPanel();
			orderQueuePanel.setLayout(null);
			orderQueuePanel.setBounds(new Rectangle(235,60,655,508));
			orderQueuePanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			orderQueuePanel.setBorder(BorderFactory.createLineBorder(Color.black));
						
			// Component setup
						
			// Title setup
			orderQueueTitle = new JLabel("Manage Order Queue");
			orderQueueTitle.setHorizontalAlignment(JLabel.CENTER);
			orderQueueTitle.setFont(orderQueueTitle.getFont().deriveFont(20.0f));
			orderQueueTitle.setBorder(BorderFactory.createLineBorder(Color.black));
			orderQueueTitle.setBounds(new Rectangle(0, 0, 846, 48));
			orderQueuePanel.add(orderQueueTitle);
			
			// Table Selection Box setup
			assignedTablesQueue = new JComboBox(getAssignedTables());
			assignedTablesQueue.addActionListener(this);
			assignedTablesQueue.setBounds(new Rectangle(412, 68, 200, 30));
			orderQueuePanel.add(assignedTablesQueue);
			// "Select Table" Text Box setup
			selectedTable = new JLabel("Select a Table:");
			selectedTable.setBounds(new Rectangle(183, 68, 200, 30));
			selectedTable.setHorizontalAlignment(JLabel.RIGHT);
			selectedTable.setFont(selectedTable.getFont().deriveFont(14.0f));
			orderQueuePanel.add(selectedTable);
			
			//orderDisplay Table
			tableManageSetup();
		    orderDisplayScroll2 = new JScrollPane(orderDisplay2);
		    orderDisplayScroll2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    orderDisplayScroll2.getVerticalScrollBar().setUnitIncrement(16);
		    orderDisplayScroll2.setBounds(new Rectangle(47, 120, 592, 420));
		    orderDisplayScroll2.setBackground(Color.white);
		    orderDisplayScroll2.setBorder(BorderFactory.createLineBorder(Color.black));
		    orderQueuePanel.add(orderDisplayScroll2);
		    
		    lblChangeStatusTo = new JLabel("Change order status to:");
		    lblChangeStatusTo.setHorizontalAlignment(SwingConstants.CENTER);
		    lblChangeStatusTo.setFont(new Font("Tahoma", Font.PLAIN, 18));
		    lblChangeStatusTo.setBounds(671, 147, 192, 40);
		    orderQueuePanel.add(lblChangeStatusTo);
		    
		    Vector<String> v = new Vector<String>();
		    v.add("");
		    v.add("Served");
		    v.add("To be delivered");
		    v.add("Paid");
		    v.add("In queue");
		    v.add("In preparation");
		    
		    statusChangeBox = new JComboBox(v);
		    statusChangeBox.setBounds(671, 198, 192, 30);
		    orderQueuePanel.add(statusChangeBox);
		    
		    statusChangeButton = new GradientButton("Confirm");
		    statusChangeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		    statusChangeButton.setBounds(671, 269, 192, 78);
		    orderQueuePanel.add(statusChangeButton);
		    
		    orderQueueHelp = new JButton();
		    orderQueueHelp.setIcon(new ImageIcon("src\\Waiter\\questionmark.png"));
		    orderQueueHelp.setBounds(844, 0, 50, 48);
		    orderQueueHelp.addActionListener(this);
		    orderQueuePanel.add(orderQueueHelp);
		    
		    label = new JLabel("or");
		    label.setHorizontalAlignment(SwingConstants.CENTER);
		    label.setFont(new Font("Tahoma", Font.PLAIN, 18));
		    label.setBounds(671, 358, 192, 62);
		    orderQueuePanel.add(label);
		    
		    removeSelectedButton = new GradientButton("Remove Selected");
		    removeSelectedButton.addActionListener(this);
		    removeSelectedButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		    removeSelectedButton.setBounds(671, 431, 192, 78);
		    orderQueuePanel.add(removeSelectedButton);
		}
		
		/**
		 * This function initializes the Payment Panel, which is used for the
		 * Accept Payment function of the Waiter
		 * 
		 * @returns none
		 * 
		 **/
		
		private void setAcceptPaymentCard()
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
			acceptPaymentTitle.setBounds(new Rectangle(0, 0, 846, 48));
			// Table Selection Box setup
			assignedTables = new JComboBox(getAssignedTables());
			assignedTables.addActionListener(this);
			assignedTables.setBounds(new Rectangle(411, 68, 200, 30));
			// "Select Table" Text Box setup
			selectedTable = new JLabel("Select a Table:");
			selectedTable.setBounds(new Rectangle(183, 68, 200, 30));
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
		    orderDisplayScroll.setBounds(new Rectangle(47, 120, 797, 287));
		    orderDisplayScroll.setBackground(Color.white);
		    orderDisplayScroll.setBorder(BorderFactory.createLineBorder(Color.black));
		    
			// Pay with Card button setup
			payWithCard = new GradientButton("Pay with Credit/Debit");
			payWithCard.addActionListener(this);
			payWithCard.setBounds(529,442,220,89);
			payWithCard.setFont(payWithCard.getFont().deriveFont(16.0f));
			payWithCard.setFocusPainted(false);
			payWithCard.setEnabled(false);
			
			// Pay with Cash button setup
			payWithCash = new GradientButton("Pay with Cash");
			payWithCash.addActionListener(this);
			payWithCash.setBounds(163,442,220,89);
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
			
			paymentHelp = new JButton();
			paymentHelp.addActionListener(this);
			paymentHelp.setIcon(new ImageIcon("src\\Waiter\\questionmark.png"));
			paymentHelp.setBounds(844, 0, 50, 48);
			paymentPanel.add(paymentHelp);
			
		}
		
		private void setRefundCard()
		{
			// Panel setup
			refundPanel = new GradientPanel();
			refundPanel.setLayout(null);
			refundPanel.setBounds(new Rectangle(235,60,655,508));
			refundPanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			refundPanel.setBorder(BorderFactory.createLineBorder(Color.black));
						
			// Component setup
						
			// Title setup
			refundTitle = new JLabel("Request Refund");
			refundTitle.setHorizontalAlignment(JLabel.CENTER);
			refundTitle.setFont(refundTitle.getFont().deriveFont(20.0f));
			refundTitle.setBorder(BorderFactory.createLineBorder(Color.black));
			refundTitle.setBounds(new Rectangle(0, 0, 846, 48));
			refundPanel.add(refundTitle);
			
			// Table Selection Box setup
			assignedTablesRefund = new JComboBox(getAssignedTables());
			assignedTablesRefund.addActionListener(this);
			assignedTablesRefund.setBounds(new Rectangle(411, 68, 200, 30));
			refundPanel.add(assignedTablesRefund);
			// "Select Table" Text Box setup
			selectedTable = new JLabel("Select a Table:");
			selectedTable.setBounds(new Rectangle(183, 68, 200, 30));
			selectedTable.setHorizontalAlignment(JLabel.RIGHT);
			selectedTable.setFont(selectedTable.getFont().deriveFont(14.0f));
			refundPanel.add(selectedTable);
			
			//orderDisplay Table
			tableRefundSetup();
		    orderDisplayScroll3 = new JScrollPane(orderDisplay3);
		    orderDisplayScroll3.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		    orderDisplayScroll3.getVerticalScrollBar().setUnitIncrement(16);
		    orderDisplayScroll3.setBounds(new Rectangle(47, 120, 592, 420));
		    orderDisplayScroll3.setBackground(Color.white);
		    orderDisplayScroll3.setBorder(BorderFactory.createLineBorder(Color.black));
		    refundPanel.add(orderDisplayScroll3);
			
			refundHelp = new JButton();
			refundHelp.addActionListener(this);
			refundHelp.setIcon(new ImageIcon("src\\Waiter\\questionmark.png"));
			refundHelp.setBounds(844, 0, 50, 48);
			refundPanel.add(refundHelp);
			
			refundLabel = new JLabel("<html><center>Why does the customer <br> want a refund?</center></html>");
			refundLabel.setHorizontalAlignment(SwingConstants.CENTER);
			refundLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
			refundLabel.setBounds(660, 127, 206, 69);
			refundPanel.add(refundLabel);
			
			refundInput = new JTextArea();
			refundInput.setFont(new Font("Monospaced", Font.PLAIN, 15));
			refundInput.setLineWrap(true);
			refundInput.setBorder(BorderFactory.createLineBorder(Color.black));
			refundInput.setBounds(660, 219, 206, 111);
			refundPanel.add(refundInput);
			
			submitRefundRequest = new GradientButton("<html><center>Submit Request to <br> Manager</center></html>");
			submitRefundRequest.setFont(new Font("Tahoma", Font.PLAIN, 16));
			submitRefundRequest.setBounds(660, 381, 206, 82);
			submitRefundRequest.addActionListener(this);
			refundPanel.add(submitRefundRequest);
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
				new LoginWindow();
				notification.close();
				dispose();
			}
			if(a == statusButton)
			{
				c.show(cardPanel, "Request Table Status Change");
			}
			if(a == acceptPaymentButton)
			{
				c.show(cardPanel, "Accept Payment");
			}
			if(a == refundButton)
			{
				c.show(cardPanel, "Request Refund");
			}
			if(a == orderQueueButton)
			{
				c.show(cardPanel, "Manage Order Queue");
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
			if(a == sendChangeRequestButton)
			{
				sendChangeRequest();
			}
			if(a == removeSelectedButton)
			{
				
			}
			if(a == submitRefundRequest)
			{
				JOptionPane.showMessageDialog(null, "Request was sent to Manager!","InfoBox", JOptionPane.INFORMATION_MESSAGE);
				refundInput.setText("");
			}
			if(a == statusChangeHelp)
			{
				JOptionPane.showMessageDialog(null, "Help will be written in the future","Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == orderQueueHelp)
			{
				JOptionPane.showMessageDialog(null, "Help will be written in the future","Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == paymentHelp)
			{
				JOptionPane.showMessageDialog(null, "Help will be written in the future","Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == refundHelp)
			{
				JOptionPane.showMessageDialog(null, "Help will be written in the future","Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == timer)
			{
				updateClock();
			}
		}
		
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
		 
		 /**
			 * This function sets up the Accept Payment JTable used to display orders for the first time.
			 * This function is only called during GUI initialization.
			 * @returns none
			 **/
		 
		 private void tableSetup(){
			// Table Parameters
			data = new Vector<Vector>();
			columnnames = new Vector<String>();
			columnnames.add("Seat Number");
			columnnames.add("Menu Item");
			columnnames.add("Quantity");
			columnnames.add("Price");
			tablemodel = new DefaultTableModel() {

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
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
			 * This function sets up the Manage Order Queue JTable used to display orders for the first time.
			 * This function is only called during GUI initialization.
			 * @returns none
			 **/
		 
		 private void tableManageSetup(){
			// Table Parameters
			data2 = new Vector<Vector>();
			columnnames2 = new Vector();
			columnnames2.add("Seat Number");
			columnnames2.add("Menu Item");
			columnnames2.add("Quantity");
			columnnames2.add("Price");
			columnnames2.add("Current Status");
			columnnames2.add("Checkbox");
			tablemodel2 = new DefaultTableModel() {

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			tablemodel2.setDataVector(data2,columnnames2);
			orderDisplay2 = new JTable(tablemodel2);
			orderDisplay2.setOpaque(true);
			orderDisplay2.setBackground(Color.white);
			orderDisplay2.setBorder(BorderFactory.createEmptyBorder());
			orderDisplay2.setShowGrid(false);
			orderDisplay2.setRowHeight(25);
			orderDisplay2.setFont(orderDisplay2.getFont().deriveFont(13.0f));
			orderDisplay2.getTableHeader().setFont(orderDisplay2.getFont().deriveFont(13.0f));
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			centerRenderer.setFont(orderDisplay2.getFont().deriveFont(13.0f));
			for(int x=0;x<tablemodel2.getColumnCount();x++){
				orderDisplay2.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		       }
		 }
		 
		 /**
			 * This function sets up the Manage Order Queue JTable used to display orders for the first time.
			 * This function is only called during GUI initialization.
			 * @returns none
			 **/
		 
		 private void tableRefundSetup(){
			// Table Parameters
			data3 = new Vector<Vector>();
			columnnames3 = new Vector();
			columnnames3.add("Seat Number");
			columnnames3.add("Menu Item");
			columnnames3.add("Quantity");
			columnnames3.add("Price");
			columnnames3.add("Checkbox");
			tablemodel3 = new DefaultTableModel() {

			    @Override
			    public boolean isCellEditable(int row, int column) {
			       //all cells false
			       return false;
			    }
			};
			tablemodel3.setDataVector(data3,columnnames3);
			orderDisplay3 = new JTable(tablemodel3);
			orderDisplay3.setOpaque(true);
			orderDisplay3.setBackground(Color.white);
			orderDisplay3.setBorder(BorderFactory.createEmptyBorder());
			orderDisplay3.setShowGrid(false);
			orderDisplay3.setRowHeight(25);
			orderDisplay3.setFont(orderDisplay3.getFont().deriveFont(13.0f));
			orderDisplay3.getTableHeader().setFont(orderDisplay2.getFont().deriveFont(13.0f));
			DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
			centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
			centerRenderer.setFont(orderDisplay3.getFont().deriveFont(13.0f));
			for(int x=0;x<tablemodel3.getColumnCount();x++){
				orderDisplay3.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
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
		  * This is called by the number pad window listener to process the payment
		  * after the number pad has accepted input
		  * 
		  * @param payment
		  * @return none
		  * 
		  */
		 
		 private void processPayment(float payment)
		 {
			 DecimalFormat df = new DecimalFormat();
				df.setMaximumFractionDigits(2);
				df.setMinimumFractionDigits(2);
		    	if(payment >= (float)0.01){
		    		int success = makePayment(payment);
		    		System.out.println(success);
		    		if(success == 0){ //Successful payment
		    			JOptionPane.showMessageDialog(null, "Payment was successful! The new balance will be reflected on the order.","InfoBox", JOptionPane.INFORMATION_MESSAGE);
		    		}
		    		else if(success == 1){ //Payment > Balance
		    			JOptionPane.showMessageDialog(null, "Please enter a number that is less than or equal to the current balance.","InfoBox", JOptionPane.ERROR_MESSAGE);
		    		}
		    		else{ //Success == 2 (Payment was declined)
		    			JOptionPane.showMessageDialog(null, "Payment was declined. Please try another payment method.","InfoBox", JOptionPane.ERROR_MESSAGE);
		    		}
		    	}
		    	else{
		    		JOptionPane.showMessageDialog(null, "Payment must be at least 0.01 and a valid number. Please try again.","InfoBox", JOptionPane.ERROR_MESSAGE);
		    	}
		 }
		 
		 /**
		   * This updates the database with the updated order after adjusting the balance
		   * 
		   * @returns the integer exit code (0,1,2)
		   * 
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
		   * 
		   * @returns none
		   * 
		   **/
		 
		 private void launchNumberPad()
		 {
			numberPad = new Numberpad();
			numberPad.setVisible(true);
			payWithCash.setEnabled(false);
			payWithCard.setEnabled(false);
			numberPad.addWindowListener(new WindowAdapter()
	        {
	            @Override
	            public void windowClosing(WindowEvent e)
	            {
	            	numberPad.setVisible(false);
	                processPayment(numberPad.get());
	                payWithCash.setEnabled(true);
	                payWithCard.setEnabled(true);
	            }
	        });
			
			
		 }
		 
		 private int sendChangeRequest()
		 {
			 if(tableStatusBox.getSelectedItem()!="" && assignedTablesBox.getSelectedItem()!=""){
				 JOptionPane.showMessageDialog(null, "Request has been sent successfully!","InfoBox", JOptionPane.INFORMATION_MESSAGE);
				 tableStatusBox.setSelectedIndex(0);
				 assignedTablesBox.setSelectedIndex(0);
				 return 0;
			 }
			 else{
				 JOptionPane.showMessageDialog(null, "Request did not send!","InfoBox", JOptionPane.ERROR_MESSAGE);
				 tableStatusBox.setSelectedIndex(0);
				 assignedTablesBox.setSelectedIndex(0);
				 return 1;
			 }
		 }
}
