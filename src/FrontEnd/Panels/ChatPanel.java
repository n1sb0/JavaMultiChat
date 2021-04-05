package FrontEnd.Panels;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.List;
import java.awt.SystemColor;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;

import DBClasses.*;
import DBConnection.DBCalls;

import javax.swing.JTextArea;
import javax.swing.DropMode;

public class ChatPanel extends JPanel {
	public JTextField txtMessagge;
	public JTextArea txtMessaggeArea;
	public JLabel lblChatname;
	public JLabel lblUsername;
	public JScrollPane scrollPane;

	private Socket clientSocket;
	private static PrintWriter out;
	private  JTextField txtChatName;
	private  JTextField txtUserName;
	private JButton btnCambiaNomeChat;
	private JButton btnCambiaUserName;
	private String oldName;
	private UserData userdata;
	
	private static MulticastSocket multiSocket;

	public void start(UserData userdata, String chatname, int myport) {
		this.userdata = userdata;
		GetAllMessages();

		lblUsername.setText("Il tuo Nome:");
		txtUserName.setText(userdata.username);
		lblChatname.setText("Nome della Chat:");
		txtChatName.setText(chatname);
			
		try {
			connect_and_join();
			clientSocket = new Socket("localhost", myport);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			new Thread(new Listener()).start();
			new Thread(new ListnerMultiCast()).start();
			out.println(userdata.email);			
		} catch (Exception err) {
			System.out.println("Client conn err: "+err);
		}
	}
	
	private class ListnerMultiCast implements Runnable{
		
		public void run() {		
			for (;;) {
				receive();
			}			
		}
	}

	private class Listener implements Runnable {
		private BufferedReader in;

		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for (;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty()))
						txtMessaggeArea.append("\r\n" + read.replace("^", "\r\n"));
				}
			} catch (IOException e) {
				return;
			}
		}
	}
	
	public void connect_and_join() {
		try {			
			multiSocket = new MulticastSocket(5000);
			multiSocket.joinGroup(InetAddress.getByName("225.4.5.6"));
			
		}catch(Exception e) {
			e.printStackTrace();

		}
	}
	
	private void receive() {		
		try {
			byte buf[] =new  byte[1024];
			DatagramPacket pack = new DatagramPacket(buf, buf.length);
			
			multiSocket.receive(pack);
			
			String[] s;
			String str = new String(buf);
			
			s = str.split(";");
			
			System.out.println(str);
			
			if(str.contains("chat")) {
				txtMessaggeArea.append("\r\n Nome della chat e' stata cambiata: " + s[0] +" \r\n");
				txtChatName.setText(s[0]);
			}else {
				txtMessaggeArea.append("\r\n Utente "+ oldName + ", ha cambiato il nome: " + s[0] +" \r\n");
				userdata.username = s[0];
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void send(String key) {		
		try {
			String msg = "";
			if(key.equals("chat")) {
				msg = txtChatName.getText() + ";"+key;
			}else {
				msg = txtUserName.getText()+ ";"+key;
				oldName = txtUserName.getText();
			}
			
			DatagramPacket pack = new DatagramPacket(msg.getBytes(), msg.length(),InetAddress.getByName("225.4.5.6"), 5000);
			   
		    multiSocket.send(pack);		  
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void GetAllMessages() {		
        ArrayList<Message> messages = new ArrayList<Message>();
		messages = DBCalls.getAllMessages();
		
		System.out.println(messages);
			
		for (Message message : messages) {
			 UserData userdata = DBCalls.Get_AllUserData("", message.userid);
			 txtMessaggeArea.append("\r\n "+ userdata.username + " " + message.messagedate + " \r\n " + message.message + " \r\n" );
			 
			 userdata = new UserData();
		}
	}
	

	public ChatPanel() {
		setBackground(SystemColor.controlHighlight);
		setLayout(null);

		txtMessagge = new JTextField();
		txtMessagge.setBounds(20, 440, 590, 30);
		add(txtMessagge);
		txtMessagge.setColumns(10);

		JButton btnSendMessagge = new JButton("INVIA");
		btnSendMessagge.setFocusPainted(false);
		btnSendMessagge.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnSendMessagge.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSendMessagge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println(txtMessagge.getText());
				txtMessagge.setText("");
			}
		});
		btnSendMessagge.setForeground(SystemColor.inactiveCaptionBorder);
		btnSendMessagge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSendMessagge.setBackground(new Color(0, 0, 51));
		btnSendMessagge.setBounds(620, 438, 100, 30);
		add(btnSendMessagge);
		


		lblChatname = new JLabel("Nome della Chat:");
		lblChatname.setBounds(20, 25, 105, 14);
		add(lblChatname);

		lblUsername = new JLabel("Il tuo Nome: ");
		lblUsername.setBounds(20, 53, 94, 15);
		add(lblUsername);
		
		txtChatName = new JTextField();
		txtChatName.setColumns(10);
		txtChatName.setBounds(124, 22, 487, 20);
		add(txtChatName);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBounds(123, 48, 487, 20);
		add(txtUserName);
		
		btnCambiaNomeChat = new JButton("Cambia");
		btnCambiaNomeChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send("chat");
			}
		});
		btnCambiaNomeChat.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnCambiaNomeChat.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCambiaNomeChat.setForeground(SystemColor.inactiveCaptionBorder);
		btnCambiaNomeChat.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCambiaNomeChat.setFocusPainted(false);
		btnCambiaNomeChat.setBackground(new Color(0, 0, 51));
		btnCambiaNomeChat.setBounds(620, 21, 100, 20);
		add(btnCambiaNomeChat);
		
		btnCambiaUserName = new JButton("Cambia");
		btnCambiaUserName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				send("name");
			}
		});
		btnCambiaUserName.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnCambiaUserName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCambiaUserName.setForeground(SystemColor.inactiveCaptionBorder);
		btnCambiaUserName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCambiaUserName.setFocusPainted(false);
		btnCambiaUserName.setBackground(new Color(0, 0, 51));
		btnCambiaUserName.setBounds(620, 49, 100, 20);
		add(btnCambiaUserName);
		
		scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		txtMessaggeArea = new JTextArea(29,139);
		txtMessaggeArea.setBounds(20, 79, 700, 350);		
		txtMessaggeArea.setEditable(true);
		DefaultCaret caret = (DefaultCaret)txtMessaggeArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		scrollPane.setBounds(20, 79, 700, 350);
		scrollPane.getViewport().setBackground(Color.WHITE);
		scrollPane.getViewport().add(txtMessaggeArea);
		
		add(scrollPane);
		

	}
}
