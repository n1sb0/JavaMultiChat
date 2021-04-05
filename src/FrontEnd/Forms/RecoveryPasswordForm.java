package FrontEnd.Forms;

import java.awt.Cursor;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;

import Utility.SendEmail;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

import java.awt.Color;
import javax.swing.border.MatteBorder;

import DBConnection.DBCalls;

import javax.swing.SwingConstants;
import java.awt.SystemColor;

public class RecoveryPasswordForm {

	private JFrame frmRecuperapassword;
	private JTextField txtEmailTo;
	private JTextField txtCode;
	private JPasswordField txtPassword;
	private JPasswordField txtNewPassword;
	private JButton btnInvia;
	private JButton btnConfermaCode;
	private JButton btnSubmitPass;
	private JLabel lblWarnEmail;
	private JLabel lblWarnCode;
	private JLabel lblInvioDellaMail;

	private int SecureCode = 0;

	public static void NewRecoveryPasswordForm() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RecoveryPasswordForm window = new RecoveryPasswordForm();
					window.frmRecuperapassword.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public RecoveryPasswordForm() {
		initialize();
	}

	private void OnGoBack_Click() {
		LoginForm.NewLoginForm();
		frmRecuperapassword.setVisible(false);
	}

	private void ChangePassword_Click() {
		DBCalls.Update_Password(txtEmailTo.getText(), txtPassword.getText());
		OnGoBack_Click();
	}

	private void SendEmailRecoveryPass() {
		if (txtEmailTo.getText().length() > 6) {
			SecureCode = SendEmail.SendEmailTo(txtEmailTo.getText());
			btnInvia.setEnabled(false);
			txtEmailTo.setEditable(false);
			lblWarnEmail.setVisible(false);
			frmRecuperapassword.setSize(340, 400);
			lblInvioDellaMail.setVisible(false);
		} else {
			lblWarnEmail.setVisible(true);
		}
	}

	private void ConfermaBtn_Click() {
		if (txtCode.getText().contentEquals(Integer.toString(SecureCode))) {
			btnConfermaCode.setEnabled(false);
			txtCode.setEditable(false);
			// lblWarnCode.setVisible(false);
			frmRecuperapassword.setSize(600, 400);
			frmRecuperapassword.setLocationRelativeTo(null);
		} else {
			lblWarnCode.setVisible(true);
		}
	}

	private void initialize() {
		frmRecuperapassword = new JFrame();
		frmRecuperapassword.getContentPane().setBackground(Color.WHITE);
		frmRecuperapassword.setTitle("Recupera Password");
		frmRecuperapassword.setBounds(100, 100, 340, 250);
		frmRecuperapassword.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmRecuperapassword.setLocationRelativeTo(null);
		frmRecuperapassword.getContentPane().setLayout(null);
		frmRecuperapassword.setResizable(false);

		btnInvia = new JButton("INVIA");
		btnInvia.setForeground(SystemColor.inactiveCaptionBorder);
		btnInvia.setBackground(Color.DARK_GRAY);
		btnInvia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInvia.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnInvia.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblInvioDellaMail.setVisible(true);
				SendEmailRecoveryPass();
			}
		});
		btnInvia.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnInvia.setBounds(75, 165, 200, 30);
		btnInvia.setFocusPainted(false);
		frmRecuperapassword.getContentPane().add(btnInvia);

		JLabel btnGoBack = new JLabel("<");
		btnGoBack.setForeground(Color.DARK_GRAY);
		btnGoBack.setFont(new Font("Tahoma", Font.BOLD, 19));
		btnGoBack.setBounds(10, 11, 25, 25);
		btnGoBack.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGoBack.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				OnGoBack_Click();
			}
		});
		frmRecuperapassword.getContentPane().add(btnGoBack);

		txtEmailTo = new JTextField();
		txtEmailTo.setBounds(75, 125, 200, 20);
		txtEmailTo.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		frmRecuperapassword.getContentPane().add(txtEmailTo);
		txtEmailTo.setColumns(10);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(75, 105, 46, 14);
		frmRecuperapassword.getContentPane().add(lblEmail);

		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBounds(10, 220, 314, 1);
		frmRecuperapassword.getContentPane().add(panel);

		JLabel lblRecuperoDellaPassword = new JLabel("Recupero della password");
		lblRecuperoDellaPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblRecuperoDellaPassword.setForeground(Color.DARK_GRAY);
		lblRecuperoDellaPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblRecuperoDellaPassword.setBounds(68, 11, 215, 41);
		frmRecuperapassword.getContentPane().add(lblRecuperoDellaPassword);

		JLabel lblInserisciLaTua = new JLabel("Inserisci la tua email e ti invieremo le istruzioni ");
		lblInserisciLaTua.setHorizontalAlignment(SwingConstants.CENTER);
		lblInserisciLaTua.setBounds(40, 45, 270, 20);
		frmRecuperapassword.getContentPane().add(lblInserisciLaTua);

		JLabel lblPerIlRecupero = new JLabel("per il recupero della password");
		lblPerIlRecupero.setHorizontalAlignment(SwingConstants.CENTER);
		lblPerIlRecupero.setBounds(85, 63, 180, 20);
		frmRecuperapassword.getContentPane().add(lblPerIlRecupero);

		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(75, 243, 46, 14);
		frmRecuperapassword.getContentPane().add(lblCode);

		txtCode = new JTextField();
		txtCode.setColumns(10);
		txtCode.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtCode.setBounds(75, 263, 200, 20);
		frmRecuperapassword.getContentPane().add(txtCode);

		btnConfermaCode = new JButton("CONFERMA");
		btnConfermaCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ConfermaBtn_Click();
			}
		});
		btnConfermaCode.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnConfermaCode.setForeground(SystemColor.inactiveCaptionBorder);
		btnConfermaCode.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfermaCode.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnConfermaCode.setBackground(Color.DARK_GRAY);
		btnConfermaCode.setBounds(75, 303, 200, 30);
		frmRecuperapassword.getContentPane().add(btnConfermaCode);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		panel_1.setBounds(334, 15, 1, 330);
		frmRecuperapassword.getContentPane().add(panel_1);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(359, 105, 65, 14);
		frmRecuperapassword.getContentPane().add(lblPassword);

		txtPassword = new JPasswordField();
		txtPassword.setColumns(10);
		txtPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtPassword.setBounds(359, 125, 200, 20);
		frmRecuperapassword.getContentPane().add(txtPassword);

		JLabel lblNuovaPassword = new JLabel("Nuova password");
		lblNuovaPassword.setBounds(359, 181, 95, 14);
		frmRecuperapassword.getContentPane().add(lblNuovaPassword);

		txtNewPassword = new JPasswordField();
		txtNewPassword.setColumns(10);
		txtNewPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtNewPassword.setBounds(359, 201, 200, 20);
		frmRecuperapassword.getContentPane().add(txtNewPassword);

		JLabel lblPassWarn = new JLabel("Password non coincide!");
		lblPassWarn.setForeground(new Color(220, 20, 60));
		lblPassWarn.setBounds(359, 232, 200, 14);
		lblPassWarn.setVisible(false);
		frmRecuperapassword.getContentPane().add(lblPassWarn);

		btnSubmitPass = new JButton("CAMBIA PASSWORD");
		btnSubmitPass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtPassword.getText().equals("") && txtPassword.getText().equals(txtNewPassword.getText())) {
					ChangePassword_Click();

				} else {
					lblPassWarn.setVisible(true);
				}
			}
		});
		btnSubmitPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSubmitPass.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		btnSubmitPass.setForeground(SystemColor.inactiveCaptionBorder);
		btnSubmitPass.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnSubmitPass.setBackground(Color.DARK_GRAY);
		btnSubmitPass.setBounds(359, 261, 200, 30);
		frmRecuperapassword.getContentPane().add(btnSubmitPass);

		JLabel lblNewPassword = new JLabel("Nuova Password");
		lblNewPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewPassword.setForeground(Color.DARK_GRAY);
		lblNewPassword.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblNewPassword.setBounds(352, 32, 215, 41);
		frmRecuperapassword.getContentPane().add(lblNewPassword);

		lblWarnEmail = new JLabel("*");
		lblWarnEmail.setForeground(new Color(220, 20, 60));
		lblWarnEmail.setHorizontalAlignment(SwingConstants.CENTER);
		lblWarnEmail.setBounds(280, 127, 11, 14);
		lblWarnEmail.setVisible(false);
		frmRecuperapassword.getContentPane().add(lblWarnEmail);

		lblWarnCode = new JLabel("*");
		lblWarnCode.setForeground(new Color(220, 20, 60));
		lblWarnCode.setBounds(280, 265, 11, 14);
		lblWarnCode.setVisible(false);
		frmRecuperapassword.getContentPane().add(lblWarnCode);

		lblInvioDellaMail = new JLabel("Invio della mail...");
		lblInvioDellaMail.setBounds(125, 200, 100, 14);
		frmRecuperapassword.getContentPane().add(lblInvioDellaMail);
		lblInvioDellaMail.setVisible(false);
	}
}
