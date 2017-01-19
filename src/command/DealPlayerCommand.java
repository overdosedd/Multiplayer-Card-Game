package command;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class DealPlayerCommand extends Command {
	Player player;
	
	public DealPlayerCommand(Player player)
	{
		this.player = player;
		
		this.setName("deal player");
		this.setType(SET);
	}
	
	public boolean execute(GameEngine engine) {
		engine.dealPlayer(player, 10);
		
		return true;
	}
}
