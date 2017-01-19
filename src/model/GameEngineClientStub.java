package model;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Collection;
import java.util.Deque;
import java.util.List;
import java.util.Properties;
import javax.swing.JOptionPane;

import command.AddGameEngineCallbackCommand;
import command.AddPlayerCommand;
import command.Command;
import command.DealHouseCommand;
import command.DealPlayerCommand;
import command.GetAllPlayersCommand;
import command.GetPlayerCommand;
import command.PlaceBetCommand;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;
import model.interfaces.Player;
import model.interfaces.PlayingCard;
import view.ClientGUI;

@SuppressWarnings("serial")
public class GameEngineClientStub implements GameEngine, Serializable {
	protected static final int MAX_PORT = 1000;
	protected static final int MIN_PORT = 100;

	// IO streams
	private ObjectInputStream inputFromServer;
	private ObjectOutputStream outputToServer;

	private int port = (int )(Math.random() * MAX_PORT + MIN_PORT);
	
	private Thread clientCallbackThread;
	
	public GameEngineClientStub (final ClientGUI view)
	{
		// Get server port from config file
		Properties serverDetails = getServerDetails();
		
		Socket socket = null;
		try
		{
			// Create a socket to connect to the server
			socket = new Socket(serverDetails.getProperty("host"), Integer.parseInt(serverDetails.getProperty("port")));
			socket.setSoTimeout(0);
			
			// Print a message in console if successfully connected to the server
			if (socket != null)
				System.out.println("Connected to server");

			// Create an output stream to send data to the server
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			inputFromServer = new ObjectInputStream(socket.getInputStream());
			
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(null, "Server is offline, now exiting..");
			System.exit(0);
		}
		
		// Generate a port number between MIN_PORT and MAX_PORT
		
		clientCallbackThread = (new Thread () {
			public void run() {
				while (true)
				{
					// Add a client gameEngine callback server for every client
					try {
						port = (int )(Math.random() * MAX_PORT + MIN_PORT);
						if (port != 0)
							new ClientGameEngineCallbackServer(view, port);
					} catch (Exception e) {
						// Loop until we find an available port
					}					
				}
			}
		});
		
		clientCallbackThread.start();
		
		addGameEngineCallback(null);		
	}
	
	public Properties getServerDetails()
	{
		Properties prop = new Properties();
		InputStream properties = null;

		try {

			String filename = "config.properties";
			properties = this.getClass().getClassLoader().getResourceAsStream(filename);
    		if(properties == null) {
    			System.out.println("Sorry, unable to find " + filename);
    		}

			// load a properties file
			prop.load(properties);

			// get the property
			return prop;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (properties != null) {
				try {
					properties.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	@Override
	public void dealPlayer(Player player, int delay) {
		Command dealPlayerCommand = new DealPlayerCommand(player);
		try {
			outputToServer.writeObject(dealPlayerCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dealHouse(int delay) {
		Command dealHouseCommand = new DealHouseCommand();
		try {
			outputToServer.writeObject(dealHouseCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void addPlayer(Player player) {
		Command addPlayerCommand = new AddPlayerCommand(player);
		try {
			outputToServer.writeObject(addPlayerCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Player getPlayer(String id) {
		Command getPlayerCommand = new GetPlayerCommand(id);
		try {
			outputToServer.writeObject(getPlayerCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			Player player = (Player) inputFromServer.readObject();
			return player;
		} catch (Exception e) {
			System.out.println("An error has occured while getting player information from server, now exiting..");
		}
		return null;
	}

	@Override
	public boolean removePlayer(Player player) {
		
		return false;
	}

	@Override
	public void calculateResult() {
		
		
	}

	@Override
	public void addGameEngineCallback(GameEngineCallback gameEngineCallback) {
		Command addGameEngineCallbackCommand = new AddGameEngineCallbackCommand(port);
		try {
			outputToServer.writeObject(addGameEngineCallbackCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Collection<Player> getAllPlayers() {
		Command getAllPlayersCommand = new GetAllPlayersCommand();
		try {
			outputToServer.writeObject(getAllPlayersCommand);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			@SuppressWarnings("unchecked")
			List<Player> allPlayers = (List<Player>) inputFromServer.readObject();
			return allPlayers;
		} catch (Exception e) {
			System.out.println("An error has occured while getting player information from server, now exiting..");
		}
		return null;
	}

	@Override
	public boolean placeBet(Player player, int bet) {
		Command placeBetCommand = new PlaceBetCommand(player, bet);
		try {
			outputToServer.writeObject(placeBetCommand);
			
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Deque<PlayingCard> getShuffledDeck() {
		return null;
	}
}