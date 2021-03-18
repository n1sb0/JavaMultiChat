package FrontEnd;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Color;

public class ChatPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public ChatPanel() {
		setBackground(Color.WHITE);
		setLayout(null);
		
		JLabel lblChatPanel = new JLabel("Chat PANEL");
		lblChatPanel.setBackground(Color.WHITE);
		lblChatPanel.setBounds(298, 188, 192, 14);
		add(lblChatPanel);

	}
}
