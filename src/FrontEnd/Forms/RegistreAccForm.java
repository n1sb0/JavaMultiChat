package FrontEnd.Forms;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JTextField;

import DBConnection.DBCalls;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

public class RegistreAccForm {

	private JFrame frmRegistraAccount;
	private JCheckBox chkAuth;
	private JLabel btnGoBack;
	private JButton btnInvia;
	private JTextField txtUserName;
	private JLabel lblUsername;
	private JLabel lblEmail;
	private JTextField txtEmail;
	private JLabel lblEmail_2;
	private JTextField txtNome;
	private JLabel lblEmail_3;
	private JTextField txtCognome;
	private JLabel lblEmail_4;
	private JPasswordField txtPassword;
	private JPasswordField txtConfPassword;
	private JLabel lblEmail_1;
	private JLabel lblCreaNuovoAccount;

	public static void  NewRegAccForm() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegistreAccForm window = new RegistreAccForm();
					window.frmRegistraAccount.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void RegisterNewaccount() throws Exception {
		DBCalls.Insert_RegistreNewAcc(txtUserName.getText(), txtNome.getText(), txtCognome.getText(), txtEmail.getText(), txtPassword.getText());
		ChatForm.NewChatForm(txtEmail.getText());
		frmRegistraAccount.setVisible(false);
	}
	
	private void OnGoBack_Click() {
		LoginForm.NewLoginForm();
		frmRegistraAccount.setVisible(false);	
	} 

	public RegistreAccForm() {
		initialize();
	}

	private void initialize() {
		frmRegistraAccount = new JFrame();
		frmRegistraAccount.getContentPane().setBackground(Color.WHITE);
		frmRegistraAccount.setTitle("Registra Account");
		frmRegistraAccount.setBounds(100, 100, 400, 550);
		frmRegistraAccount.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRegistraAccount.getContentPane().setLayout(null);
		frmRegistraAccount.setLocationRelativeTo(null);
		frmRegistraAccount.setResizable(false);
		
		chkAuth = new JCheckBox("Autorizzo tutti i miei dati ");
		chkAuth.setBackground(Color.WHITE);
		chkAuth.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		chkAuth.setBounds(100, 402, 200, 23);
		frmRegistraAccount.getContentPane().add(chkAuth);
		
		btnGoBack = new JLabel("<");
		btnGoBack.setForeground(Color.DARK_GRAY);
		btnGoBack.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnGoBack.setBounds(10, 11, 25, 25);
		btnGoBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGoBack.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	OnGoBack_Click();
		    }
		}); 
		frmRegistraAccount.getContentPane().add(btnGoBack);
		
		btnInvia = new JButton("INVIA");
		btnInvia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RegisterNewaccount();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btnInvia.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnInvia.setForeground(SystemColor.inactiveCaptionBorder);
		btnInvia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInvia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInvia.setBackground(Color.DARK_GRAY);
		btnInvia.setBounds(100, 442, 200, 30);
		btnInvia.setFocusPainted(false);
		frmRegistraAccount.getContentPane().add(btnInvia);
		
		txtUserName = new JTextField();
		txtUserName.setColumns(10);
		txtUserName.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtUserName.setBounds(100, 110, 200, 20);
		frmRegistraAccount.getContentPane().add(txtUserName);
		
		lblUsername = new JLabel("Username");
		lblUsername.setBounds(100, 90, 200, 14);
		frmRegistraAccount.getContentPane().add(lblUsername);
		
		lblEmail = new JLabel("Email");
		lblEmail.setBounds(100, 141, 200, 14);
		frmRegistraAccount.getContentPane().add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtEmail.setBounds(100, 161, 200, 20);
		frmRegistraAccount.getContentPane().add(txtEmail);
		
		lblEmail_2 = new JLabel("Nome");
		lblEmail_2.setBounds(100, 192, 200, 14);
		frmRegistraAccount.getContentPane().add(lblEmail_2);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtNome.setBounds(100, 212, 200, 20);
		frmRegistraAccount.getContentPane().add(txtNome);
		
		lblEmail_3 = new JLabel("Cognome");
		lblEmail_3.setBounds(100, 243, 200, 14);
		frmRegistraAccount.getContentPane().add(lblEmail_3);
		
		txtCognome = new JTextField();
		txtCognome.setColumns(10);
		txtCognome.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtCognome.setBounds(100, 263, 200, 20);
		frmRegistraAccount.getContentPane().add(txtCognome);
		
		lblEmail_4 = new JLabel("Password");
		lblEmail_4.setBounds(100, 294, 200, 14);
		frmRegistraAccount.getContentPane().add(lblEmail_4);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtPassword.setBounds(100, 314, 200, 20);
		frmRegistraAccount.getContentPane().add(txtPassword);
		
		txtConfPassword = new JPasswordField();
		txtConfPassword.setColumns(10);
		txtConfPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtConfPassword.setBounds(100, 365, 200, 20);
		frmRegistraAccount.getContentPane().add(txtConfPassword);
		
		lblEmail_1 = new JLabel("Conferma password");
		lblEmail_1.setBounds(100, 345, 200, 14);
		frmRegistraAccount.getContentPane().add(lblEmail_1);
		
		lblCreaNuovoAccount = new JLabel("Crea nuovo account");
		lblCreaNuovoAccount.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreaNuovoAccount.setForeground(Color.DARK_GRAY);
		lblCreaNuovoAccount.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblCreaNuovoAccount.setBounds(85, 20, 215, 41);
		frmRegistraAccount.getContentPane().add(lblCreaNuovoAccount);
	}
}
