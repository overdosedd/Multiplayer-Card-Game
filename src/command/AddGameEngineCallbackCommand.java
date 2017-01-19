package command;

import model.ServerStubGameEngineCallback;
import model.interfaces.GameEngine;
import model.interfaces.GameEngineCallback;

@SuppressWarnings("serial")
public class AddGameEngineCallbackCommand extends Command  {
	private int port;
	private GameEngineCallback serverStubGameEngineCallback;
	
	public AddGameEngineCallbackCommand(int port)
	{
		this.port = port;
		
		this.setName("add callback");
		this.setType(SET);
	}
	
	public boolean execute(GameEngine engine) {		
		// Create a new server stub game engine callback for every client 
		serverStubGameEngineCallback = new ServerStubGameEngineCallback(port);
		
		engine.addGameEngineCallback(serverStubGameEngineCallback);
		
		return true;
	}
}