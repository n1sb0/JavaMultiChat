package FrontEnd.Forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.sql.Connection;

import javax.swing.JLabel;

import DBConnection.DBCalls;
import DBConnection.DBConnect;
import FrontEnd.Panels.ChatPanel;
import FrontEnd.Panels.FriendPanel;
import FrontEnd.Panels.SettingsPanel;

import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.CardLayout;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;

import DBClasses.UserData;
import javax.swing.JTextField;

public class ChatForm {

	private JFrame frmChat;
	public static JPanel MainPanel;
	public static ChatPanel chatp;
	public static SettingsPanel settingsp;
	public static FriendPanel friendp;
	private JButton btnSettings;
	private static UserData userdata;
	private static boolean chatstatus = false;
	

	public static void NewChatForm(String email) {
	    userdata = DBCalls.Get_AllUserData(email,0);
	    
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ChatForm window = new ChatForm();
					window.frmChat.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void MyCardsLayouts() {
		chatp = new ChatPanel();
		chatp.txtMessaggeArea.setBounds(20, 79, 700, 350);
		chatp.lblChatname.setBounds(20, 23, 105, 14);
		chatp.lblUsername.setBounds(20, 51, 93, 15);
		settingsp = new SettingsPanel(userdata);
		friendp = new FriendPanel(userdata.username);
		
		MainPanel.add(chatp, "chatpanel");
		MainPanel.add(settingsp, "settingspanel");
		MainPanel.add(friendp, "friendspanel");	
	}
	
	private void CallMyPanel() {
		CardLayout cl = (CardLayout) MainPanel.getLayout();
		cl.show(MainPanel, "friendspanel");
	}

	public ChatForm() throws Exception {
		initialize();	
	}
	
	private void OnGoBack_Click() {
		LoginForm.NewLoginForm();
		frmChat.setVisible(false);	
	} 
	
	private void Open_FormSettings() {
		CardLayout cl = (CardLayout) MainPanel.getLayout();
		cl.show(MainPanel, "settingspanel");
	}

	private void initialize() {
		frmChat = new JFrame();
		frmChat.setTitle("Chat");
		frmChat.setBounds(100, 100, 834, 527);
		frmChat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmChat.setLocationRelativeTo(null);
		frmChat.getContentPane().setLayout(null);
		frmChat.setResizable(false);
		
		MainPanel = new JPanel();
		MainPanel.setBackground(Color.WHITE);
		MainPanel.setBounds(80, 0, 750, 500);
		MainPanel.setLayout(new CardLayout());	
		MyCardsLayouts();
		CallMyPanel();
		frmChat.getContentPane().add(MainPanel);
		
		JPanel Leftpanel = new JPanel();
		Leftpanel.setBackground(SystemColor.control);
		Leftpanel.setBounds(0, 0, 80, 498);
		frmChat.getContentPane().add(Leftpanel);
		Leftpanel.setLayout(null);
			
		JButton btnChat = new JButton();
		Border emptyBorder = BorderFactory.createEmptyBorder();
		btnChat.setBorder(emptyBorder);
		btnChat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnChat.setBackground(SystemColor.control);
		Image iconChat = new ImageIcon(this.getClass().getResource("/chat.png")).getImage();
		btnChat.setIcon(new ImageIcon(iconChat));
		btnChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
							
				CardLayout cl = (CardLayout) MainPanel.getLayout();
				cl.show(MainPanel, "chatpanel");
				
				if (!chatstatus) {
					chatp.start(userdata,"chat comune",4444);
					chatstatus = true;
				}			
			}
		});
		btnChat.setBounds(15, 20, 50, 50);
		Leftpanel.add(btnChat);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setText(userdata.username);
		lblUserName.setBounds(10, 415, 60, 15);
		Leftpanel.add(lblUserName);
		
		JButton btnExit = new JButton();
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnGoBack_Click();
			}
		});
		btnExit.setBorder(emptyBorder);
		btnExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnExit.setBackground(SystemColor.control);
		Image iconExit = new ImageIcon(this.getClass().getResource("/exit.png")).getImage();
		btnExit.setIcon(new ImageIcon(iconExit));
		btnExit.setBackground(SystemColor.menu);
		btnExit.setBounds(30, 465, 20, 20);
		Leftpanel.add(btnExit);
		
		btnSettings = new JButton();
		btnSettings.setBorder(emptyBorder);
		btnSettings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSettings.setBackground(SystemColor.control);
		Image iconSettings = new ImageIcon(this.getClass().getResource("/settings.png")).getImage();
		btnSettings.setIcon(new ImageIcon(iconSettings));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Open_FormSettings();
			}
		});
		btnSettings.setBackground(SystemColor.menu);
		btnSettings.setBounds(28, 435, 25, 25);
		Leftpanel.add(btnSettings);
		
	}
}
