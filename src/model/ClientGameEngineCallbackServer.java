package model;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;

import callback.Callback;
import model.interfaces.GameEngineCallback;
import view.ClientGUI;

@SuppressWarnings("serial")
public class ClientGameEngineCallbackServer implements Serializable {
	public ClientGameEngineCallbackServer (ClientGUI view, int port) throws Exception {
		try (ServerSocket serverSocket = new ServerSocket(port))
		{
			GameEngineCallback gameEngineCallback = new GameEngineCallbackImpl(view);
			GameEngineCallback gameEngineCallbackForConsole = new GameEngineCallbackConsole(view);
			
			System.out.println("[CALLBACK] Waiting for connection on port " + serverSocket.getLocalPort() + "...");
			
			serverSocket.setSoTimeout(0);
			
			// Accept the first connection
			Socket socket = serverSocket.accept();
			
			// Create an input stream to receive data from the server
			ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
			
			while (true)
			{
				try {
					Callback callback = (Callback) inputFromClient.readObject();
					
					callback.execute(gameEngineCallback);
					callback.execute(gameEngineCallbackForConsole);
				} catch (Exception e) {
					System.out.println("An error has occured while receiving callback, now exiting..");
					System.exit(0);
				}
			}
		} catch (Exception e)
		{
			System.out.println("A critical error has occured while trying to start the local callback server, now exiting..");
			System.exit(0);
		}
	}

}
