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
		private String name;

		public ClientHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String[] s;
			String message;
			
			try {
				in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				out = new PrintWriter(socket.getOutputStream(), true);
			
				name = in.readLine();
				s = name.split(";");

				connectedClients.put(name, out);
				while ((message = in.readLine()) != null) {
					if (!message.isEmpty()) {
						broadcastMessage("^ " + s[0] + " " + Utility.getCurrentTime() + " ^ " + message, s[1]);
					}
				}
			} catch (Exception e) {
				System.out.println("Server conn err: "+ e);
			}
		}
	}
	// End of Client Handler

	// trasmete il messaggio a tutti i Client connessi
	private static void broadcastMessage(String message, String chatname) {
		for (Entry<String, PrintWriter> p : connectedClients.entrySet()) {
			if (p.getKey() != null && p.getKey().contains(chatname)) {
				p.getValue().println(message);
			}
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