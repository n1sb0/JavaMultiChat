package DBConnection;

import java.sql.*;
import java.util.ArrayList;

import DBClasses.Message;
import DBClasses.UserData;
import Encryption.Encryption;

public class DBCalls {

	static Connection _conn = null;

	public static void getConnection() throws Exception {
		_conn = DBConnect.getConnection();
	}
	
	public static void Update_Password(String email ,String password) {

		String hashedPass = "", salt = "";

		salt = Encryption.getSalt(30);
		hashedPass = Encryption.generateSecurePassword(password, salt);

		String query = "UPDATE users_data SET password = '"+hashedPass+"', salt = '"+salt+"' WHERE email = '"+email+"'";
		
		try {
			getConnection();
			PreparedStatement insert = _conn.prepareStatement(query);
			insert.executeUpdate();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Update_Password: " + e);
		}
	}

	public static void Insert_RegistreNewAcc(String username, String nome, String cognome, String email,
			String password) {

		String hashedPass = "", salt = "";

		salt = Encryption.getSalt(30);
		hashedPass = Encryption.generateSecurePassword(password, salt);

		String query = "INSERT INTO users_data (username, nome, cognome, email, password, salt) values " + "('"
				+ username + "','" + nome + "','" + cognome + "','" + email + "','" + hashedPass + "','" + salt + "')";
		try {
			getConnection();
			PreparedStatement insert = _conn.prepareStatement(query);
			insert.executeUpdate();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Insert_RegistreNewAcc: " + e);
		}
	}

	public static boolean Get_LoginUser(String email, String password) {
		UserData userdata = new UserData();

		try {
			getConnection();

			String query = "SELECT email,password,salt FROM users_data Where email = '" + email + "'";
			Statement st = _conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				userdata.email = rs.getString("email");
				userdata.password = rs.getString("password");
				userdata.salt = rs.getString("salt");
			}
			if(userdata.email == null || userdata.email.equals("")) {
				st.close();
				return false;
			}
						
			st.close();
			_conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Get_LoginUser: " + e);
		}

		return Encryption.verifyUserPassword(password, userdata.password, userdata.salt);
	}
	
	public static UserData Get_AllUserData(String email, int id) {
		UserData userdata = new UserData();

		try {
			getConnection();
			String query = "";
			
			if(email.length() > 0) {
				query = "SELECT * FROM users_data Where email = '" + email + "'";
			}else if(id > 0){
				query = "SELECT * FROM users_data Where id = '" + id + "'";
			}
			
			Statement st = _conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				userdata.id = rs.getInt("id");
				userdata.username = rs.getString("username");
				userdata.nome = rs.getString("nome");
				userdata.cognome = rs.getString("cognome");
				userdata.email = rs.getString("email");
				userdata.password = rs.getString("password");
				userdata.salt = rs.getString("salt");
			}
			
			if(userdata.email == null || userdata.email.equals("")) {
				st.close();
				_conn.close();
				return userdata;
			}
						
			st.close();
			_conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Get_AllUserData: " + e);
		}

		return userdata;
	}
	
	public static String Get_UserName(String email) {
		String username = "";

		try {
			getConnection();

			String query = "SELECT username FROM users_data Where email = '" + email + "'";
			Statement st = _conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				username = rs.getString("username");
			}
						
			st.close();
			_conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Get_UserName: " + e);
		}
		
		return username;
	}

	public static boolean ChangePassword(String email, String newpassword) {
		try {
			getConnection();
			
			String salt = Encryption.getSalt(30);
			String hashedPass = Encryption.generateSecurePassword(newpassword, salt);
			
			String query = "UPDATE users_data set password = '" + hashedPass + "', salt = '" + salt + "'  Where email = '" + email + "'";
			PreparedStatement update = _conn.prepareStatement(query);
			update.executeUpdate();
			
			update.close();
			_conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ChangePassword: " + e);
		}	
		
		return false;
	}

	public static boolean Update_UserData(UserData userdata) {	
		try {
			getConnection();
			
			String query = "UPDATE users_data set username = '" + userdata.username + "', email = '" + userdata.email + "', nome = '"+ userdata.nome +"', cognome = '"+ userdata.cognome +"'  Where id = '" + userdata.id + "'";
			PreparedStatement update = _conn.prepareStatement(query);
			update.executeUpdate();
			
			update.close();
			_conn.close();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Get_LoginUser: " + e);
		}	
		
		return false;
	}

	public static void Insert_Message(int id, String data, String message) {

		String query = "INSERT INTO messaggi (userid, messagedata, message) values " + "('"
				+ id + "','" + data + "', aes_encrypt('" + message + "','chiave123'))";
		try {
			getConnection();
			PreparedStatement insert = _conn.prepareStatement(query);
			insert.executeUpdate();
			_conn.close();
		} catch (Exception e) {
			System.out.println("Update_UserData: " + e);
		}
				
	}

	public static ArrayList<Message> getAllMessages() {
		
		ArrayList<Message> messages = new ArrayList<Message>();
		Message message;
		
		try {
			getConnection();

			String query = "SELECT id, userid, messagedata, cast(aes_decrypt(message,'chiave123') as char(10000)) as decmessage FROM messaggi";
			Statement st = _conn.createStatement();
			ResultSet rs = st.executeQuery(query);

			while (rs.next()) {
				message = new Message();
				message.messageid = rs.getInt("id");
				message.userid = rs.getInt("userid");
				message.messagedate = rs.getString("messagedata");
				message.message = rs.getString("decmessage");
				
				messages.add(message);
			}
						
			st.close();
			_conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("getAllMessages: " + e);
		}
		
		
		return messages;
	}

}
