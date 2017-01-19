package model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

import callback.*;
import model.interfaces.*;

@SuppressWarnings("serial")
public class ServerStubGameEngineCallback implements GameEngineCallback, Serializable {
	private ObjectOutputStream outputToServer;
	private int port;
	
	public ServerStubGameEngineCallback (int port) {
		this.port = port;
		
		Socket socket = null;
		try
		{
			// Create a socket to connect to the server
			socket = new Socket("localhost", port);
			socket.setSoTimeout(0);
			
			if (socket != null)
				System.out.println("Connected to callback server on port " + port);

			// Create an output stream to send data to the server
			outputToServer = new ObjectOutputStream(socket.getOutputStream());
			
		} catch (IOException ex)
		{
			System.out.println("Unable to connect to callback server on port " + port);			
		}
	}
	
	public int getPort() {
		return this.port;
	}
	
	@Override
	public void nextCard(Player player, PlayingCard card, GameEngine engine) {
		Callback nextCardCallback = new NextCardCallback(player, card, null);
		try {
			outputToServer.writeObject(nextCardCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at next card callback");
		}
	}

	@Override
	public void bustCard(Player player, PlayingCard card, GameEngine engine) {
		Callback bustCardCallback = new BustCardCallback(player, card, null);
		try {
			outputToServer.writeObject(bustCardCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at bust card callback");
		}
	}

	@Override
	public void result(Player player, int result, GameEngine engine) {
		Callback resultCallback = new ResultCallback(player, result, null);
		try {
			outputToServer.writeObject(resultCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at result callback");
		}
	}

	@Override
	public void nextHouseCard(PlayingCard card, GameEngine engine) {
		Callback nextHouseCallback = new NextHouseCallback(card, null);
		try {
			outputToServer.writeObject(nextHouseCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at next house card callback");
		}
	}

	@Override
	public void houseBustCard(PlayingCard card, GameEngine engine) {
		Callback houseBustCardCallback = new HouseBustCallback(card, null);
		try {
			outputToServer.writeObject(houseBustCardCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at house boust card callback");
		}
	}

	@Override
	public void houseResult(int result, GameEngine engine) {
		Callback houseResultCallback = new HouseResultCallback(result, null);
		try {
			outputToServer.writeObject(houseResultCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at house result callback");
		}
	}

	@Override
	public void finalResult(String result, Player player, int score, List<Player> drawList, GameEngine engine) {
		Callback finalResultCallback = new FinalResultCallback(result, player, score, drawList, null);
		try {
			outputToServer.writeObject(finalResultCallback);
			outputToServer.reset();
		} catch (IOException e) {
			System.out.println("An error has occured at final result callback");
		}
		
	}

}
