package Waiter;

import java.awt.*; 
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import Login.LoginWindow;
import Shared.Numberpad;
import Shared.Notifications.NotificationGUI;
import Shared.Gradients.*;

import javax.swing.border.LineBorder;
import javax.swing.JTextArea;



public class WaiterGUI extends JFrame implements ActionListener{

	
	/**
	 * This class facilitates interaction between the employee
	 * and the Waiter restaurant system
	 * 
	 * @author Samuel Baysting
	 * @tester Samuel Baysting
	 * @debugger Samuel Baysting
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
		//Parent Panels
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel,buttonPanelBackground,cardPanel;
		private GradientPanel card1,card2;
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
				public WaiterHandler waiter;
				//Parent Panels
				//Subpanels
				private GradientPanel paymentPanel;
				private JPanel paymentDisplay;
				//Swing Objects
				private JLabel selectedTable,acceptPaymentTitle;
				private JScrollPane orderDisplayScroll;
				private JTable orderDisplay;
				@SuppressWarnings("rawtypes")
				private JComboBox assignedTables;
				@SuppressWarnings("rawtypes")
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
				@SuppressWarnings("rawtypes")
				private JComboBox tableStatusBox;
				private GradientButton sendChangeRequestButton;
				private Component newStatusLabel;
				@SuppressWarnings("rawtypes")
				private JComboBox assignedTablesBox;
				private Component tableToChangeLabel;
				private Vector<String> tableStatuses = null;
				@SuppressWarnings("rawtypes")
				private JComboBox assignedTablesQueue;
				@SuppressWarnings("rawtypes")
				private JComboBox assignedTablesRefund;
				private DefaultTableModel tablemodel2;
				private JTable orderDisplay2;
				private Vector<String> columnnames2;
				@SuppressWarnings("rawtypes")
				private Vector<Vector> data2;
				private JScrollPane orderDisplayScroll2;
				private JButton orderQueueHelp;
				private GradientButton statusChangeButton;
				@SuppressWarnings("rawtypes")
				private JComboBox statusChangeBox;
				private JLabel lblChangeStatusTo;
				private JButton statusChangeHelp;
				private JButton paymentHelp;
				private JButton refundHelp;
				@SuppressWarnings("rawtypes")
				private Vector<Vector> data3;
				@SuppressWarnings("rawtypes")
				private Vector columnnames3;
				private DefaultTableModel tablemodel3;
				private JTable orderDisplay3;
				private JScrollPane orderDisplayScroll3;
				private JLabel refundLabel;
				private JTextArea refundInput;
				private GradientButton submitRefundRequest;
				protected NotificationGUI notification;
				private GradientButton btnSelectAll;
				private boolean select = false;
		
		/**
		 * Class constructor
		 * Initialize Waiter GUI window
		 * 
		 */
		
		public WaiterGUI()
		{
			super();
			waiter = new WaiterHandler();
			init();
		}

		/**
		 * Set base parameters for the GUI window
		 * 
		 * @return void
		 * 
		 */

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
	                waiter.disconnect();
	                dispose();
	            }
	        });
			
			c = (CardLayout)(cardPanel.getLayout());
			
			this.setVisible(true);
		}

		/**
		 * Set up panels within the main JFrame
		 * 
		 * @return void
		 * 
		 */
		
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
		
		/**
		 * Adds all of the subpanels to the main rootPanel
		 * 
		 * @return void
		 * 
		 */
		
		private void setRootPanel()
		{
			// Create Notification GUI
			notification = new NotificationGUI(1,"Waiter");
			rootPanel.add(notification);
			// Create all other panels
			rootPanel.add(titlePanel);
			rootPanel.add(cardPanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(buttonPanelBackground);
			rootPanel.add(backgroundPanel);
		}
		
		/**
		 * Sets up the background panel with colors and size
		 * 
		 * @return void
		 * 
		 */
		
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
		
		/**
		 * Set up title panel with title, date and time
		 * 
		 * @return void
		 * 
		 */
		
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
		
		/**
		 * Sets up all of the side buttons to reach the main features of
		 * the program
		 * 
		 * @return void
		 * 
		 */
		
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
		
		/**
		 * Set up the panel which contains the interactive part of the GUI
		 * to assist with Waiter functions
		 * 
		 * @return void
		 * 
		 */
		
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
			
			new GradientPanel();
			
			JPanel temp = new JPanel();
			temp.setOpaque(false);
			cardPanel.add(temp,"BLANK");
			cardPanel.add(paymentPanel,"Accept Payment"); // How to add cards to a Card Layout
			cardPanel.add(tableStatusPanel,"Request Table Status Change");
			cardPanel.add(orderQueuePanel,"Manage Order Queue");
			cardPanel.add(refundPanel,"Request Refund");
			
			cardPanel.setVisible(true);
		}
		
		/**
		 * Sets up "Request Table Status Change" panel
		 * 
		 * @return void
		 * 
		 */
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
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
			
			assignedTablesBox = new JComboBox(waiter.assignedTableNames());
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
		
		/**
		 * Sets up "Manage Order Queue" panel
		 * 
		 * @return void
		 * 
		 */
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
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
			assignedTablesQueue = new JComboBox(waiter.assignedTableNames());
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
		    lblChangeStatusTo.setBounds(671, 190, 192, 40);
		    orderQueuePanel.add(lblChangeStatusTo);
		    
		    Vector<String> v = new Vector<String>();
		    v.add("");
		    v.add("SERVED");
		    v.add("PAID");
		    v.add("RETURNED");
		    
		    statusChangeBox = new JComboBox(v);
		    statusChangeBox.setBounds(671, 241, 192, 30);
		    orderQueuePanel.add(statusChangeBox);
		    
		    statusChangeButton = new GradientButton("Confirm");
		    statusChangeButton.setFont(new Font("Tahoma", Font.PLAIN, 18));
		    statusChangeButton.setBounds(671, 385, 192, 123);
		    statusChangeButton.addActionListener(this);
		    orderQueuePanel.add(statusChangeButton);
		    
		    orderQueueHelp = new JButton();
		    orderQueueHelp.setIcon(new ImageIcon("src\\Waiter\\questionmark.png"));
		    orderQueueHelp.setBounds(844, 0, 50, 48);
		    orderQueueHelp.addActionListener(this);
		    orderQueuePanel.add(orderQueueHelp);
		    
		    btnSelectAll = new GradientButton("Select All");
		    btnSelectAll.addActionListener(this);
		    btnSelectAll.setBounds(671, 120, 192, 23);
		    orderQueuePanel.add(btnSelectAll);
		}
		
		/**
		 * This function initializes the Payment Panel, which is used for the
		 * Accept Payment function of the Waiter
		 * 
		 * @returns void
		 * 
		 **/
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
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
			assignedTables = new JComboBox(waiter.assignedTableNames());
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
		
		/**
		 * Sets up the "Request Refund" panel
		 * 
		 * @return void
		 * 
		 */
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
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
			assignedTablesRefund = new JComboBox(waiter.assignedTableNames());
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
		 * 
		 * @return void
		 * 
		 **/
		
		// Action Listener
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == backButton)
			{
				new LoginWindow();
				notification.close();
				waiter.disconnect();
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
			if(a == assignedTablesQueue)
			{
				updateManageOrderQueueTable(assignedTablesQueue.getSelectedItem());
			}
			if(a == assignedTables)
			{
			if(assignedTables.getSelectedItem() != ""){
				payWithCash.setEnabled(true);
				payWithCard.setEnabled(true);
				updateAcceptPaymentTable(assignedTables.getSelectedItem());
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
				String tableToChange = (String)assignedTablesBox.getSelectedItem();
				String newStatus = (String)tableStatusBox.getSelectedItem();
				assignedTablesBox.setSelectedIndex(0);
				tableStatusBox.setSelectedIndex(0);
				if(!tableToChange.equals("") && !newStatus.equals("")){
					if(notification.sendMessage("Host","Request table status change: "+tableToChange+" to "+newStatus)==0){
						JOptionPane.showMessageDialog(null, "Request was successfully sent to Host!","InfoBox", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
						JOptionPane.showMessageDialog(null, "Request failed to send!","InfoBox", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
			if(a == assignedTablesRefund)
			{
				updateRefundTable(assignedTablesRefund.getSelectedItem());
			}
			if(a == submitRefundRequest)
			{
				Vector<Vector> v = new Vector<Vector>();
				Vector temp = new Vector();
				String currentTable = (String) assignedTablesRefund.getSelectedItem();
				Vector<String> items = new Vector<String>();
				Vector<Integer> seatNumbers = new Vector<Integer>();
				Vector<Integer> quantities = new Vector<Integer>();
				Vector<String> prices = new Vector<String>();
				for(int i = 0;i < tablemodel3.getRowCount();i++){
					if(tablemodel3.getValueAt(i,4)!=null){
						if(((Boolean)tablemodel3.getValueAt(i,4))==true){
							items.add((String) tablemodel3.getValueAt(i,1));
							seatNumbers.add((Integer) tablemodel3.getValueAt(i,0));
							quantities.add((Integer) tablemodel3.getValueAt(i,2));
							prices.add((String) tablemodel3.getValueAt(i,3));
							temp.add((Integer) tablemodel3.getValueAt(i,0));
							temp.add((String) tablemodel3.getValueAt(i,1));
							temp.add((Integer) tablemodel3.getValueAt(i,2));
							v.add(temp);
							temp = new Vector();
						}
					}
				}
				StringBuilder s = new StringBuilder();
				s.append("<html>*********** REFUND REQUEST: "+currentTable+" ***********<br><br>");
				for(int i = 0;i < items.size();i++){
					s.append("SEAT ");
					s.append(seatNumbers.get(i));
					s.append(" ITEM ");
					s.append(items.get(i));
					s.append(" QUANTITY ");
					s.append(quantities.get(i));
					s.append(" PRICE ");
					s.append(prices.get(i));
					s.append("<br>Reason: ");
					s.append(refundInput.getText());
					s.append("<br>");
				}
				s.append("</html>");
				waiter.changeItemStatus(v, waiter.parseTableName(currentTable), "REFUND");
				if(notification.sendMessage("Manager", s.toString())==0 && items.size()!=0){
					JOptionPane.showMessageDialog(null, "Request was sent to Manager!","InfoBox", JOptionPane.INFORMATION_MESSAGE);
					refundInput.setText("");
				}
				else{
					JOptionPane.showMessageDialog(null, "Request failed to send!","InfoBox", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(a == statusChangeHelp)
			{
				JLabel title3 = new JLabel("What is this for?");
				title3.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body8 = new JLabel("This interface allows you to request the status change of a table when a customer moves tables or leaves");
				JLabel title = new JLabel("How to request a status change for a table:");
				title.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel space = new JLabel(" ");
				JLabel body1 = new JLabel("1) Choose the table you want to change the status of from the first drop-down menu");
				JLabel body2 = new JLabel("2) Choose the new status you want from the second drop-down menu");
				JLabel body3 = new JLabel("3) Hit the \"Send Request to Host\" button to push the request");
				JLabel space1 = new JLabel(" ");
				JLabel title2 = new JLabel("Why am I having trouble?");
				title2.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body4 = new JLabel("1) You may not be connected to the restaurant database (Contact system admin)");
				Object[] message = {
					    title3,
					    body8,
					    space,
						title, 
					    body1,
					    body2,
					    body3,
					    space1,title2,body4
					};
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == orderQueueHelp)
			{
				JLabel title3 = new JLabel("What is this for?");
				title3.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body8 = new JLabel("This interface allows you to change the status of an order when it is moved from the kitchen to the dining room, or vice versa");
				JLabel title1 = new JLabel("How to change the status of an order:");
				title1.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body1 = new JLabel("1) Choose the table you want to change the status of from the first drop-down menu");
				JLabel body2 = new JLabel("2) Choose the new status you want from the second drop-down menu");
				JLabel body3 = new JLabel("3) Select the checkboxes next to the order you want to change or select all");
				JLabel body4 = new JLabel("4) Hit \"Confirm\" to sync the changes to all employees");
				JLabel space = new JLabel(" ");
				JLabel space1 = new JLabel(" ");
				JLabel title2 = new JLabel("Why am I having trouble?");
				title2.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body5 = new JLabel("1) Waiters can only change the statuses of orders in READY or SERVED status");
				JLabel body6 = new JLabel("2) You may not have a table, new status or an order selected");
				JLabel body7 = new JLabel("3) You may not be connected to the restaurant database (Contact system admin)");
				Object[] message = {
					    title3,
					    body8,
					    space1,
						title1, 
					    body1,
					    body2,
					    body3,
					    body4,
					    space,
					    title2,
					    body5,
					    body6,
					    body7
					};
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == paymentHelp)
			{
				JLabel title1 = new JLabel("What is this for?");
				title1.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body1 = new JLabel("This interface allows you to accept a payment from a customer via cash or card");
				JLabel space = new JLabel(" ");
				JLabel title2 = new JLabel("How to accept a payment from the customer:");
				title2.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body2 = new JLabel("1) Choose the table of the customer who wants to pay from the drop-down menu");
				JLabel body3 = new JLabel("2) View the balance and hit either \"Pay with Cash\" or \"Pay with Card\"");
				JLabel body4 = new JLabel("3) Enter the payment amount into the pop-up number pad and hit \"Submit\"");
				JLabel body5 = new JLabel("4) You will receive a pop-up with the outcome of the payment");
				JLabel space1 = new JLabel(" ");
				JLabel title3 = new JLabel("Why am I having trouble?");
				title3.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body6 = new JLabel("1) You may be trying to enter a number < 0.01");
				JLabel body7 = new JLabel("2) You may be trying to enter a number > the current balance");
				JLabel body8 = new JLabel("3) You may not be connected to the restaurant database (Contact system admin)");
				Object[] message = {
					    title1,body1,space,
					    title2,body2,body3,body4,body5,space1,
					    title3,body6,body7,body8
					};
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == refundHelp)
			{
				JLabel title1 = new JLabel("What is this for?");
				title1.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body1 = new JLabel("This interface allows you request a refund on behalf of the customer");
				JLabel space = new JLabel(" ");
				JLabel title2 = new JLabel("How to request a refund:");
				title2.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body2 = new JLabel("1) Choose the table of the customer who wants the refund from the drop-down menu");
				JLabel body3 = new JLabel("2) Select the checkbox of the item that the customer is requesting the refund for");
				JLabel body4 = new JLabel("3) Enter the customer's reason for requesting a refund");
				JLabel body5 = new JLabel("4) Hit \"Submit Request to Manager\" to alert Manager of the situation");
				JLabel body6 = new JLabel("5) A pop-up will display showing the outcome of the push request");
				JLabel space1 = new JLabel(" ");
				JLabel title3 = new JLabel("Why am I having trouble?");
				title3.setFont(new Font("Tahoma", Font.BOLD, 13));
				JLabel body7 = new JLabel("1) You may not be connected to the restaurant database (Contact system admin)");
				Object[] message = {
					    title1,body1,space,
					    title2,body2,body3,body4,body5,body6,space1,
					    title3,body7
					};
				JOptionPane.showMessageDialog(null, message, "Help", JOptionPane.INFORMATION_MESSAGE);
			}
			if(a == btnSelectAll)
			{
				if(select == false){
					for(int y = 0; y < tablemodel2.getRowCount(); y++){
						if(!orderDisplay2.getValueAt(y, 1).equals("") && !orderDisplay2.getValueAt(y, 1).equals("BALANCE") &&
								!((String)(orderDisplay2.getValueAt(y, 1))).contains("Payment")){
							tablemodel2.setValueAt(true, y, 5);
						}
						else{
							tablemodel2.setValueAt(false, y, 5);
						}
						btnSelectAll.setText("Unselect All");
						select = true;
					}
				}else{
					for(int y = 0; y < tablemodel2.getRowCount(); y++){
							tablemodel2.setValueAt(false, y, 5);
					}
						btnSelectAll.setText("Select All");
						select = false;
					}
				tablemodel2.fireTableDataChanged();
			}
			if(a == statusChangeButton)
			{
				String newStatus = (String)statusChangeBox.getSelectedItem();
				if(!newStatus.equals("") && !((String)assignedTablesQueue.getSelectedItem()).equals("")){
				Boolean valid = true;
				Vector<Vector> data = tablemodel2.getDataVector();
				Vector<Vector> filtered = new Vector<Vector>();
				for(int i = 0; i < data.size(); i++){
					if(((Boolean)data.get(i).get(5))==true){
						filtered.add(data.get(i));
					}
				}
				for(int i = 0; i < filtered.size(); i++){
					if(filtered.get(i).get(4).equals("READY") || filtered.get(i).get(4).equals("SERVED")){
						//Do nothing
					}
					else{
						JOptionPane.showMessageDialog(null, "<html>Status is not READY or SERVED<br>"
								+ "Waiter cannot change any other status, please use Request Refund or contact a Manager","Help", JOptionPane.ERROR_MESSAGE);
						valid = false;
						break;
					}
				}
				if(valid == true){
					waiter.changeItemStatus(filtered,waiter.parseTableName((String)assignedTablesQueue.getSelectedItem()),newStatus);
					JOptionPane.showMessageDialog(null, "Statuses successfully changed!","Info", JOptionPane.INFORMATION_MESSAGE);
					updateManageOrderQueueTable((String)assignedTablesQueue.getSelectedItem());
				}
				}else{
					JOptionPane.showMessageDialog(null, "No status picked or no table selected!","Info", JOptionPane.ERROR_MESSAGE);
				}
			}
			if(a == timer)
			{
				updateClock();
			}
		}
		
		/**
		 * Update clock function
		 * This function refreshes the clock with the current date and time every 1/2 a second
		 * 
		 * @return void
		 * 
		 */


		private void updateClock() {
            dateAndTime.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }
		 
		/**
		  * This function sets up the Accept Payment JTable used to display orders for the first time.
		  * This function is only called during GUI initialization.
		  * 
		  * @return void
		  * 
		  */
		 
		 @SuppressWarnings("rawtypes")
		private void tableSetup(){
			// Table Parameters
			data = new Vector<Vector>();
			columnnames = new Vector<String>();
			columnnames.add("Seat Number");
			columnnames.add("Menu Item");
			columnnames.add("Quantity");
			columnnames.add("Price");
			tablemodel = new DefaultTableModel() {

				private static final long serialVersionUID = 1L;

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
		   * 
		   * @return void
		   * 
		   */
		 
		 @SuppressWarnings({ "rawtypes", "unchecked" })
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
			tablemodel2 = new DefaultTableModel(){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getColumnClass(int column) {
					
					switch (column) {

					case 5:
						
						return Boolean.class;

					default:

						return String.class;
					}
				}
	
			};
			tablemodel2.setDataVector(data2,columnnames2);
			orderDisplay2 = new JTable(tablemodel2){
				
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int rowIndex, int colIndex) {
				
					if(orderDisplay2.getValueAt(rowIndex, 1).equals("")){
						return false;
					}
	                return (colIndex == 5);//Disallow the editing of any cell except JCheckBox
	            }

			};
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
			for(int x=0;x<tablemodel2.getColumnCount()-1;x++){
				orderDisplay2.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
		      }
		 }
		 
		 /**
		   * This function sets up the Manage Order Queue JTable used to display orders for the first time.
		   * This function is only called during GUI initialization.
		   * 
		   * @return void
		   * 
		   */
		 
		 @SuppressWarnings({ "unchecked", "rawtypes" })
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

				private static final long serialVersionUID = 1L;

				@Override
				public Class<?> getColumnClass(int column) {
					
					switch (column) {

					case 4:
						
						return Boolean.class;

					default:

						return String.class;
					}
				}
			};
			tablemodel3.setDataVector(data3,columnnames3);
			orderDisplay3 = new JTable(tablemodel3){
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public boolean isCellEditable(int rowIndex, int colIndex) {
				
					if(orderDisplay3.getValueAt(rowIndex, 1).equals("")){
						return false;
					}
	                return (colIndex == 4);//Disallow the editing of any cell except JCheckBox
	            }

			};
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
		 * Updates the JTable located in the Accept Payment panel
		 * 
		 * @param String tableName
		 * @return void
		 * 
		 */
		 
		private void updateAcceptPaymentTable(Object tableName) 
		{
			int tableNumber = waiter.parseTableName((String)tableName);
			tablemodel.setDataVector(waiter.getAcceptPaymentDataVector(tableNumber),columnnames);
			tablemodel.fireTableDataChanged();		
		}
		
		/**
		 * Updates the JTable located in the Manage Order Queue panel
		 * 
		 * @param String tableName
		 * @return void
		 * 
		 */
		
		private void updateManageOrderQueueTable(Object tableName)
		{
			int tableNumber = waiter.parseTableName((String)tableName);
			Vector<Vector> v = waiter.getManageOrderQueueDataVector(tableNumber);
			for(int i = 0; i < v.size(); i++){
				if(v.get(i).get(1).equals("") || v.get(i).contains("BALANCE") || v.get(i).contains("Payment - Card") || 
						v.get(i).contains("Payment - Cash") || v.get(i).contains("PAID")){
					v.remove(i);
					i--;
				}
			}
			tablemodel2.setDataVector(v,columnnames2);
			tablemodel2.fireTableDataChanged();	
			orderDisplay2.repaint();
		}
		
		/**
		 * Updates the JTable located in the Request Refund panel
		 * 
		 * @param String tableName
		 * @return void
		 * 
		 */
		
		private void updateRefundTable(Object tableName)
		{
			int tableNumber = waiter.parseTableName((String)tableName);
			Vector<Vector> v = waiter.getRefundTableDataVector(tableNumber);
			
			for(int i = 0; i < v.size(); i++){
				if(v.get(i).get(1).equals("") || v.get(i).contains("BALANCE") || v.get(i).contains("Payment - Card") || 
						v.get(i).contains("Payment - Cash")){
					v.remove(i);
					i--;
				}
			}
			tablemodel3.setDataVector(v,columnnames3);
			tablemodel3.fireTableDataChanged();		
		}
		
				
		 
		 /**
		   * Launches the number pad for use with the makePayemnt() function located
		   * in the Waiter Handler
		   * 
		   * @return void
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
	            	int valid = -1;
	            	numberPad.setVisible(false);
	            	if(paymentButtonPressed==0){
	            		valid = waiter.makePayment(waiter.parseTableName((String)assignedTables.getSelectedItem()),numberPad.get(),"Cash");
	            	}
	            	else{
	            		valid = waiter.makePayment(waiter.parseTableName((String)assignedTables.getSelectedItem()),numberPad.get(),"Card");
	            	}
	            	updateAcceptPaymentTable((String)assignedTables.getSelectedItem());
	            	if(valid == 0){
	            		JOptionPane.showMessageDialog(null, "Payment successfully processed!","Info", JOptionPane.INFORMATION_MESSAGE);
	            	}
	            	else{
	            		JOptionPane.showMessageDialog(null, "Failed to process payment!","Info", JOptionPane.ERROR_MESSAGE);
	            	}
	            	payWithCash.setEnabled(true);
	                payWithCard.setEnabled(true);
	            }
	        });
			
			
		 }
}