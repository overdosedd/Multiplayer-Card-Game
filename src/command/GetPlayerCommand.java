package command;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class GetPlayerCommand extends Command {
	private String id;
	
	public GetPlayerCommand(String playerID) 
	{
		this.id = playerID;
		
		this.setType(GET);
	}
	
	public Player retrieve(GameEngine engine) {
		Player player;
		
		player = engine.getPlayer(id);
		
		return player;
	}
}
