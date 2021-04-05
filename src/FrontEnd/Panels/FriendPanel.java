package FrontEnd.Panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;

public class FriendPanel extends JPanel {

	public FriendPanel(String username) {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblFriendsPanel = new JLabel("benvenuto nlla nostra app.");
		lblFriendsPanel.setHorizontalAlignment(SwingConstants.CENTER);
		lblFriendsPanel.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFriendsPanel.setBounds(220, 215, 300, 50);
		add(lblFriendsPanel);
		
		JLabel lblUserName = new JLabel("");
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblUserName.setBounds(50, 266, 640, 50);
		lblUserName.setText(username);
		add(lblUserName);

	}

}
