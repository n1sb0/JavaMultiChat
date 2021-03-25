package FrontEnd.Forms;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Toolkit;
import java.sql.Connection;

import javax.swing.JLabel;

import DBConnection.DBConnect;
import FrontEnd.Panels.ChatPanel;
import FrontEnd.Panels.FriendPanel;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.SystemColor;
import java.awt.CardLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.event.ActionEvent;

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

	private void initialize() {
		frmChat = new JFrame();
		frmChat.setTitle("Chat");
		frmChat.setIconImage(Toolkit.getDefaultToolkit().getImage(ChatForm.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
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
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 80, 511);
		frmChat.getContentPane().add(panel);
		panel.setLayout(null);
		
		JButton btnNewButton = new JButton("1");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CardLayout cl = (CardLayout) MainPanel.getLayout();
				cl.show(MainPanel, "chatpanel");
				chatp.start(_UserName,"chat1",4444);
			}
		});
		btnNewButton.setBounds(15, 20, 50, 50);
		panel.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("2");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout) MainPanel.getLayout();
				cl.show(MainPanel, "chatpanel2");
				chatp2.start(_UserName,"chat2",4444);
			}
		});
		btnNewButton_1.setBounds(15, 90, 50, 50);
		panel.add(btnNewButton_1);
		
	}
}
