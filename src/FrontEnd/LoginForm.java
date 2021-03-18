package FrontEnd;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

import DBConnection.DBCalls;
import Utility.SendEmail;

import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.SystemColor;
import javax.swing.border.MatteBorder;

public class LoginForm {

	private JFrame frmLogin;
	private JLabel lblCreateNewAcc;
	private JLabel lblForgetPass;
	private JLabel lblPlaymechat;
	private JLabel lblWrongCredentials;
	private JButton btnLogin;
	private JPasswordField txtPassword;
	private JTextField txtEmail;

	public static void main(String[] args) {
		NewLoginForm();
	}
	
	public static void NewLoginForm() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm window = new LoginForm();
					window.frmLogin.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginForm() {
		initialize();
	}
	
	private void OnSendMail_Click() {	
		RecoveryPasswordForm.NewRecoveryPasswordForm();
		frmLogin.setVisible(false);
	} 
	
	private boolean CheckUser() {	
		return DBCalls.Get_LoginUser(txtEmail.getText(),txtPassword.getText());
	}
	
	private void OnCreateAcc_Click() {
		RegistreAccForm.NewRegAccForm();
		frmLogin.setVisible(false);
	}
	
	private void OnLoginBtn_Click() {
		if(CheckUser()) {
			ChatForm.NewChatForm(txtEmail.getText());
			frmLogin.setVisible(false);
		}else {
			lblWrongCredentials.setVisible(true);
		}
	}

	private void initialize() {
		frmLogin = new JFrame();
		frmLogin.getContentPane().setBackground(Color.WHITE);
		frmLogin.setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/javax/swing/plaf/metal/icons/ocean/warning.png")));
		frmLogin.setTitle("Login");
		frmLogin.setBounds(100, 100, 350, 319);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(75, 70, 180, 14);
		frmLogin.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(75, 125, 180, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		lblCreateNewAcc = new JLabel("- Registra nuovo account");
		lblCreateNewAcc.setBounds(75, 218, 180, 14);	
		lblCreateNewAcc.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblCreateNewAcc.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	OnCreateAcc_Click();
		    }  
		}); 
		frmLogin.getContentPane().add(lblCreateNewAcc);
		
		lblForgetPass = new JLabel("- Hai dimenticato la password?");
		lblForgetPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblForgetPass.setBounds(75, 243, 180, 14);
		lblForgetPass.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	OnSendMail_Click();
		    }
		}); 
		frmLogin.getContentPane().add(lblForgetPass);
		
		lblPlaymechat = new JLabel("PlayMeChat");
		lblPlaymechat.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlaymechat.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblPlaymechat.setBounds(125, 10, 100, 35);
		frmLogin.getContentPane().add(lblPlaymechat);
		
		lblWrongCredentials = new JLabel("Login o password sono sbagliate!");
		lblWrongCredentials.setFont(lblWrongCredentials.getFont().deriveFont(lblWrongCredentials.getFont().getStyle() | Font.BOLD));
		lblWrongCredentials.setBounds(80, 47, 191, 14);
		frmLogin.getContentPane().add(lblWrongCredentials);
		lblWrongCredentials.setVisible(false);
		lblWrongCredentials.setForeground(Color.RED);
		
		btnLogin = new JButton("LOGIN");
		btnLogin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnLogin.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnLoginBtn_Click();
			}
		});
		btnLogin.setForeground(SystemColor.inactiveCaptionBorder);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setBounds(75, 175, 200, 30);
		frmLogin.getContentPane().add(btnLogin);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtPassword.setBounds(75, 145, 200, 20);
		frmLogin.getContentPane().add(txtPassword);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtEmail.setBounds(75, 90, 200, 20);
		frmLogin.getContentPane().add(txtEmail);
	}
}
