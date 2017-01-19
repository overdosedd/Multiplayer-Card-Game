package command;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class AddPlayerCommand extends Command {
	private Player player;
	
	public AddPlayerCommand(Player player)
	{
		this.player = player;
		
		this.setName("add player");
		this.setType(SET);
	}
	
	public boolean execute(GameEngine engine) {
		engine.addPlayer(player);
		
		return true;
	}
}
