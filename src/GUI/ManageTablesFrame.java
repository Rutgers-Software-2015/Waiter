package GUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import ADT.*;
import Gradients.*;
import Interface.*;

public class ManageTablesFrame extends JFrame implements ActionListener{

		//Parent Windows
		public final WaiterGUI parent;
		//Waiter Interface
		public Waiter waiter;
		//Parent Panels
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel;
		//Subpanels
		private GradientPanel statusPanel,paymentPanel;
		//Swing Objects
		private JButton backButton,statusButton,orderQueueButton,acceptPaymentButton,refundButton;
		private JButton payWithCash,payWithCard;
		private JLabel titleLabel,dateAndTime,scrollTest,selectedTable,acceptPaymentTitle,orderDisplay;
		private JScrollPane scroll,orderDisplayScroller;
		private JComboBox assignedTables;
		//Other Variables
		private Timer timer;
		private LinkedList<Table> tables;
		
		
		public ManageTablesFrame(WaiterGUI gui)
		{
			super();
			parent = gui;
			waiter = new Waiter();
			init();
		}


		public void init()
		{
			this.setTitle("Manage Tables");
			this.setResizable(true);
			this.setSize(900,600);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			this.add(rootPanel);
			this.setVisible(true);
		}

		public void frameManipulation()
		{
			setBackgroundPanel();
			setTitlePanel();
			setButtonPanel();
			setStatusPanel();
			setPaymentPanel();
			setRootPanel();
		}
		
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
		
		private void setStatusPanel()
		{
			statusPanel = new GradientPanel();
			statusPanel.setLayout(null);
			statusPanel.setBounds(new Rectangle(235,60,655,508));
			statusPanel.setGradient(new Color(255,255,255), new Color(255,180,180));
			statusPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			//Create scroll bar test
			scrollTest = new JLabel();
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
			tables = waiter.getCurrentTables();
			assignedTables = new JComboBox<String>(tables);
			assignedTables.addActionListener(this);
			assignedTables.setBounds(new Rectangle(285,60,200,30));
			// "Select Table" Text Box setup
			selectedTable = new JLabel("Select a Table:");
			selectedTable.setBounds(new Rectangle(60,60,200,30));
			selectedTable.setHorizontalAlignment(JLabel.RIGHT);
			selectedTable.setFont(selectedTable.getFont().deriveFont(14.0f));
			// Order Display Text Box setup
			orderDisplay = new JLabel();
			orderDisplay.setOpaque(true);
			orderDisplay.setBackground(Color.white);
			orderDisplayScroller = new JScrollPane(orderDisplay);
			orderDisplayScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			orderDisplayScroller.getVerticalScrollBar().setUnitIncrement(16);
			orderDisplayScroller.setBounds(new Rectangle(10,105,635,200));
			orderDisplayScroller.setBorder(BorderFactory.createLineBorder(Color.black));
			// Pay with Card button setup
			payWithCard = new JButton("Pay with Credit/Debit");
			payWithCard.setBounds(364,360,220,80);
			payWithCard.setFont(payWithCard.getFont().deriveFont(16.0f));
			payWithCard.setFocusPainted(false);
			payWithCard.setEnabled(false);
			// Pay with Cash button setup
			payWithCash = new JButton("Pay with Cash");
			payWithCash.setBounds(72,360,220,80);
			payWithCash.setFont(payWithCash.getFont().deriveFont(16.0f));
			payWithCash.setFocusPainted(false);
			payWithCash.setEnabled(false);
			// Add components to panel
			paymentPanel.add(acceptPaymentTitle);
			paymentPanel.add(assignedTables);
			paymentPanel.add(selectedTable);
			paymentPanel.add(orderDisplayScroller);
			paymentPanel.add(payWithCard);
			paymentPanel.add(payWithCash);
			
			// Set default panel visibility
			paymentPanel.setVisible(false);
		}
		
		private void setBackgroundPanel()
		{
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setGradient(new Color(255,255,255), new Color(255,110,110));
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,900,600);
		}
		
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
			//backButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/matt.png"));
			//backButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/Nazi-flag.png"));
			
			// Set Request Table Status Change Button
			statusButton = new JButton("<html>Request Table<br />Status Change</html>");
			statusButton.addActionListener(this);
			statusButton.setFont(statusButton.getFont().deriveFont(16.0f));
			statusButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			statusButton.setFocusPainted(false);
			//statusButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/matt.png"));
			//statusButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/Nazi-flag.png"));
			
			// Set Manage Order Queue Button
			orderQueueButton = new JButton("Manage Order Queue");
			orderQueueButton.addActionListener(this);
			orderQueueButton.setFont(orderQueueButton.getFont().deriveFont(16.0f));
			orderQueueButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			orderQueueButton.setFocusPainted(false);
			//orderQueueButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/matt.png"));
			//orderQueueButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/Nazi-flag.png"));
			
			// Set Accept Payment Button
			acceptPaymentButton = new JButton("Accept Payment");
			acceptPaymentButton.addActionListener(this);
			acceptPaymentButton.setFont(acceptPaymentButton.getFont().deriveFont(16.0f));
			acceptPaymentButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			acceptPaymentButton.setFocusPainted(false);
			//acceptPaymentButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/matt.png"));
			//acceptPaymentButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/Nazi-flag.png"));
			
			// Set Request Refund Button
			refundButton = new JButton("Request Refund");
			refundButton.addActionListener(this);
			refundButton.setFont(refundButton.getFont().deriveFont(16.0f));
			refundButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			refundButton.setFocusPainted(false);
			//refundButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/matt.png"));
			//refundButton.setIcon(new ImageIcon("C:/Users/Sam/Downloads/Nazi-flag.png"));
			
			//Add components to Button Panel
			buttonPanel.add(statusButton);
			buttonPanel.add(orderQueueButton);
			buttonPanel.add(acceptPaymentButton);
			buttonPanel.add(refundButton);
			buttonPanel.add(backButton);
		}
		
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
					}
				else{
					payWithCash.setEnabled(false);
					payWithCard.setEnabled(false);
					}
				}
			if(a == timer)
				{
					updateClock();
				}
		}
		
		private void updateClock() {
            dateAndTime.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }

}
