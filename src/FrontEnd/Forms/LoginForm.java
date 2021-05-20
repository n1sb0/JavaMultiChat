package FrontEnd.Forms;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Image;

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
	private JLabel Logopanel;

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
		frmLogin.setTitle("PlayMe");
		Image icon = new ImageIcon(this.getClass().getResource("/logo.png")).getImage();  
		frmLogin.setIconImage(icon);  
		frmLogin.setBounds(100, 100, 350, 385);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.getContentPane().setLayout(null);
		frmLogin.setLocationRelativeTo(null);
		frmLogin.setResizable(false);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(70, 140, 200, 14);
		frmLogin.getContentPane().add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(70, 195, 200, 14);
		frmLogin.getContentPane().add(lblPassword);
		
		lblCreateNewAcc = new JLabel("- Registra nuovo account");
		lblCreateNewAcc.setBounds(70, 288, 200, 14);	
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
		lblForgetPass.setBounds(70, 313, 200, 14);
		lblForgetPass.addMouseListener(new MouseAdapter()  
		{  
		    public void mouseClicked(MouseEvent e)  
		    {  
		    	OnSendMail_Click();
		    }
		}); 
		frmLogin.getContentPane().add(lblForgetPass);
		
		lblPlaymechat = new JLabel("PlayMe");
		lblPlaymechat.setHorizontalAlignment(SwingConstants.CENTER);
		lblPlaymechat.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblPlaymechat.setBounds(90, 10, 150, 35);
		frmLogin.getContentPane().add(lblPlaymechat);
		
		lblWrongCredentials = new JLabel("Login o password sono sbagliate!");
		lblWrongCredentials.setHorizontalAlignment(SwingConstants.CENTER);
		lblWrongCredentials.setFont(lblWrongCredentials.getFont().deriveFont(lblWrongCredentials.getFont().getStyle() | Font.BOLD));
		lblWrongCredentials.setBounds(70, 115, 200, 14);
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
		btnLogin.setFocusPainted(false);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBackground(Color.DARK_GRAY);
		btnLogin.setBounds(70, 245, 200, 30);
		frmLogin.getContentPane().add(btnLogin);
		
		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtPassword.setBounds(70, 215, 200, 20);
		frmLogin.getContentPane().add(txtPassword);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtEmail.setBounds(70, 160, 200, 20);
		frmLogin.getContentPane().add(txtEmail);
		
//		JButton button = new JButton("2");
//		button.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				txtEmail.setText("pippo@gmail.com");
//				txtPassword.setText("pippo123");
//			}
//		});
//		button.setBounds(284, 218, 39, 23);
//		frmLogin.getContentPane().add(button);
//		
//		JButton button_1 = new JButton("3");
//		button_1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				txtEmail.setText("mario.rossi@gmail.com");
//				txtPassword.setText("mario123");
//			}
//		});
//		button_1.setBounds(284, 256, 39, 23);
//		frmLogin.getContentPane().add(button_1);
//		
//		JButton button_2 = new JButton("1");
//		button_2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				txtEmail.setText("bohdan.sivak@gmail.com");
//				txtPassword.setText("bogdan123");
//			}
//		});
//		button_2.setBounds(285, 181, 39, 23);
//		frmLogin.getContentPane().add(button_2);
		
		Logopanel = new JLabel("");
		Logopanel.setBounds(125, 40, 70, 70);
		Image iconChat = new ImageIcon(this.getClass().getResource("/minilogo.png")).getImage();
		Logopanel.setIcon(new ImageIcon(iconChat));
		frmLogin.getContentPane().add(Logopanel);
	}
}
