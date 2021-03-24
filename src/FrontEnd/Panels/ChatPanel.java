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

	private static Socket clientSocket;
	private static int PORT = 4444;
	private static PrintWriter out;
	
	public void start(String username, int myport) {
		try {
			clientSocket = new Socket("localhost", myport);
			out = new PrintWriter(clientSocket.getOutputStream(), true);
			new Thread(new Listener()).start();
			out.println(username);
		} catch (Exception err) {
		}
	}
	
	private class Listener implements Runnable {
		private BufferedReader in;
		@Override
		public void run() {
			try {
				in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
				String read;
				for(;;) {
					read = in.readLine();
					if (read != null && !(read.isEmpty())) txtMessaggeArea.append("\r\n"+read);
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

	}
}
