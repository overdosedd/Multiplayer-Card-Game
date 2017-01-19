package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import command.Command;
import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class GameEngineServerStub implements Serializable {
	private static final String GET = "get";
	private static final String SET = "set";

	private static final int port = 10001;
	private GameEngine engine;
	
	public static void main(String [] args) throws Exception
	{
		new GameEngineServerStub();
	}
	
	public GameEngineServerStub () throws Exception {
		engine = new GameEngineImpl();
		handleConnection();
	}

	public void handleConnection() throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
			
			serverSocket.setSoTimeout(0);
			
			int clientCount = 1;
			while (true)
			{
				// Listen for a connection request
				Socket socket = serverSocket.accept();

				// create new client connection on separate thread
				new ClientThread(socket, clientCount).start();

				System.out.println("Client #" + clientCount + " has connected");

				clientCount++;				
			}
		} catch (Exception e)
		{
			
		}
	}
	
	public void handleClient(Socket socket, int clientId) throws IOException
	{
			try {
				// Create an output stream to send data to the server
				ObjectOutputStream outputToClient = new ObjectOutputStream(socket.getOutputStream());
				// Create an input stream to receive data from the server
				ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
				
				while (true)
				{
					Command cmd = (Command) inputFromClient.readObject();
						
					if (cmd != null)
					{
						if (cmd.getType().equals(SET))
						{
							if (cmd.execute(engine))
								System.out.println("Client #" + clientId + " has executed command <" + cmd.getCmdName() + ">");
						}
						else if (cmd.getType().equals(GET))
						{
							Object object = cmd.retrieve(engine);
							outputToClient.writeObject(object);
							outputToClient.reset();
						}
					}
				}
			} catch (SocketException e) {
				System.out.println("Client #" + clientId + " has left the game");
				ArrayList<Player> playerList = (ArrayList<Player>) engine.getAllPlayers();
				
				Player player = playerList.get(clientId-1);
				
				engine.removePlayer(player);
				playerList = (ArrayList<Player>) engine.getAllPlayers();
				for (Player p : playerList)
				{
					System.out.println("player index : " + playerList.indexOf(p) + " has id " + p.getPlayerId());
				}
				
			} catch (ClassNotFoundException e) {
				System.out.println("[" + clientId + "] An error has occured while executing a command");
			} 
	}
	
	private class ClientThread extends Thread
	{
		private Socket socket;
		private int clientId;

		public ClientThread(Socket socket, int clientId)
		{
			this.socket = socket;
			this.clientId = clientId;
		}

		public void run()
		{
			try {
				handleClient(socket, clientId);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
}
