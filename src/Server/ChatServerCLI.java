package Server;

import java.awt.Font;
import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JEditorPane;

import DBClasses.UserData;
import DBConnection.DBCalls;
import Utility.Utility;

public class ChatServerCLI {
	private static HashMap<String, PrintWriter> connectedClients = new HashMap<>();
	private static final int MAX_CONNECTED = 50;
	private static int PORT;
	private static ServerSocket server;

	private static class ClientHandler implements Runnable {
		private Socket socket;
		private PrintWriter out;
		private BufferedReader in;
		private String email;
		private UserData userdata;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String message;
			
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			
				email = in.readLine();
				
				userdata = DBCalls.Get_AllUserData(email,0);
				
				broadcastMessage("^ Benvenuto nella chat, " + userdata.username.toUpperCase() + "! ^");
				out.println(" Benvenuto, ti sei connesso nella chat... ^");
				
				connectedClients.put(userdata.username, out);
				
				while ((message = in.readLine()) != null) {
					if (!message.isEmpty()) {
						String data = Utility.getCurrentTime();
						broadcastMessage(" " + userdata.username + " " + data + " ^ " + message +" ^");
						
						DBCalls.Insert_Message(userdata.id, data, message);
					}
				}
			} catch (Exception e) {
				
				broadcastMessage("^ Utente: " + userdata.username.toUpperCase() + ", e' disconnesso. ^");
				try {
					stop();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println("Server conn err: "+ e);
			}
		}
	}
	// End of Client Handler

	// trasmete il messaggio a tutti i Client connessi
	private static void broadcastMessage(String message) {
		for (Entry<String, PrintWriter> p : connectedClients.entrySet()) {
				p.getValue().println(message);
		}
	}

	// apre connessione con Client
	public static void start() {
		try {
			server = new ServerSocket(4444);
			for (;;) {
				if (connectedClients.size() <= MAX_CONNECTED) {
					Thread newClient = new Thread(new ClientHandler(server.accept()));
					newClient.start();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void stop() throws IOException {
		if (!server.isClosed())
			server.close();
	}

	public static void main(String[] args) throws IOException {
		start();
	}
}