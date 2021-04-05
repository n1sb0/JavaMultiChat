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

public class ChatForm {
	
	private static String _UserName = "";

	private JFrame frmChat;
	public static JPanel MainPanel;
	public static ChatPanel chatp;
	public static ChatPanel chatp2;
	public static FriendPanel friendp;
	

	public static void NewChatForm(String name) {
	    _UserName = name;
	    
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
		chatp2 = new ChatPanel();
		friendp = new FriendPanel();
		
		MainPanel.add(chatp2, "chatpanel2");
		MainPanel.add(chatp, "chatpanel");
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
		Leftpanel.setBounds(0, 0, 80, 511);
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
				chatp.start(_UserName,"chat1",4444);
			}
		});
		btnChat.setBounds(15, 20, 50, 50);
		Leftpanel.add(btnChat);
		
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setText(DBCalls.Get_UserName(_UserName));
		lblUserName.setBounds(10, 435, 60, 15);
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
		btnExit.setBounds(25, 455, 30, 30);
		Leftpanel.add(btnExit);
		
	}
}
