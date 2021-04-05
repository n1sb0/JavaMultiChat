package FrontEnd.Panels;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.border.MatteBorder;

import DBClasses.UserData;
import DBConnection.DBCalls;
import Encryption.Encryption;

import javax.swing.JLabel;
import javax.swing.JPasswordField;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SettingsPanel extends JPanel {
	private JTextField txtUsername;
	private JTextField txtEmail;
	private JTextField txtNome;
	private JTextField txtCognome;
	private JPasswordField txtPassword;
	private JPasswordField txtConfirmPassword;
	private JPasswordField txtCurrentPassword;	
	private JLabel lblDatiMSG;
	private JLabel lblPasswordMSG;
	private JButton btnCambiaPassword;
	private JButton btnComfermaPassword;
	private JLabel lblCurrentPssword;
	private JLabel lblCambiaLaPassword;
	private JLabel lblPassword;
	private JLabel lblConfirmPassword;
	
	private UserData userdata;
	private JPanel pnlChangePass;
	
	private void Set_UserData() {
		lblDatiMSG.setVisible(false);
		lblPasswordMSG.setVisible(false);
		
		pnlChangePass.setVisible(false);
		
		txtUsername.setText(userdata.username);
		txtNome.setText(userdata.nome);
		txtCognome.setText(userdata.cognome);
		txtEmail.setText(userdata.email);
	}
	
	private boolean checkPass() {
		return Encryption.verifyUserPassword(txtCurrentPassword.getText(), userdata.password, userdata.salt);
	}
	
	private void Show_ChangePasswordPanel() {
		if(checkPass()) {
			pnlChangePass.setVisible(true);
			txtPassword.setText("");
			txtConfirmPassword.setText("");
			lblPasswordMSG.setVisible(false);
		}else {
			lblPasswordMSG.setText("Attenzione: la password errata.");
			lblPasswordMSG.setForeground(Color.red);
			lblPasswordMSG.setVisible(true);
		}
	}
	
	private void OnChangePassword() {
		if(txtPassword.getText().equals(txtConfirmPassword.getText())){
			Change_UserPassword();
		}else {
			lblPasswordMSG.setText("Attenzione: la password non coincide.");
			lblPasswordMSG.setForeground(Color.red);
			lblPasswordMSG.setVisible(true);
		}
	}
	
	private void Change_UserPassword() {
		
		boolean res = DBCalls.ChangePassword(userdata.email, txtPassword.getText());
		
		if (res){
			userdata = DBCalls.Get_AllUserData(userdata.email,0);
			lblPasswordMSG.setText("La password \u00E8 stata cambiata corretamente.");
			lblPasswordMSG.setForeground(Color.green);
			lblPasswordMSG.setVisible(true);
			pnlChangePass.setVisible(false);
			txtCurrentPassword.setText("");
		}else {
			lblPasswordMSG.setText("Attenzione: errore, impossibile cambiare la password.");
			lblPasswordMSG.setForeground(Color.orange);
			lblPasswordMSG.setVisible(true);
		}
		
	}
	
	public void Change_UserData() {
		if(CheckNullOrEmptyData()) {
			if(Update_UserData()) {			
				lblDatiMSG.setText("I dati sono stati cambiati corretamente.");
				lblDatiMSG.setForeground(Color.green);
				lblDatiMSG.setVisible(true);
			}else {
				lblDatiMSG.setText("Attenzione: errore, impossibile cambiare i dati.");
				lblDatiMSG.setForeground(Color.orange);
				lblDatiMSG.setVisible(true);
			}
		}else {		
			lblDatiMSG.setText("Attenzione: dati errati.");
			lblDatiMSG.setForeground(Color.red);
			lblDatiMSG.setVisible(true);
		}
	}
	
	private boolean Update_UserData() {
		userdata.username = txtUsername.getText();
		userdata.email = txtEmail.getText();
		userdata.nome = txtNome.getText();
		userdata.cognome = txtCognome.getText();
		
		return DBCalls.Update_UserData(userdata);
	}

	public boolean CheckNullOrEmptyData() {
		
		if(!txtUsername.getText().equals("") && !txtEmail.getText().equals("") && !txtNome.getText().equals("") && !txtCognome.getText().equals("")) {
			return true;
		}
		
		return false;
	}
	
	
	public SettingsPanel(UserData userd) {
		userdata = userd;
		
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 740, 500);
		add(panel);
		panel.setLayout(null);
		
		txtUsername = new JTextField();
		txtUsername.setColumns(10);
		txtUsername.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtUsername.setBounds(85, 115, 200, 20);
		panel.add(txtUsername);
		
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setBounds(85, 95, 200, 14);
		panel.add(lblUsername);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setBounds(85, 146, 200, 14);
		panel.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setColumns(10);
		txtEmail.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtEmail.setBounds(85, 166, 200, 20);
		panel.add(txtEmail);
		
		JLabel lblEmail_2 = new JLabel("Nome");
		lblEmail_2.setBounds(85, 197, 200, 14);
		panel.add(lblEmail_2);
		
		txtNome = new JTextField();
		txtNome.setColumns(10);
		txtNome.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtNome.setBounds(85, 217, 200, 20);
		panel.add(txtNome);
		
		JLabel lblEmail_3 = new JLabel("Cognome");
		lblEmail_3.setBounds(85, 248, 200, 14);
		panel.add(lblEmail_3);
		
		txtCognome = new JTextField();
		txtCognome.setColumns(10);
		txtCognome.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtCognome.setBounds(85, 268, 200, 20);
		panel.add(txtCognome);
		
		JLabel lblNewLabel = new JLabel("Cambia i dati personali");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(85, 45, 200, 20);
		panel.add(lblNewLabel);
		
		lblCambiaLaPassword = new JLabel("Cambia la password");
		lblCambiaLaPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblCambiaLaPassword.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCambiaLaPassword.setBounds(455, 45, 200, 20);
		panel.add(lblCambiaLaPassword);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(368, 11, 2, 480);
		panel.add(panel_1);
		
		lblCurrentPssword = new JLabel("Password corrente");
		lblCurrentPssword.setBounds(455, 95, 200, 14);
		panel.add(lblCurrentPssword);
		
		txtCurrentPassword = new JPasswordField();
		txtCurrentPassword.setColumns(10);
		txtCurrentPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		txtCurrentPassword.setBounds(455, 115, 200, 20);
		panel.add(txtCurrentPassword);
		
		btnComfermaPassword = new JButton("CONFERMA");
		btnComfermaPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Show_ChangePasswordPanel();
			}
		});
		btnComfermaPassword.setForeground(SystemColor.inactiveCaptionBorder);
		btnComfermaPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnComfermaPassword.setFocusPainted(false);
		btnComfermaPassword.setBackground(Color.DARK_GRAY);
		btnComfermaPassword.setBounds(455, 146, 200, 30);
		panel.add(btnComfermaPassword);
		
		JButton btnCambiaDati = new JButton("CAMBIA");
		btnCambiaDati.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Change_UserData();
			}
		});
		btnCambiaDati.setForeground(SystemColor.inactiveCaptionBorder);
		btnCambiaDati.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCambiaDati.setFocusPainted(false);
		btnCambiaDati.setBackground(Color.DARK_GRAY);
		btnCambiaDati.setBounds(85, 331, 200, 30);
		panel.add(btnCambiaDati);
		
		lblDatiMSG = new JLabel("I dati sono stati cambiati corretamente");
		lblDatiMSG.setForeground(new Color(50, 205, 50));
		lblDatiMSG.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatiMSG.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblDatiMSG.setBounds(50, 415, 270, 20);
		panel.add(lblDatiMSG);
		
		lblPasswordMSG = new JLabel("La password \u00E8 stata cambiata corretamente");
		lblPasswordMSG.setHorizontalAlignment(SwingConstants.CENTER);
		lblPasswordMSG.setForeground(new Color(50, 205, 50));
		lblPasswordMSG.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPasswordMSG.setBounds(398, 415, 314, 20);
		panel.add(lblPasswordMSG);
		
		pnlChangePass = new JPanel();
		pnlChangePass.setBackground(Color.WHITE);
		pnlChangePass.setBounds(368, 187, 344, 190);
		panel.add(pnlChangePass);
		pnlChangePass.setLayout(null);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(85, 31, 200, 20);
		pnlChangePass.add(txtPassword);
		txtPassword.setColumns(10);
		txtPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		
		lblPassword = new JLabel("Password");
		lblPassword.setBounds(85, 11, 200, 14);
		pnlChangePass.add(lblPassword);
		
		lblConfirmPassword = new JLabel("Conferma password");
		lblConfirmPassword.setBounds(85, 62, 200, 14);
		pnlChangePass.add(lblConfirmPassword);
		
		txtConfirmPassword = new JPasswordField();
		txtConfirmPassword.setBounds(85, 82, 200, 20);
		pnlChangePass.add(txtConfirmPassword);
		txtConfirmPassword.setColumns(10);
		txtConfirmPassword.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(64, 64, 64)));
		
		btnCambiaPassword = new JButton("CAMBIA");
		btnCambiaPassword.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OnChangePassword();
			}
		});
		btnCambiaPassword.setBounds(85, 145, 200, 30);
		pnlChangePass.add(btnCambiaPassword);
		btnCambiaPassword.setForeground(SystemColor.inactiveCaptionBorder);
		btnCambiaPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnCambiaPassword.setFocusPainted(false);
		btnCambiaPassword.setBackground(Color.DARK_GRAY);
		
		Set_UserData();
	}
}
