package Waiter;

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

import ADT.Gradients.GradientPanel;
import Login.LoginWindow;
import Waiter.GUI.AssistFrame;
import Waiter.GUI.ManageTablesFrame;
import Waiter.GUI.MessengerFrame;


public class WaiterMainGUI extends JFrame implements ActionListener{

		//Windows
		private ManageTablesFrame manage;
		private MessengerFrame messenger;
		private AssistFrame assist;
		//Swing Variables
		private JPanel rootPanel,titlePanel,buttonPanel;
		private GradientPanel backgroundPanel;
		private JButton logoutButton,messengerButton,clockInOutButton,manageButton,assistButton;
		//private GradientButton logoutButton;
		private JLabel titleLabel,dateAndTime;
		//Other Variables
		private Timer timer;
		
		
		public WaiterMainGUI()
		{
			super();
			init();
		}


		public void init()
		{
			this.setTitle("Waiter GUI");
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
			setRootPanel();
		}
		
		private void setRootPanel()
		{
			rootPanel = new JPanel();
			rootPanel.setLayout(null);
			rootPanel.add(titlePanel);
			rootPanel.add(buttonPanel);
			rootPanel.add(backgroundPanel);
		}
		
		private void setBackgroundPanel()
		{
			// Create Background Panel
			backgroundPanel = new GradientPanel();
			backgroundPanel.setGradient(Color.white,new Color(153,230,255));
			//backgroundPanel.setBrightness(backgroundPanel.getColor2(),1);
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
			titleLabel = new JLabel("Wait Staff Interface");
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
			buttonPanel.setLayout(null);
			buttonPanel.setBounds(new Rectangle(0,0,900,600));
			buttonPanel.setOpaque(false);
			// Set Logout Button
			logoutButton = new JButton("LOGOUT");
			//logoutButton.setGradient(Color.blue,Color.gray.brighter());
			logoutButton.setOpaque(false);
			logoutButton.setBounds(new Rectangle(100,500,700,50));
			logoutButton.addActionListener(this);
			logoutButton.setFont(logoutButton.getFont().deriveFont(16.0f));
			logoutButton.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
			logoutButton.setFocusPainted(false);
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
			
			//Add components to Button Panel
			buttonPanel.add(logoutButton);
			buttonPanel.add(messengerButton);
			buttonPanel.add(clockInOutButton);
			buttonPanel.add(manageButton);
			buttonPanel.add(assistButton);
		}
		
		public void actionPerformed(ActionEvent e) 
		{
			Object a = e.getSource();
			if(a == logoutButton)
				{
					new LoginWindow();
					dispose();
				}
			if(a == messengerButton)
				{
					messenger = new MessengerFrame(this);
					this.setVisible(false);
					messenger.setVisible(true);
				}
			if(a == manageButton)
				{
					manage = new ManageTablesFrame(this);
					this.setVisible(false);
					manage.setVisible(true);
				}
			if(a == assistButton)
				{
					assist = new AssistFrame(this);
					this.setVisible(false);
					assist.setVisible(true);
				}
			if(a == clockInOutButton)
				{
				
				
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
