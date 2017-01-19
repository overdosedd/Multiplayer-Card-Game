package command;

import model.interfaces.GameEngine;
import model.interfaces.Player;

@SuppressWarnings("serial")
public class PlaceBetCommand extends Command {
	private Player player;
	private int bet;
	
	public PlaceBetCommand(Player player, int bet)
	{
		this.player = player;
		this.bet = bet;
		
		this.setName("place bet");
		this.setType(SET);
	}
	
	public boolean execute(GameEngine engine) {
		return engine.placeBet(player, bet);
	}
}
