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
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.SwingConstants;

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

	public void start(String username, String chatname, int myport) {

		this.username = username;
		this.chatname = chatname;

		lblUsername.setText("UserName: " + username);
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
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);

		txtMessagge = new JTextField();
		txtMessagge.setBounds(25, 460, 590, 30);
		add(txtMessagge);
		txtMessagge.setColumns(10);

		JButton btnSendMessagge = new JButton("INVIA");
		btnSendMessagge.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnSendMessagge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				out.println(txtMessagge.getText());
			}
		});
		btnSendMessagge.setForeground(SystemColor.inactiveCaptionBorder);
		btnSendMessagge.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSendMessagge.setBackground(new Color(0, 0, 51));
		btnSendMessagge.setBounds(625, 458, 100, 30);
		add(btnSendMessagge);

		txtMessaggeArea = new JTextArea();
		txtMessaggeArea.setBounds(25, 55, 700, 395);
		add(txtMessaggeArea);

		lblChatname = new JLabel("ChatName: ");
		lblChatname.setBounds(25, 11, 293, 14);
		add(lblChatname);

		lblUsername = new JLabel("UserName: ");
		lblUsername.setBounds(25, 36, 293, 14);
		add(lblUsername);

	}
}
