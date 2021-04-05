package FrontEnd.Panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Cursor;

import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

import DBClasses.UserData;
import DBConnection.DBCalls;

import javax.swing.JTextArea;

public class ChatPanel extends JPanel {
	public JTextField txtMessagge;
	public JTextArea txtMessaggeArea;
	public JLabel lblChatname;
	public JLabel lblUsername;

	private Socket clientSocket;
	private static int PORT = 4444;
	private static PrintWriter out;
	private static String username;
	private static String chatname;
	private static boolean connected = false;

	public void start(UserData userdata, String chatname, int myport) {

		this.username = userdata.username;
		this.chatname = chatname;

		lblUsername.setText("UserName: " + userdata.username);
		lblChatname.setText("ChatName: " + chatname);

		try {
			if (!clientSocket.isClosed()) {
				clientSocket.close();
			}

			clientSocket = new Socket("localhost", myport);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			new Thread(new Listener()).start();
			out.println(username + ";" + chatname);
			connected = true;
		} catch (Exception err) {
			System.out.println("Client conn err: "+err);
		}
	}

	private class Listener implements Runnable {
		private BufferedReader in;

		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for (;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty()))
						System.out.println(read);
						txtMessaggeArea.append("\r\n" + read.replace("^", "\r\n"));
				}
			} catch (IOException e) {
				return;
			}
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
			}
		});
		btnSendMessagge.setForeground(SystemColor.inactiveCaptionBorder);
		btnSendMessagge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSendMessagge.setBackground(new Color(0, 0, 51));
		btnSendMessagge.setBounds(620, 438, 100, 30);
		add(btnSendMessagge);

		txtMessaggeArea = new JTextArea();
		txtMessaggeArea.setEditable(false);
		txtMessaggeArea.setBounds(20, 55, 700, 370);
		add(txtMessaggeArea);

		lblChatname = new JLabel("ChatName: ");
		lblChatname.setBounds(20, 15, 293, 14);
		add(lblChatname);

		lblUsername = new JLabel("UserName: ");
		lblUsername.setBounds(20, 35, 293, 15);
		add(lblUsername);

	}
}
