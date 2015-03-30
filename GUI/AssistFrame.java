package Waiter.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Waiter.WaiterMainGUI;
import ADT.Gradients.*;


public class AssistFrame extends JFrame implements ActionListener{

	/**
	 * This .java file creates a sub-GUI for the Waiter class.
	 * This sub-GUI handles the Customer Assist Frame
	 * author Samuel Baysting
	 **/
	
		//Parent Windows
		public final WaiterMainGUI parent;
		//Swing Variables
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel;
		private JButton backButton,messengerButton,clockInOutButton,manageButton,assistButton;
		//private GradientButton logoutButton;
		private JLabel titleLabel,dateAndTime;
		//Other Variables
		private Timer timer;
		
		/**
		 * This function initializes the GUI and runs the constructor for JFrame and the Waiter class
		 * @returns none
		 **/
		
		public AssistFrame(WaiterMainGUI gui)
		{
			super();
			parent = gui;
			init();
		}

		/**
		 * This function sets the parameters for the given JFrame
		 * @returns none
		 **/

		public void init()
		{
			this.setTitle("Customer Assistance Interface");
			this.setResizable(true);
			this.setSize(900,600);
			this.frameManipulation();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setLocationRelativeTo(null);
			this.setResizable(false);
			this.add(rootPanel);
			this.setVisible(true);
		}

		/**
		 * This function initializes the JPanels assocaited with the GUI
		 * @returns none
		 **/
		
		public void frameManipulation()
		{
			setBackgroundPanel();
			setTitlePanel();
			setButtonPanel();
			setRootPanel();
		}
		
		/**
		 * This function initializes the rootPanel JPanel associated with this GUI
		 * @returns none
		 **/
		
		private void setRootPanel()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			rootPanel.add(titlePanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(backgroundPanel);
		}
		
		/**
		 * This function initializes the background JPanel and sets the color for it
		 * @returns none
		 **/
		
		private void setBackgroundPanel()
		{
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setGradient(Color.white, new Color(112,112,255));
			backgroundPanel.setLayout(null);
			backgroundPanel.setBounds(0,0,900,600);
		}
		
		/**
		 * This function initializes the title panel, date and time
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
			titleLabel = new JLabel("Customer Assistance Interface");
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
		 * This function initializes the button frame and associated JButtons
		 * @returns none
		 **/
		
		private void setButtonPanel()
		{
			// Create Button Panel
			buttonPanel = new JPanel();
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(new Rectangle(0,0,900,600));
			buttonPanel.setOpaque(false);
			// Set Logout Button
			backButton = new JButton("BACK");
			//logoutButton.setGradient(Color.blue,Color.gray.brighter());
			backButton.setOpaque(false);
			backButton.setBounds(new Rectangle(100,500,700,50));
			backButton.addActionListener(this);
			backButton.setFont(backButton.getFont().deriveFont(16.0f));
			backButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			backButton.setFocusPainted(false);
			// Set Messenger Button
			messengerButton = new JButton("MESSENGER");
			messengerButton.setBounds(new Rectangle(150,120,270,120));
			messengerButton.addActionListener(this);
			messengerButton.setFont(messengerButton.getFont().deriveFont(16.0f));
			messengerButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			messengerButton.setFocusPainted(false);
			// Set Clock In/Out Button
			clockInOutButton = new JButton("CLOCK IN/OUT");
			clockInOutButton.setBounds(new Rectangle(150,(500-120-120+55),270,120));
			clockInOutButton.addActionListener(this);
			clockInOutButton.setFont(clockInOutButton.getFont().deriveFont(16.0f));
			clockInOutButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			clockInOutButton.setFocusPainted(false);
			// Set Manage Tables Button
			manageButton = new JButton("MANAGE TABLES");
			manageButton.setBounds(new Rectangle((900-150-270),120,270,120));
			manageButton.addActionListener(this);
			manageButton.setFont(manageButton.getFont().deriveFont(16.0f));
			manageButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			manageButton.setFocusPainted(false);
			// Set Assist Customer Button
			assistButton = new JButton("ASSIST CUSTOMER");
			assistButton.setBounds(new Rectangle((900-150-270),(500-120-120+55),270,120));
			assistButton.addActionListener(this);
			assistButton.setFont(assistButton.getFont().deriveFont(16.0f));
			assistButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			assistButton.setFocusPainted(false);
			// Set Border Line
			
			
			//Add components to Button Panel
			buttonPanel.add(backButton);
			buttonPanel.add(messengerButton);
			buttonPanel.add(clockInOutButton);
			buttonPanel.add(manageButton);
			buttonPanel.add(assistButton);
		}
		
		/**
		 * This is the action listener for all of the JComponents. This decides what function will be run
		 * upon some event related to the given JComponents
		 * @returns none
		 **/
		
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == backButton)
				{
				parent.setVisible(true);
				dispose();
				}
			if(a == messengerButton)
				{
				
				}
				if(a == manageButton)
				{
				
				}
			if(a == assistButton)
				{
				
				
				}
			if(a == clockInOutButton)
				{
				
				
				}
			if(a == timer)
				{
					updateClock();
				}
		}
		
		/**
		 * This function updates the clock.
		 * It is called every half of a second
		 * @returns none
		 **/
		
		private void updateClock() {
            dateAndTime.setText(DateFormat.getDateTimeInstance().format(new Date()));
        }

}
