package FrontEnd.Panels;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class FriendPanel extends JPanel {

	public FriendPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblFriendsPanel = new JLabel("Friends panel");
		lblFriendsPanel.setBounds(269, 228, 192, 14);
		add(lblFriendsPanel);

	}

}
